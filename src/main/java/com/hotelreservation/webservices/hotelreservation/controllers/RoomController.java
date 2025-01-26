package com.hotelreservation.webservices.hotelreservation.controllers;

import com.hotelreservation.webservices.hotelreservation.models.Room;
import com.hotelreservation.webservices.hotelreservation.service.implementation.RoomServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {


    private final RoomServiceImplementation roomService;


    public RoomController(RoomServiceImplementation roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/hotels/{id}/rooms")
    public ResponseEntity<Room> createRoom(@PathVariable Integer id, @Valid @RequestBody Room room) {

        return roomService.createRoom(id, room);
    }

    @GetMapping("/hotels/{id}/rooms")
    public List<Room> retrieveHotelRooms(@PathVariable Integer id) {
        return roomService.getAllRoomsInHotel(id);
    }

    @GetMapping("/hotels/{hotel_id}/rooms/{room_id}")
    public Room retrieveRoomInHotel(@PathVariable Integer hotel_id, @PathVariable Integer room_id) {
        return roomService.getHotelRoomById(hotel_id, room_id);
    }

    @DeleteMapping("/hotels/{hotel_id}/rooms/{room_id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer hotel_id, @PathVariable Integer room_id) {
        roomService.deleteRoomById(hotel_id, room_id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/hotels/{hotel_id}/rooms/{room_id}")
    public ResponseEntity<Room> updateRoomInHotel(@PathVariable Integer hotel_id, @PathVariable Integer room_id, @Valid @RequestBody Room roomDetails) {
        return roomService.updateRoom(hotel_id, room_id, roomDetails);
    }
}
