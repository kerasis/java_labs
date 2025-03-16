import models.*;
import service.CinemaService;
import auth.AuthService;
// import ui.AdminInterface;
// import ui.UserInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final CinemaService cinemaService = new CinemaService();
    private static AuthService.User currentUser = null;

    public static void main(String[] args) {
        initializeTestData();

        while (true) {
            if (currentUser == null) {
                authenticateUser();
            } else if (currentUser.isAdmin()) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void authenticateUser() {
        System.out.println("\n=== Авторизация ===");
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        currentUser = authService.authenticate(username, password);
        if (currentUser == null) {
            System.out.println("Неверный логин или пароль!");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== Меню администратора ===");
        System.out.println("1. Добавить кинотеатр");
        System.out.println("2. Добавить зал в кинотеатр");
        System.out.println("3. Добавить фильм");
        System.out.println("4. Создать сеанс");
        System.out.println("5. Выйти из аккаунта");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                addCinema();
                break;
            case "2":
                addHall();
                break;
            case "3":
                addMovie();
                break;
            case "4":
                createSession();
                break;
            case "5":
                currentUser = null;
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n=== Меню пользователя ===");
        System.out.println("1. Найти ближайший сеанс фильма");
        System.out.println("2. Купить билет");
        System.out.println("3. Выйти из аккаунта");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                findNextSession();
                break;
            case "2":
                buyTicket();
                break;
            case "3":
                currentUser = null;
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }

    private static void addCinema() {
        System.out.print("Введите название кинотеатра: ");
        String name = scanner.nextLine();
        System.out.print("Введите адрес кинотеатра: ");
        String address = scanner.nextLine();

        Cinema cinema = new Cinema(name, address);
        cinemaService.addCinema(cinema);
        System.out.println("Кинотеатр успешно добавлен!");
    }

    private static void addHall() {
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Сначала добавьте кинотеатр!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemaService.getCinemas().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinemaService.getCinemas().get(i));
        }

        int cinemaIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (cinemaIndex < 0 || cinemaIndex >= cinemaService.getCinemas().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        System.out.print("Введите название зала: ");
        String name = scanner.nextLine();
        System.out.print("Введите количество рядов: ");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите количество мест в ряду: ");
        int seats = Integer.parseInt(scanner.nextLine());

        CinemaHall hall = new CinemaHall(name, rows, seats);
        cinemaService.getCinemas().get(cinemaIndex).addHall(hall);
        System.out.println("Зал успешно добавлен!");
    }

    private static void addMovie() {
        System.out.print("Введите название фильма: ");
        String title = scanner.nextLine();
        System.out.print("Введите продолжительность (в минутах): ");
        int duration = Integer.parseInt(scanner.nextLine());

        Movie movie = new Movie(title, duration);
        cinemaService.addMovie(movie);
        System.out.println("Фильм успешно добавлен!");
    }

    private static void createSession() {
        if (cinemaService.getMovies().isEmpty() || cinemaService.getCinemas().isEmpty()) {
            System.out.println("Сначала добавьте фильмы и кинотеатры!");
            return;
        }

        System.out.println("Выберите фильм:");
        for (int i = 0; i < cinemaService.getMovies().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinemaService.getMovies().get(i));
        }

        int movieIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (movieIndex < 0 || movieIndex >= cinemaService.getMovies().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemaService.getCinemas().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinemaService.getCinemas().get(i));
        }

        int cinemaIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (cinemaIndex < 0 || cinemaIndex >= cinemaService.getCinemas().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        Cinema cinema = cinemaService.getCinemas().get(cinemaIndex);
        if (cinema.getHalls().isEmpty()) {
            System.out.println("В этом кинотеатре нет залов!");
            return;
        }

        System.out.println("Выберите зал:");
        for (int i = 0; i < cinema.getHalls().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinema.getHalls().get(i).getName());
        }

        int hallIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (hallIndex < 0 || hallIndex >= cinema.getHalls().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        System.out.print("Введите дату и время сеанса (формат: dd.MM.yyyy HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, 
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        System.out.print("Введите цену билета: ");
        double price = Double.parseDouble(scanner.nextLine());

        MovieSession session = new MovieSession(cinemaService.getMovies().get(movieIndex), 
            dateTime, price);
        cinema.getHalls().get(hallIndex).addSession(session);
        System.out.println("Сеанс успешно создан!");
    }

    private static void findNextSession() {
        if (cinemaService.getMovies().isEmpty()) {
            System.out.println("Нет доступных фильмов!");
            return;
        }

        System.out.println("Выберите фильм:");
        for (int i = 0; i < cinemaService.getMovies().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinemaService.getMovies().get(i));
        }

        int movieIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (movieIndex < 0 || movieIndex >= cinemaService.getMovies().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        Movie movie = cinemaService.getMovies().get(movieIndex);
        MovieSession nextSession = cinemaService.findNextAvailableSession(movie.getTitle());

        if (nextSession == null) {
            System.out.println("Нет доступных сеансов для этого фильма!");
        } else {
            System.out.printf("Ближайший доступный сеанс: %s%n", nextSession);
        }
    }

    private static void buyTicket() {
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Нет доступных кинотеатров!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemaService.getCinemas().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinemaService.getCinemas().get(i));
        }

        int cinemaIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (cinemaIndex < 0 || cinemaIndex >= cinemaService.getCinemas().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        Cinema cinema = cinemaService.getCinemas().get(cinemaIndex);
        if (cinema.getHalls().isEmpty()) {
            System.out.println("В этом кинотеатре нет залов!");
            return;
        }

        System.out.println("Выберите зал:");
        for (int i = 0; i < cinema.getHalls().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinema.getHalls().get(i).getName());
        }

        int hallIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (hallIndex < 0 || hallIndex >= cinema.getHalls().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        CinemaHall hall = cinema.getHalls().get(hallIndex);
        if (hall.getSessions().isEmpty()) {
            System.out.println("В этом зале нет сеансов!");
            return;
        }

        System.out.println("Выберите сеанс:");
        for (int i = 0; i < hall.getSessions().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, hall.getSessions().get(i));
        }

        int sessionIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (sessionIndex < 0 || sessionIndex >= hall.getSessions().size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        MovieSession session = hall.getSessions().get(sessionIndex);
        System.out.println("\nПлан зала:");
        System.out.println(hall.printSeatingPlan(session));

        System.out.print("Введите номер ряда: ");
        int row = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите номер места: ");
        int seat = Integer.parseInt(scanner.nextLine());

        if (cinemaService.bookSeat(cinema, hall, session, row, seat)) {
            System.out.printf("Билет успешно куплен! Цена: %.2f руб.%n", session.getPrice());
        } else {
            System.out.println("Не удалось забронировать место. Возможно, оно уже занято или не существует.");
        }
    }

    private static void initializeTestData() {
        //  тестовый кинотеатр
        Cinema cinema = new Cinema("Космос", "ул. Ленина, 1");
        cinemaService.addCinema(cinema);

        //  зал
        CinemaHall hall = new CinemaHall("Зал 1", 5, 10);
        cinema.addHall(hall);

        //  фильм
        Movie movie = new Movie("Звездные войны", 120);
        cinemaService.addMovie(movie);

        // сеанс
        LocalDateTime sessionTime = LocalDateTime.now().plusHours(2);
        MovieSession session = new MovieSession(movie, sessionTime, 500.0);
        hall.addSession(session);
    }
}