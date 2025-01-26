package com.hotelreservation.webservices.hotelreservation.api.qa.baserequests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteRequests {

    private final RequestSpecification requestSpec;

    public DeleteRequests(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response deleteWithTokenAndHeader(String endpoint, int expectedStatusCode) {
        return RestAssured.given(requestSpec)
                .header("Content-Type", "application/json")
                .when()
                .delete(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
    }
}
