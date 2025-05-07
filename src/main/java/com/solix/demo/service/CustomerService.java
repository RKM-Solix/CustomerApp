package com.solix.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solix.demo.entity.CustomerEntity;
import com.solix.demo.model.LoginRequest;
import com.solix.demo.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public boolean registerCustomer(CustomerEntity customer) {
		
		if(!customerRepository.existsById(customer.getPhoneNumber())) {
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	
	public boolean loginCustomer(LoginRequest loginRequest) {
	
		Integer count = customerRepository.checkLogin(loginRequest.getPhoneNumber(), loginRequest.getPassword());
		
		if(count == 1) {
			return true;
		}
		return false;
	}


	public CustomerEntity readCustomer(Long phoneNumber) {
		
		Optional<CustomerEntity> customerEntity = customerRepository.findById(phoneNumber);
		
		if(customerEntity.isPresent()) {
			return customerEntity.get();
		}
		return null;
	}

}
