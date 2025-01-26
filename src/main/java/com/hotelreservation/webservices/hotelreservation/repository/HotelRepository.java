package com.hotelreservation.webservices.hotelreservation.repository;

import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}
