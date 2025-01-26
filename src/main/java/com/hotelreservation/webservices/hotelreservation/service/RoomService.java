package com.hotelreservation.webservices.hotelreservation.service;

import com.hotelreservation.webservices.hotelreservation.models.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {


    List<Room> getAllRoomsInHotel(Integer hotelId);

    Room getHotelRoomById(Integer hotelId, Integer roomId);

    ResponseEntity<Room> createRoom(Integer hotelId, Room room);

    ResponseEntity<Room> updateRoom(Integer hotelId, Integer roomId, Room room);

    void deleteRoomById(Integer hotelId, Integer roomId);
}
