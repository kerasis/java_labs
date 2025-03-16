package models;

import java.time.LocalDateTime;

public class MovieSession {
    private Movie movie;
    private LocalDateTime startTime;
    private double price;

    public MovieSession(Movie movie, LocalDateTime startTime, double price) {
        this.movie = movie;
        this.startTime = startTime;
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Цена: %.2f руб.)", 
            movie.getTitle(), 
            startTime.toString(), 
            price);
    }
} 