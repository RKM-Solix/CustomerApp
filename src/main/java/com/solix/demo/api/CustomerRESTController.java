package com.solix.demo.api;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.solix.demo.entity.CustomerEntity;
import com.solix.demo.entity.PlanEntity;
import com.solix.demo.model.CustomerResponse;
import com.solix.demo.model.LoginRequest;
import com.solix.demo.service.CustomerService;

@RestController
@RequestMapping("/api3")
public class CustomerRESTController {

	private static final String PLAN_URL = "http://localhost:8082/getPlan/{id}";
	private static final String FRIEND_URL = "http://localhost:8083/friend/{phoneNumber}";
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping(value = "/customer/register", consumes = "application/json")
	public boolean addCustomer(@RequestBody CustomerEntity customer) {
		return customerService.registerCustomer(customer);
	}
	
	@PostMapping(value = "/customer/login")
	public boolean loginCustomer(@RequestBody LoginRequest loginRequest) {
		return customerService.loginCustomer(loginRequest);
	}
	
	// profile...
	/* CustomerResponse = CustomerEntity(phoneNumber, username, email, planId) + 
	                      PlanEntity(planName, description, validity) + 
	                      FriendEntity(FriendsContactNumberList)..
	*/
	@GetMapping("/customer/profile/{phoneNumber}")
	public CustomerResponse showProfile(@PathVariable Long phoneNumber) {
		
		CustomerEntity customerEntity = customerService.readCustomer(phoneNumber);
		CustomerResponse customerResponse = new CustomerResponse();
		
		//(source, destination)...
		BeanUtils.copyProperties(customerEntity, customerResponse);
		
		// calling plan services..
		ResponseEntity<PlanEntity> re = restTemplate.getForEntity(PLAN_URL, PlanEntity.class, customerEntity.getPlanId());
		
		PlanEntity planEntity = re.getBody();
		BeanUtils.copyProperties(planEntity, customerResponse);
		
		// calling friend microservice...
		List<Long> friendsContactNumbers = restTemplate.getForObject(FRIEND_URL, List.class, phoneNumber);
		customerResponse.setFriendsContactNumbers(friendsContactNumbers);
		
		return customerResponse;
	}
	
}
