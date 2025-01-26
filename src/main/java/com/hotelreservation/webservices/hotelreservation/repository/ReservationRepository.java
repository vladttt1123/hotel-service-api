package com.hotelreservation.webservices.hotelreservation.repository;

import com.hotelreservation.webservices.hotelreservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
