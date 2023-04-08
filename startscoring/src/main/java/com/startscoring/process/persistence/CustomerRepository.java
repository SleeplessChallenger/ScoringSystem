package com.startscoring.process.persistence;

import com.startscoring.process.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
