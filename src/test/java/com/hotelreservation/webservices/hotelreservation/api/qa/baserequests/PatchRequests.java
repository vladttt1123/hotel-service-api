package com.hotelreservation.webservices.hotelreservation.api.qa.baserequests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PatchRequests {

    private final RequestSpecification requestSpec;

    public PatchRequests(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response patchWithTokenAndHeader(String endpoint, Object body, int expectedStatusCode) {
        return RestAssured.given(requestSpec)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .statusCode(expectedStatusCode).extract().response();
    }
}
