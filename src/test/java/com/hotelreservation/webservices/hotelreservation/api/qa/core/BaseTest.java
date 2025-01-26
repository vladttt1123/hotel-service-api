package com.hotelreservation.webservices.hotelreservation.api.qa.core;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected RequestSpecification requestSpec;

    @BeforeEach
    public void setup() {
        // Initialize the RequestSpecification with shared configurations
        requestSpec = new RequestSpecBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .setAuth(io.restassured.RestAssured.basic("user", "password"))
                .build();
    }


}
