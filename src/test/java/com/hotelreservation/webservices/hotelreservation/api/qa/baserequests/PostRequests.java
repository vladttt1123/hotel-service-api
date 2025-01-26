package com.hotelreservation.webservices.hotelreservation.api.qa.baserequests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostRequests {

    private final RequestSpecification requestSpec;

    public PostRequests(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response postWithTokenAndHeader(String endpoint, Object body, int expectedStatusCode) {
        return RestAssured.given(requestSpec)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

}
