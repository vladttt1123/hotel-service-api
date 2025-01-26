package com.hotelreservation.webservices.hotelreservation.api.qa.tests.hotelstests;

import com.hotelreservation.webservices.hotelreservation.api.qa.core.BaseTest;
import com.hotelreservation.webservices.hotelreservation.api.qa.requests.HotelRequests;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class HotelApiTests extends BaseTest {

    private static HotelRequests hotelRequests;
    //private static int hotelId;
    private final Map<String, Object> jsonBody = new HashMap<>();


    @BeforeEach
    public void initBeforeEach() {
        hotelRequests = new HotelRequests(requestSpec);
    }

    @Test
    void testGetAllHotelsExpects200() {

        Response response = hotelRequests.getAllHotels(200);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/getAllHotelsResponseSchema.json"));

    }

    @Test
    void testGetHotelByIdExpects200() {

        Response response = hotelRequests.getHotelById(1000, 200);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/getHotelByIdResponseSchema.json"));

    }

    @Test
    void createHotelExpects201() {

        jsonBody.clear();
        jsonBody.put("name", "AC Hotel");
        jsonBody.put("city", "krakow");
        jsonBody.put("address", "al 3 maja");

        Response response = hotelRequests.createHotel(jsonBody, 201);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/createHotelResponseSchema.json"));

        int hotelId = response.jsonPath().getInt("id");
        hotelRequests.deleteHotel(hotelId, 204);
    }

    @Test
    void createHotelWithInvalidNameExpects400() {

        String[] invalidValues = {"A", "A".repeat(101)};

        for (String value : invalidValues) {
            jsonBody.clear();
            jsonBody.put("name", value);
            jsonBody.put("city", "Valid City");
            jsonBody.put("address", "Valid Address");

            Response response = hotelRequests.createHotel(jsonBody, 400);
            response.then().
                    assertThat().
                    body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
        }

    }

    @Test
    void createHotelWithInvalidCityExpects400() {

        String[] invalidValues = {"A", "A".repeat(101)};

        for (String value : invalidValues) {
            jsonBody.clear();
            jsonBody.put("name", "Valid name");
            jsonBody.put("city", value);
            jsonBody.put("address", "Valid Address");

            Response response = hotelRequests.createHotel(jsonBody, 400);
            response.then().
                    assertThat().
                    body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
        }

    }

    @Test
    void createHotelWithInvalidAddressExpects400() {

        String[] invalidValues = {"A", "A".repeat(101)};

        for (String value : invalidValues) {
            jsonBody.clear();
            jsonBody.put("name", "valid name");
            jsonBody.put("city", "Valid City");
            jsonBody.put("address", value);

            Response response = hotelRequests.createHotel(jsonBody, 400);
            response.then().
                    assertThat().
                    body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
        }

    }

    @Test
    void deleteNotExistingHotelExpects404() {

        Response response = hotelRequests.deleteHotel(9999, 404);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));

    }

    @Test
    void getNotExistingHotelExpects404() {
        Response response = hotelRequests.getHotelById(9999, 404);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
    }

    @Test
    void updateHotelChangeNameExpects204() {

        jsonBody.clear();
        jsonBody.put("name", "New Name");
        jsonBody.put("city", "krakow");
        jsonBody.put("address", "al 3 maja");

        Response response = hotelRequests.createHotel(jsonBody, 201);
        int hotelId = response.jsonPath().getInt("id");

        jsonBody.clear();
        jsonBody.put("name", "Updated Name");

        // update name
        hotelRequests.updateHotel(hotelId, jsonBody, 204);
        response = hotelRequests.getHotelById(hotelId, 200);
        response.then().
                assertThat()
                .body("name", equalTo("Updated Name"))
                .body("city", equalTo("krakow"))
                .body("address", equalTo("al 3 maja"));

        hotelRequests.deleteHotel(hotelId, 204);
    }

    @Test
    void updateHotelChangeAddressExpects204() {

        jsonBody.clear();
        jsonBody.put("name", "New Name");
        jsonBody.put("city", "krakow");
        jsonBody.put("address", "al 3 maja");

        Response response = hotelRequests.createHotel(jsonBody, 201);
        int hotelId = response.jsonPath().getInt("id");

        // update address
        jsonBody.clear();
        jsonBody.put("address", "Updated Address");
        hotelRequests.updateHotel(hotelId, jsonBody, 204);
        response = hotelRequests.getHotelById(hotelId, 200);
        response.then().
                assertThat()
                .body("name", equalTo("New Name"))
                .body("city", equalTo("krakow"))
                .body("address", equalTo("Updated Address"));


        hotelRequests.deleteHotel(hotelId, 204);
    }

    @Test
    void updateNotExistingHotelExpects404() {

        jsonBody.clear();
        jsonBody.put("name", "New Name");

        Response response = hotelRequests.updateHotel(9999, jsonBody, 404);
        response.then().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseSchemas/errorResponseSchema.json"));
    }
}
