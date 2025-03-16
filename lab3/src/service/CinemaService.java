package service;

import models.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CinemaService {
    private List<Cinema> cinemas;
    private List<Movie> movies;

    public CinemaService() {
        this.cinemas = new ArrayList<>();
        this.movies = new ArrayList<>();
    }

    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public MovieSession findNextAvailableSession(String movieTitle) {
        LocalDateTime now = LocalDateTime.now();
        MovieSession nextSession = null;

        for (Cinema cinema : cinemas) {
            for (CinemaHall hall : cinema.getHalls()) {
                for (MovieSession session : hall.getSessions()) {
                    if (session.getMovie().getTitle().equals(movieTitle) &&
                        session.getStartTime().isAfter(now)) {
                        // Проверяем наличие свободных мест
                        boolean hasAvailableSeats = false;
                        for (Seat[] row : hall.getSeats()) {
                            for (Seat seat : row) {
                                if (!seat.isOccupied()) {
                                    hasAvailableSeats = true;
                                    break;
                                }
                            }
                            if (hasAvailableSeats) break;
                        }

                        if (hasAvailableSeats && (nextSession == null || 
                            session.getStartTime().isBefore(nextSession.getStartTime()))) {
                            nextSession = session;
                        }
                    }
                }
            }
        }
        return nextSession;
    }

    public boolean bookSeat(Cinema cinema, CinemaHall hall, MovieSession session, int row, int seat) {
        if (row < 1 || row > hall.getSeats().length || 
            seat < 1 || seat > hall.getSeats()[0].length) {
            return false;
        }

        Seat targetSeat = hall.getSeats()[row - 1][seat - 1];
        if (targetSeat.isOccupied()) {
            return false;
        }

        targetSeat.setOccupied(true);
        return true;
    }

    public void displayAllCinemas() {
        if (cinemas.isEmpty()) {
            System.out.println("Нет доступных кинотеатров");
            return;
        }
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinemas.get(i));
        }
    }

    public void displayAllMovies() {
        if (movies.isEmpty()) {
            System.out.println("Нет доступных фильмов");
            return;
        }
        for (int i = 0; i < movies.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, movies.get(i));
        }
    }

    public void displayAllScreenings() {
        boolean found = false;
        for (Cinema cinema : cinemas) {
            for (CinemaHall hall : cinema.getHalls()) {
                if (!hall.getSessions().isEmpty()) {
                    System.out.printf("Кинотеатр: %s, Зал: %s%n", cinema.getName(), hall.getName());
                    for (MovieSession session : hall.getSessions()) {
                        System.out.println(session);
                    }
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("Нет доступных сеансов");
        }
    }

    public Cinema getCinemaById(int id) {
        if (id > 0 && id <= cinemas.size()) {
            return cinemas.get(id - 1);
        }
        return null;
    }

    public Movie getMovieById(int id) {
        if (id > 0 && id <= movies.size()) {
            return movies.get(id - 1);
        }
        return null;
    }

    public void createScreening(Cinema cinema, CinemaHall hall, Movie movie, String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        MovieSession session = new MovieSession(movie, dateTime, 500.0); // Стандартная цена
        hall.addSession(session);
    }
} 