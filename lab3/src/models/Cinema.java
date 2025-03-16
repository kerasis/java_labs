package models;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String name;
    private String address;
    private List<CinemaHall> halls;

    public Cinema(String name, String address) {
        this.name = name;
        this.address = address;
        this.halls = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<CinemaHall> getHalls() {
        return halls;
    }

    public void addHall(CinemaHall hall) {
        halls.add(hall);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, address);
    }
} 