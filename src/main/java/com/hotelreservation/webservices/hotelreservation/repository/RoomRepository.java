package com.hotelreservation.webservices.hotelreservation.repository;

import com.hotelreservation.webservices.hotelreservation.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
