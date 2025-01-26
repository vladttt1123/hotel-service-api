package com.hotelreservation.webservices.hotelreservation.unit.repositorytests;


import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import com.hotelreservation.webservices.hotelreservation.repository.HotelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;


    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll(); // hotels are cached, that's why need to clear them
        // Arrange
        hotel = Hotel.builder()
                .name("Test Hotel")
                .city("Test City")
                .address("123 Test Street")
                .build();
    }

    @Test
    void HotelRepository_GetAllHotels() {

        // act
        hotelRepository.save(hotel);
        // act
        List<Hotel> hotels = hotelRepository.findAll();
        Assertions.assertThat(hotels).isNotNull();
        Assertions.assertThat(hotels.size()).isEqualTo(1);
    }

    @Test
    void HotelRepository_GetHotelById() {

        // Act
        Hotel savedHotel = hotelRepository.save(hotel);
        Optional<Hotel> hotelOptional = hotelRepository.findById(savedHotel.getId());
        // Assert
        Assertions.assertThat(hotelOptional).isPresent();
        Assertions.assertThat(hotelOptional.get().getId()).isEqualTo(savedHotel.getId());

    }

    @Test
    void HotelRepository_UpdateHotelByName() {

        // Act
        Hotel savedHotel = hotelRepository.save(hotel);
        savedHotel.setName("Updated Hotel");
        hotelRepository.save(savedHotel);
        Optional<Hotel> hotelOptional = hotelRepository.findById(savedHotel.getId());
        // Assert
        Assertions.assertThat(hotelOptional).isPresent();
        Assertions.assertThat(hotelOptional.get().getName()).isEqualTo("Updated Hotel");
    }

    @Test
    void HotelRepository_UpdateHotelByAddress() {
        // Act
        Hotel savedHotel = hotelRepository.save(hotel);
        savedHotel.setAddress("Updated Address");
        hotelRepository.save(savedHotel);
        Optional<Hotel> hotelOptional = hotelRepository.findById(savedHotel.getId());
        // Assert
        Assertions.assertThat(hotelOptional).isPresent();
        Assertions.assertThat(hotelOptional.get().getAddress()).isEqualTo("Updated Address");
    }

    @Test
    void HotelRepository_CreateHotel() {
        // Act
        Hotel savedHotel = hotelRepository.save(hotel);
        // Assert
        Assertions.assertThat(savedHotel).isNotNull();
        Assertions.assertThat(savedHotel.getId()).isGreaterThan(0);
    }

    @Test
    void HotelRepository_DeleteHotelById_HotelIsEmpty() {

        hotel = hotelRepository.save(hotel);
        // Act
        hotelRepository.deleteById(hotel.getId());
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotel.getId());

        Assertions.assertThat(hotelOptional).isEmpty();
    }
}
