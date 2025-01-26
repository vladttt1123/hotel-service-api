package com.hotelreservation.webservices.hotelreservation.controllers;

import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import com.hotelreservation.webservices.hotelreservation.service.implementation.HotelServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {

    private final HotelServiceImplementation hotelService;

    public HotelController(HotelServiceImplementation hotelService) {
        this.hotelService = hotelService;
    }


    @GetMapping("/hotels")
    public List<Hotel> retrieveAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    public Hotel retrieveHotelById(@PathVariable Integer id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PatchMapping("/hotels/{id}")
    public ResponseEntity<Void> updateHotel(@PathVariable Integer id, @Valid @RequestBody Hotel hotelDetails) {
        hotelService.updateHotel(id, hotelDetails);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Integer id) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hotels")
    public ResponseEntity<Void> deleteHotels() {
        hotelService.deleteAllHotels();
        return ResponseEntity.noContent().build();
    }

}
