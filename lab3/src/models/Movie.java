package models;

public class Movie {
    private String title;
    private int duration; // в минутах

    public Movie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("%s (Продолжительность: %d мин.)", title, duration);
    }
} 