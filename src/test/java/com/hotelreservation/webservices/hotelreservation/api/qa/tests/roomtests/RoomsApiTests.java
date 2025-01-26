package com.hotelreservation.webservices.hotelreservation.api.qa.tests.roomtests;

import com.hotelreservation.webservices.hotelreservation.api.qa.core.BaseTest;
import com.hotelreservation.webservices.hotelreservation.api.qa.requests.HotelRequests;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomsApiTests extends BaseTest {

    private HotelRequests hotelRequests;
    private final Map<String, Object> jsonBody = new HashMap<>();


    @BeforeEach
    public void initBeforeEach() {
        hotelRequests = new HotelRequests(requestSpec);
    }


    @Test
    void getAllRoomsInHotelExpects200() {
        Response response = hotelRequests.getAllRoomsInHotel(1000, 200);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/getAllRoomsInHotelResponseSchema.json"));
    }

    @Test
    void getHotelRoomExpects200() {
        Response response = hotelRequests.getRoomInHotelById(1000, 101, 200);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/getRoomInHotelByIdResponseSchema.json"));
    }

    @Test
    void addRoomToTheHotelExpects201() {

        jsonBody.clear();
        jsonBody.put("roomNumber", 50);
        jsonBody.put("roomType", "DOUBLE");
        jsonBody.put("available", true);

        Response response = hotelRequests.createRoomInTheHotel(jsonBody, 1000, 201);
        response.then()
                .assertThat().body(matchesJsonSchemaInClasspath("responseSchemas/createRoomInTheHotelResponseSchema.json"));

        int roomId = response.jsonPath().getInt("id");
        hotelRequests.deleteHotelRoom(1000, roomId, 204);

    }

    @Test
    void updateRoomInHotelExpects204() {

        jsonBody.clear();
        jsonBody.put("roomNumber", 76);
        jsonBody.put("roomType", "DOUBLE");
        jsonBody.put("available", true);

        Response response = hotelRequests.createRoomInTheHotel(jsonBody, 1000, 201);
        int roomId = response.jsonPath().getInt("id");

        jsonBody.clear();
        jsonBody.put("available", false);

        hotelRequests.updateHotelRoom(1000, roomId, jsonBody, 204);
        response = hotelRequests.getRoomInHotelById(1000, roomId, 200);
        Assertions.assertEquals("DOUBLE", response.jsonPath().getString("roomType"));
        Assertions.assertFalse(response.jsonPath().getBoolean("available"));

        jsonBody.clear();
        jsonBody.put("roomType", "SINGLE");
        hotelRequests.updateHotelRoom(1000, roomId, jsonBody, 204);
        response = hotelRequests.getRoomInHotelById(1000, roomId, 200);
        Assertions.assertEquals("SINGLE", response.jsonPath().getString("roomType"));
        Assertions.assertFalse(response.jsonPath().getBoolean("available"));


        hotelRequests.deleteHotelRoom(1000, roomId, 204);
    }

    @Test
    void updateHotelRoomHotelIdNotExistsExpect404() {


        Response response = hotelRequests.updateHotelRoom(9999, 9999, jsonBody, 404);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
    }

    @Test
    void updateHotelRoomRoomIdNotExistsExpect404() {

        Response response = hotelRequests.updateHotelRoom(1000, 9999, jsonBody, 404);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
    }


}
