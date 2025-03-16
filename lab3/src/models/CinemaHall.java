package models;

import java.util.ArrayList;
import java.util.List;

public class CinemaHall {
    private String name;
    private Seat[][] seats;
    private List<MovieSession> sessions;

    public CinemaHall(String name, int rows, int seatsPerRow) {
        this.name = name;
        this.seats = new Seat[rows][seatsPerRow];
        this.sessions = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = new Seat(i + 1, j + 1);
            }
        }
    }

    public String getName() {
        return name;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public List<MovieSession> getSessions() {
        return sessions;
    }

    public void addSession(MovieSession session) {
        sessions.add(session);
    }

    public String printSeatingPlan(MovieSession session) {
        StringBuilder plan = new StringBuilder();
        plan.append("План зала для сеанса: ").append(session.toString()).append("\n");
        plan.append("   ");
        
        for (int j = 0; j < seats[0].length; j++) {
            plan.append(String.format("%3d ", j + 1));
        }
        plan.append("\n");

        for (int i = 0; i < seats.length; i++) {
            plan.append(String.format("%2d ", i + 1));
            for (int j = 0; j < seats[i].length; j++) {
                plan.append(seats[i][j].isOccupied() ? " [X] " : " [ ] ");
            }
            plan.append("\n");
        }
        return plan.toString();
    }
} 