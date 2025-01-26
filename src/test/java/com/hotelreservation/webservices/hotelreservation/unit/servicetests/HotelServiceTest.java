package com.hotelreservation.webservices.hotelreservation.unit.servicetests;


import com.hotelreservation.webservices.hotelreservation.exceptions.HotelNotFoundException;
import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import com.hotelreservation.webservices.hotelreservation.repository.HotelRepository;
import com.hotelreservation.webservices.hotelreservation.service.implementation.HotelServiceImplementation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImplementation hotelService;

    private Hotel mockHotel;
    private Hotel mockHotel2;
    private Integer hotelId;
    private Integer hotelId2;


    @BeforeEach
    void setUp() {
        hotelId = 1;
        mockHotel = new Hotel();
        mockHotel.setId(hotelId);
        mockHotel.setName("Mock Hotel");
        mockHotel.setCity("Mock City");
        mockHotel.setAddress("Mock Address");

        hotelId2 = 2;
        mockHotel2 = new Hotel();
        mockHotel2.setId(hotelId2);
        mockHotel2.setName("Mock Hotel");
        mockHotel2.setCity("Mock City");
        mockHotel2.setAddress("Mock Address");
    }


    @Test
    void HotelService_GetAllHotels_ShouldReturnHotels() {
        // Arrange: Mocking the repository's findAll() method
        List<Hotel> hotelList = List.of(mockHotel, mockHotel2);
        when(hotelRepository.findAll()).thenReturn(hotelList);

        // Act: Calling the service method
        List<Hotel> hotels = hotelService.getAllHotels();

        // Assert: Verifying the results
        Assertions.assertThat(hotels).isNotNull();
        Assertions.assertThat(hotels.size()).isEqualTo(2);
        Assertions.assertThat(hotels.get(0).getName()).isEqualTo("Mock Hotel");
        Assertions.assertThat(hotels.get(0).getName()).isEqualTo("Mock Hotel");
    }

    @Test
    void HotelService_GetHotelById_Success() {
        // Arrange: Mocking the repository's findById() method
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(mockHotel));

        // Act: Calling the service method
        Hotel hotel = hotelService.getHotelById(hotelId);

        // Assert: Verifying the results
        Assertions.assertThat(hotel).isNotNull();
        Assertions.assertThat(hotel.getId()).isEqualTo(hotelId);
        Assertions.assertThat(hotel.getName()).isEqualTo("Mock Hotel");
    }

    @Test
    void HotelService_GetHotelById_NotFound() {
        // Arrange: Hotel not found in the repository
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception
        Assertions.assertThatThrownBy(() -> hotelService.getHotelById(hotelId))
                .isInstanceOf(HotelNotFoundException.class);
    }

    @Test
    void HotelService_CreateHotel_Success() {

        when(hotelRepository.save(mockHotel)).thenReturn(mockHotel);

        // Act: Call the service method
        ResponseEntity<Hotel> response = hotelService.createHotel(mockHotel);

        // Assert: Verify the response
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Mock Hotel");
    }

    @Test
    void HotelService_UpdateHotelOnlyName_Success() {

        // Arrange: Mock data
        Integer hotelId = 1;
        // Mock the existing hotel in the repository
        Hotel existingHotel = new Hotel();
        existingHotel.setId(hotelId);
        existingHotel.setName("Original Name");
        existingHotel.setAddress("Original Address");

        // Mock the details to update
        Hotel hotelDetails = new Hotel();
        hotelDetails.setName("Updated Name");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(existingHotel));
        when(hotelRepository.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved object

        // Act: Call the service method
        hotelService.updateHotel(hotelId, hotelDetails);

        // Assert: Verify the hotel was updated
        assertThat(existingHotel.getName()).isEqualTo("Updated Name");
        assertThat(existingHotel.getAddress()).isEqualTo("Original Address");

        // Verify repository interactions
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelRepository, times(1)).save(existingHotel);
    }

    @Test
    void HotelService_UpdateHotelOnlyAddress_Success() {

        // Arrange: Mock data
        Integer hotelId = 1;
        // Mock the existing hotel in the repository
        Hotel existingHotel = new Hotel();
        existingHotel.setId(hotelId);
        existingHotel.setName("Original Name");
        existingHotel.setAddress("Original Address");

        // Mock the details to update
        Hotel hotelDetails = new Hotel();
        hotelDetails.setAddress("Updated Address");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(existingHotel));
        when(hotelRepository.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved object

        // Act: Call the service method
        hotelService.updateHotel(hotelId, hotelDetails);

        // Assert: Verify the hotel was updated
        assertThat(existingHotel.getName()).isEqualTo("Original Name");
        assertThat(existingHotel.getAddress()).isEqualTo("Updated Address");

        // Verify repository interactions
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelRepository, times(1)).save(existingHotel);
    }

    @Test
    void HotelService_UpdateHotelNameAndAddress_Success() {

        // Arrange: Mock data
        Integer hotelId = 1;
        // Mock the existing hotel in the repository
        Hotel existingHotel = new Hotel();
        existingHotel.setId(hotelId);
        existingHotel.setName("Original Name");
        existingHotel.setAddress("Original Address");

        // Mock the details to update
        Hotel hotelDetails = new Hotel();
        hotelDetails.setName("Updated Name");
        hotelDetails.setAddress("Updated Address");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(existingHotel));
        when(hotelRepository.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved object

        // Act: Call the service method
        hotelService.updateHotel(hotelId, hotelDetails);

        // Assert: Verify the hotel was updated
        assertThat(existingHotel.getName()).isEqualTo("Updated Name");
        assertThat(existingHotel.getAddress()).isEqualTo("Updated Address");

        // Verify repository interactions
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelRepository, times(1)).save(existingHotel);
    }

    @Test
    void HotelService_UpdateHotel_NotFound() {
        Integer hotelId = 1;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception
        Assertions.assertThatThrownBy(() -> hotelService.updateHotel(hotelId, mockHotel))
                .isInstanceOf(HotelNotFoundException.class);

        // Verify that save was never called
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelRepository, never()).save(any(Hotel.class));
    }


    @Test
    void HotelService_DeleteHotelById_Success() {
        // Arrange: Mock a hotel object

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(mockHotel));
        doNothing().when(hotelRepository).deleteById(hotelId);

        // Act: Call the service method
        hotelService.deleteHotelById(hotelId);

        // Assert: Verify interactions with the repository
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelRepository, times(1)).deleteById(hotelId);
    }

    @Test
    void HotelService_DeleteHotelById_ThrowsException() {
        // Arrange: Hotel not found in the repository
        Integer hotelId = 1;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception
        Assertions.assertThatThrownBy(() -> hotelService.deleteHotelById(hotelId))
                .isInstanceOf(HotelNotFoundException.class);

        // Verify that deleteById was never called
        verify(hotelRepository, never()).deleteById(hotelId);
    }


}
