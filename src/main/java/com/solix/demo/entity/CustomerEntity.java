package com.solix.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_entity")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerEntity {

	@Id
	private Long phoneNumber;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String planId;
}
