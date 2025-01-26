package com.hotelreservation.webservices.hotelreservation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotelreservation.webservices.hotelreservation.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"roomNumber", "hotel_id"}),
        indexes = @Index(name = "idx_room_hotel", columnList = "hotel_id"))
public class Room {

    @Id
    @GeneratedValue
    private Integer id;

    @Min(value = 1, message = "Room number should be a positive number")
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Hotel hotel;


    public Room(Integer id, Integer roomNumber, RoomType roomType, Boolean isAvailable, Hotel hotel) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
    }

    public Room() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", isAvailable=" + isAvailable +
                ", hotel=" + hotel +
                '}';
    }
}
