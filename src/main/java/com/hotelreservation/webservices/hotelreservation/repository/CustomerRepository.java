package com.hotelreservation.webservices.hotelreservation.repository;

import com.hotelreservation.webservices.hotelreservation.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
