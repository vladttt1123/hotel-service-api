package com.hotelreservation.webservices.hotelreservation.service.implementation;

import com.hotelreservation.webservices.hotelreservation.exceptions.HotelNotFoundException;
import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import com.hotelreservation.webservices.hotelreservation.repository.HotelRepository;
import com.hotelreservation.webservices.hotelreservation.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImplementation implements HotelService {


    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImplementation(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    @Override
    public List<Hotel> getAllHotels() {

        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Integer id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);

        if (hotel.isEmpty()) {
            throw new HotelNotFoundException("id-" + id);
        }
        return hotel.get();
    }

    @Override
    public ResponseEntity<Hotel> createHotel(Hotel hotel) {
        Hotel savedHotel = hotelRepository.save(hotel);
        return ResponseEntity.created(null).body(savedHotel);
    }

    @Override
    public ResponseEntity<Void> updateHotel(Integer id, Hotel hotelDetails) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        if (hotelOptional.isEmpty()) {
            throw new HotelNotFoundException("id-" + id);
        }

        Hotel hotel = hotelOptional.get();
        if (hotelDetails.getName() != null) {
            hotel.setName(hotelDetails.getName());
        }

        if (hotelDetails.getAddress() != null) {
            hotel.setAddress(hotelDetails.getAddress());
        }

        hotelRepository.save(hotel);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void deleteHotelById(Integer id) {

        Optional<Hotel> hotel = hotelRepository.findById(id);

        if (hotel.isEmpty()) {
            throw new HotelNotFoundException("id-" + id);
        }
        hotelRepository.deleteById(hotel.get().getId());
    }

    @Override
    public void deleteAllHotels() {
        hotelRepository.deleteAll();

    }
}
