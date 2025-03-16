package ui;

import models.Cinema;
import models.CinemaHall;
import models.Movie;
//import models.MovieSession;
import service.CinemaService;
import utils.InputUtils;

import java.util.Scanner;

public class AdminInterface {
    private final CinemaService cinemaService;
    private final Scanner scanner;
    
    public AdminInterface(CinemaService cinemaService, Scanner scanner) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
    }
    
    public void showMenu() {
        boolean adminSession = true;
        
        while (adminSession) {
            System.out.println("\n===== МЕНЮ АДМИНИСТРАТОРА =====");
            System.out.println("1. Добавить кинотеатр");
            System.out.println("2. Добавить зал в кинотеатр");
            System.out.println("3. Настроить конфигурацию кресел в зале");
            System.out.println("4. Добавить фильм");
            System.out.println("5. Создать сеанс");
            System.out.println("6. Показать все кинотеатры");
            System.out.println("7. Показать все фильмы");
            System.out.println("8. Показать все сеансы");
            System.out.println("0. Выход в главное меню");
            System.out.print("Выберите опцию: ");
            
            int choice = InputUtils.getIntInput(scanner);
            
            switch (choice) {
                case 1:
                    addCinema();
                    break;
                case 2:
                    addHall();
                    break;
                case 3:
                    configureHallSeats();
                    break;
                case 4:
                    addMovie();
                    break;
                case 5:
                    createScreening();
                    break;
                case 6:
                    cinemaService.displayAllCinemas();
                    break;
                case 7:
                    cinemaService.displayAllMovies();
                    break;
                case 8:
                    cinemaService.displayAllScreenings();
                    break;
                case 0:
                    adminSession = false;
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }
    
    private void addCinema() {
        System.out.print("Введите название кинотеатра: ");
        String name = scanner.nextLine();
        System.out.print("Введите адрес кинотеатра: ");
        String address = scanner.nextLine();
        
        Cinema cinema = new Cinema(name, address);
        cinemaService.addCinema(cinema);
        System.out.println("Кинотеатр успешно добавлен.");
    }
    
    private void addHall() {
        cinemaService.displayAllCinemas();
        
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Сначала добавьте кинотеатр.");
            return;
        }
        
        System.out.print("Введите ID кинотеатра: ");
        int cinemaId = InputUtils.getIntInput(scanner);
        
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        if (cinema == null) {
            System.out.println("Кинотеатр не найден.");
            return;
        }
        
        System.out.print("Введите название зала: ");
        String name = scanner.nextLine();
        System.out.print("Введите количество рядов: ");
        int rows = InputUtils.getIntInput(scanner);
        System.out.print("Введите количество мест в ряду: ");
        int seats = InputUtils.getIntInput(scanner);
        
        CinemaHall hall = new CinemaHall(name, rows, seats);
        cinema.addHall(hall);
        System.out.println("Зал успешно добавлен.");
    }
    
    private void configureHallSeats() {
        cinemaService.displayAllCinemas();
        
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Сначала добавьте кинотеатр.");
            return;
        }
        
        System.out.print("Введите ID кинотеатра: ");
        int cinemaId = InputUtils.getIntInput(scanner);
        
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        if (cinema == null) {
            System.out.println("Кинотеатр не найден.");
            return;
        }
        
        System.out.println("Залы кинотеатра:");
        for (int i = 0; i < cinema.getHalls().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinema.getHalls().get(i).getName());
        }
        if (cinema.getHalls().isEmpty()) {
            System.out.println("Сначала добавьте зал в кинотеатр.");
            return;
        }
        
        System.out.print("Введите ID зала: ");
        int hallIndex = InputUtils.getIntInput(scanner) - 1;
        CinemaHall hall = hallIndex >= 0 && hallIndex < cinema.getHalls().size() ? 
            cinema.getHalls().get(hallIndex) : null;
        if (hall == null) {
            System.out.println("Зал не найден.");
            return;
        }
        
        if (hall.getSeats() == null || hall.getSeats().length == 0) {
            System.out.println("Сначала настройте конфигурацию кресел в зале.");
            return;
        }
        
        System.out.print("Введите количество рядов: ");
        int rows = InputUtils.getIntInput(scanner);
        
        if (rows <= 0) {
            System.out.println("Количество рядов должно быть положительным числом.");
            return;
        }
        
        int[] seatsPerRow = new int[rows];
        
        for (int i = 0; i < rows; i++) {
            System.out.print("Введите количество мест в ряду " + (i + 1) + ": ");
            int seats = InputUtils.getIntInput(scanner);
            
            if (seats <= 0) {
                System.out.println("Количество мест должно быть положительным числом.");
                return;
            }
            
            seatsPerRow[i] = seats;
        }
        
        CinemaHall newHall = new CinemaHall(hall.getName(), rows, seatsPerRow[0]);
        cinema.getHalls().set(hallIndex, newHall);
        System.out.println("Конфигурация кресел успешно настроена.");
    }
    
    private void addMovie() {
        System.out.print("Введите название фильма: ");
        String title = scanner.nextLine();
        System.out.print("Введите длительность фильма (в минутах): ");
        int duration = InputUtils.getIntInput(scanner);
        
        if (duration <= 0) {
            System.out.println("Длительность должна быть положительным числом.");
            return;
        }
        
        Movie movie = new Movie(title, duration);
        cinemaService.addMovie(movie);
        System.out.println("Фильм успешно добавлен.");
    }
    
    private void createScreening() {
        cinemaService.displayAllCinemas();
        
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Сначала добавьте кинотеатр.");
            return;
        }
        
        System.out.print("Введите ID кинотеатра: ");
        int cinemaId = InputUtils.getIntInput(scanner);
        
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        if (cinema == null) {
            System.out.println("Кинотеатр не найден.");
            return;
        }
        
        System.out.println("Залы кинотеатра:");
        for (int i = 0; i < cinema.getHalls().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinema.getHalls().get(i).getName());
        }
        if (cinema.getHalls().isEmpty()) {
            System.out.println("Сначала добавьте зал в кинотеатр.");
            return;
        }
        
        System.out.print("Введите ID зала: ");
        int hallIndex = InputUtils.getIntInput(scanner) - 1;
        CinemaHall hall = hallIndex >= 0 && hallIndex < cinema.getHalls().size() ? 
            cinema.getHalls().get(hallIndex) : null;
        if (hall == null) {
            System.out.println("Зал не найден.");
            return;
        }
        
        if (hall.getSeats() == null || hall.getSeats().length == 0) {
            System.out.println("Сначала настройте конфигурацию кресел в зале.");
            return;
        }
        
        cinemaService.displayAllMovies();
        if (cinemaService.getMovies().isEmpty()) {
            System.out.println("Сначала добавьте фильм.");
            return;
        }
        
        System.out.print("Введите ID фильма: ");
        int movieId = InputUtils.getIntInput(scanner);
        
        Movie movie = cinemaService.getMovieById(movieId);
        if (movie == null) {
            System.out.println("Фильм не найден.");
            return;
        }
        
        System.out.print("Введите дату и время начала сеанса (формат: dd.MM.yyyy HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        
        try {
            cinemaService.createScreening(cinema, hall, movie, dateTimeStr);
            System.out.println("Сеанс успешно создан.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}