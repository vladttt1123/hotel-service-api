package com.hotelreservation.webservices.hotelreservation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, max = 100, message = "Hotel name should have at least 2 characters but not more than 100 characters")
    private String name;

    @Size(min = 2, max = 100, message = "Hotel city should have at least 2 characters but not more than 100 characters")
    private String city;

    @Size(min = 2, max = 100, message = "Hotel address should have at least 2 characters but not more than 100 characters")
    private String address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Room> rooms;


    public Hotel(Integer id, String name, String city, String address, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.rooms = rooms;
    }

    public Hotel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
