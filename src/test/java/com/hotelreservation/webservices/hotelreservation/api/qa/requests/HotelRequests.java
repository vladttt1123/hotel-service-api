package com.hotelreservation.webservices.hotelreservation.api.qa.requests;

import com.hotelreservation.webservices.hotelreservation.api.qa.baserequests.DeleteRequests;
import com.hotelreservation.webservices.hotelreservation.api.qa.baserequests.GetRequests;
import com.hotelreservation.webservices.hotelreservation.api.qa.baserequests.PatchRequests;
import com.hotelreservation.webservices.hotelreservation.api.qa.baserequests.PostRequests;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HotelRequests {

    private final GetRequests getRequests;
    private final PostRequests postRequests;
    private final DeleteRequests deleteRequests;
    private final PatchRequests patchRequests;


    public HotelRequests(RequestSpecification requestSpec) {
        this.getRequests = new GetRequests(requestSpec);
        this.postRequests = new PostRequests(requestSpec);
        this.deleteRequests = new DeleteRequests(requestSpec);
        this.patchRequests = new PatchRequests(requestSpec);
    }


    public Response getAllHotels(int expectedStatusCode) {
        return getRequests.getWithTokenAndHeader("/hotels", expectedStatusCode);
    }

    public Response getHotelById(int hotelId, int expectedStatusCode) {
        return getRequests.getWithTokenAndHeader("/hotels/" + hotelId, expectedStatusCode);
    }

    public Response getAllRoomsInHotel(int hotelId, int expectedStatusCode) {
        return getRequests.getWithTokenAndHeader("/hotels/" + hotelId + "/rooms", expectedStatusCode);
    }

    public Response getRoomInHotelById(int hotelId, int roomId, int expectedStatusCode) {
        return getRequests.getWithTokenAndHeader("/hotels/" + hotelId + "/rooms/" + roomId, expectedStatusCode);
    }

    public Response createHotel(Object jsonBody, int expectedStatusCode) {
        return postRequests.postWithTokenAndHeader("/hotels", jsonBody, expectedStatusCode);
    }

    public Response createRoomInTheHotel(Object jsonBody, int hotelId, int expectedStatusCode) {

        return postRequests.postWithTokenAndHeader("/hotels/" + hotelId + "/rooms", jsonBody, expectedStatusCode);
    }

    public Response deleteHotel(int hotelId, int expectedStatusCode) {
        return deleteRequests.deleteWithTokenAndHeader("/hotels/" + hotelId, expectedStatusCode);
    }

    public Response deleteHotelRoom(int hotelId, int roomId, int expectedStatusCode) {
        return deleteRequests.deleteWithTokenAndHeader("/hotels/" + hotelId + "/rooms/" + roomId, expectedStatusCode);
    }

    public Response updateHotel(int hotelId, Object jsonBody, int expectedStatusCode) {
        return patchRequests.patchWithTokenAndHeader("/hotels/" + hotelId, jsonBody, expectedStatusCode);
    }

    public Response updateHotelRoom(int hotelId, int roomId, Object jsonBody, int expectedStatusCode) {
        return patchRequests.patchWithTokenAndHeader("/hotels/" + hotelId + "/rooms/" + roomId, jsonBody, expectedStatusCode);
    }


}
