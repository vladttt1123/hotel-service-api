package com.hotelreservation.webservices.hotelreservation.unit.controllertests;


import com.hotelreservation.webservices.hotelreservation.controllers.HotelController;
import com.hotelreservation.webservices.hotelreservation.exceptions.HotelNotFoundException;
import com.hotelreservation.webservices.hotelreservation.models.Hotel;
import com.hotelreservation.webservices.hotelreservation.service.implementation.HotelServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//Controller Integration Test: It tests how the controller behaves when receiving HTTP requests and producing HTTP responses, but it does not test the service or repository layers.
@WebMvcTest(controllers = HotelController.class)
@AutoConfigureMockMvc(addFilters = false) // to be used to disable the default security filter chain
@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelServiceImplementation hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel A");
        hotel.setCity("City A");
        hotel.setAddress("Address A");
    }

    @Test
    void HotelController_GetAllHotels_ReturnsListOfHotels() throws Exception {
        // Mocking the service method call
        List<Hotel> mockHotels = List.of(
                new Hotel(1, "Hotel One", "City One", "Address One", null),
                new Hotel(2, "Hotel Two", "City Two", "Address Two", null)
        );

        when(hotelService.getAllHotels()).thenReturn(mockHotels);

        mockMvc.perform(get("/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void HotelController_GetHotelById_ReturnHotel() throws Exception {
        when(hotelService.getHotelById(hotel.getId())).thenReturn(hotel);  // Mocking the service method call

        mockMvc.perform(get("/hotels/{id}", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void HotelController_GetNotExistingHotelById_ReturnNotFound() throws Exception {

        doThrow(new HotelNotFoundException("exception")).when(hotelService).getHotelById(hotel.getId());

        mockMvc.perform(get("/hotels/{id}", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void HotelController_CreateHotel_ReturnCreated() throws Exception {
        // Arrange: Mock data
        Hotel savedHotel = new Hotel();
        savedHotel.setId(1);
        savedHotel.setName("New Hotel");
        savedHotel.setCity("New City");
        savedHotel.setAddress("New Address");

        when(hotelService.createHotel(any(Hotel.class))).thenReturn(ResponseEntity.created(null).body(savedHotel));

        // Act & Assert: Perform the request and verify the response
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Hotel\", \"city\": \"New City\", \"address\": \"New Address\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedHotel.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedHotel.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value(savedHotel.getCity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(savedHotel.getAddress()));
    }

    @Test
    void HotelController_UpdateHotel_ReturnNoContent() throws Exception {
        Hotel updatedHotel = new Hotel();
        updatedHotel.setId(1);
        updatedHotel.setName("Updated Hotel");
        updatedHotel.setCity("Updated City");
        updatedHotel.setAddress("Updated Address");

        when(hotelService.updateHotel(hotel.getId(), updatedHotel)).thenReturn(ResponseEntity.noContent().build());  // Mocking the service method call

        mockMvc.perform(patch("/hotels/{id}", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Hotel\", \"address\": \"Updated Address\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(hotelService, times(1)).updateHotel(eq(1), any(Hotel.class));
    }


    @Test
    void HotelController_DeleteHotelById_ReturnNoContent() throws Exception {
        doNothing().when(hotelService).deleteHotelById(hotel.getId());  // Mocking the service method call

        ResultActions response = mockMvc.perform(delete("/hotels/{id}", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void HotelController_DeleteNotExistingHotel_ReturnNotFound() throws Exception {
        doThrow(new HotelNotFoundException("exceptio")).when(hotelService).deleteHotelById(hotel.getId());  // Mocking the service method call

        mockMvc.perform(delete("/hotels/{id}", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
