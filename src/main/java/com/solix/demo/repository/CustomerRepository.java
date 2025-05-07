package com.solix.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.solix.demo.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	@Query(value = "select count(*) from customer_entity c where c.phone_number = ?1 and c.password = ?2", nativeQuery = true)
	Integer checkLogin(Long phoneNumber, String password);
}
