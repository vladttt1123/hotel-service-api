package com.hotelreservation.webservices.hotelreservation.service;

import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HotelService {

    List<Hotel> getAllHotels();

    Hotel getHotelById(Integer id);

    ResponseEntity<Hotel> createHotel(Hotel hotel);

    ResponseEntity<Void> updateHotel(Integer id, Hotel hotelDetails);

    void deleteHotelById(Integer id);

    void deleteAllHotels();
}
