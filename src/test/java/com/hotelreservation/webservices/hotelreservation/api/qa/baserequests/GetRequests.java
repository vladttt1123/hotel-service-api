package com.hotelreservation.webservices.hotelreservation.api.qa.baserequests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequests {

    private final RequestSpecification requestSpec;

    public GetRequests(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getWithTokenAndHeader(String endpoint, int expectedStatusCode) {
        return RestAssured.given(requestSpec)
                .header("Content-Type", "application/json")
                .when()
                .get(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }
}
