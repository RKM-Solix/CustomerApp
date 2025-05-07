package com.solix.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerResponse {

	private Long phoneNumber;
	
	private String username;
	
	private String email;
	
	private String planId;
	
	private String planName;
	
	private String description;
	
	private String validity;
	
	private List<Long> friendsContactNumbers;
}
