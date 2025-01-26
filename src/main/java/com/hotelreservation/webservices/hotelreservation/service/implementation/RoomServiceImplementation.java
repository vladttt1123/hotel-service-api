package com.hotelreservation.webservices.hotelreservation.service.implementation;

import com.hotelreservation.webservices.hotelreservation.exceptions.HotelNotFoundException;
import com.hotelreservation.webservices.hotelreservation.exceptions.RoomNotFoundException;
import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import com.hotelreservation.webservices.hotelreservation.models.Room;
import com.hotelreservation.webservices.hotelreservation.repository.HotelRepository;
import com.hotelreservation.webservices.hotelreservation.repository.RoomRepository;
import com.hotelreservation.webservices.hotelreservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImplementation implements RoomService {


    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImplementation(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public List<Room> getAllRoomsInHotel(Integer hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isEmpty()) {
            throw new HotelNotFoundException("id: " + hotelId);
        }
        return hotel.get().getRooms();
    }

    @Override
    public Room getHotelRoomById(Integer hotelId, Integer roomId) {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isEmpty()) {
            throw new HotelNotFoundException("id: " + hotelId);
        }

        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty() || !room.get().getHotel().getId().equals(hotelId)) {
            throw new RoomNotFoundException("id: " + roomId);
        }
        return room.get();
    }

    @Override
    public ResponseEntity<Room> createRoom(Integer hotelId, Room room) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isEmpty()) {
            throw new HotelNotFoundException("id: " + hotelId);
        }

        room.setHotel(hotel.get());
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.created(null).body(savedRoom);
    }

    @Override
    public ResponseEntity<Room> updateRoom(Integer hotelId, Integer roomId, Room roomDetails) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if (hotelOptional.isEmpty()) {
            throw new HotelNotFoundException("id: " + hotelId);
        }

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty() || !roomOptional.get().getHotel().getId().equals(hotelId)) {
            throw new RoomNotFoundException("id: " + roomId);
        }

        Room room = roomOptional.get();

        if (roomDetails.getRoomType() != null) {
            room.setRoomType(roomDetails.getRoomType());
        }

        if (roomDetails.getAvailable() != null) {
            room.setAvailable(roomDetails.getAvailable());
        }
        roomRepository.save(room);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void deleteRoomById(Integer hotelId, Integer roomId) {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isEmpty()) {
            throw new HotelNotFoundException("id: " + hotelId);
        }

        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty() || !room.get().getHotel().getId().equals(hotelId)) {
            throw new RoomNotFoundException("id: " + roomId);
        }
        roomRepository.deleteById(roomId);
    }
}
