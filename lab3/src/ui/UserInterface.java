package ui;

import models.Movie;
import models.MovieSession;
import auth.AuthService.User;
import service.CinemaService;
import utils.InputUtils;
import models.*;
import java.util.Scanner;

public class UserInterface {
    private final CinemaService cinemaService;
    private final Scanner scanner;
    private final User user;
    
    public UserInterface(CinemaService cinemaService, Scanner scanner, User user) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
        this.user = user;
    }
    
    public void showMenu() {
        boolean userSession = true;
        
        while (userSession) {
            System.out.println("\n===== МЕНЮ ПОЛЬЗОВАТЕЛЯ =====");
            System.out.println("1. Поиск ближайшего сеанса фильма");
            System.out.println("2. Просмотр всех фильмов");
            System.out.println("3. Просмотр всех сеансов фильма");
            System.out.println("4. Показать план зала для сеанса");
            System.out.println("5. Купить билет");
            System.out.println("6. Просмотреть мои билеты");
            System.out.println("0. Выход в главное меню");
            System.out.print("Выберите опцию: ");
            
            int choice = InputUtils.getIntInput(scanner);
            
            switch (choice) {
                case 1:
                    findNextScreening();
                    break;
                case 2:
                    cinemaService.displayAllMovies();
                    break;
                case 3:
                    findMovieScreenings();
                    break;
                case 4:
                    showHallPlan();
                    break;
                case 5:
                    buyTicket();
                    break;
                case 6:
                    
                    System.out.println("Функционал просмотра билетов находится в разработке");
                    break;
                case 0:
                    userSession = false;
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }
    
    private void findNextScreening() {
        cinemaService.displayAllMovies();
        
        if (cinemaService.getMovies().isEmpty()) {
            System.out.println("В системе нет фильмов.");
            return;
        }
        
        System.out.print("Введите ID фильма: ");
        int movieId = InputUtils.getIntInput(scanner);
        
        Movie movie = cinemaService.getMovieById(movieId);
        if (movie == null) {
            System.out.println("Фильм не найден.");
            return;
        }
        
        MovieSession nextScreening = cinemaService.findNextAvailableSession(movie.getTitle());
        if (nextScreening != null) {
            System.out.println("Ближайший доступный сеанс: " + nextScreening);
        } else {
            System.out.println("Нет доступных сеансов для этого фильма.");
        }
    }

    private void findMovieScreenings() {
        cinemaService.displayAllMovies();
    }

    private void showHallPlan() {
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Нет доступных кинотеатров!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        cinemaService.displayAllCinemas();
        
        int cinemaIndex = InputUtils.getIntInput(scanner) - 1;
        Cinema cinema = cinemaService.getCinemas().get(cinemaIndex);
        if (cinema == null) {
            System.out.println("Неверный выбор!");
            return;
        }

        if (cinema.getHalls().isEmpty()) {
            System.out.println("В этом кинотеатре нет залов!");
            return;
        }

        System.out.println("Выберите зал:");
        for (int i = 0; i < cinema.getHalls().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinema.getHalls().get(i).getName());
        }

        int hallIndex = InputUtils.getIntInput(scanner) - 1;
        CinemaHall hall = cinema.getHalls().get(hallIndex);
        if (hall == null) {
            System.out.println("Неверный выбор!");
            return;
        }

        if (hall.getSessions().isEmpty()) {
            System.out.println("В этом зале нет сеансов!");
            return;
        }

        System.out.println("Выберите сеанс:");
        for (int i = 0; i < hall.getSessions().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, hall.getSessions().get(i));
        }

        int sessionIndex = InputUtils.getIntInput(scanner) - 1;
        if (sessionIndex >= 0 && sessionIndex < hall.getSessions().size()) {
            MovieSession session = hall.getSessions().get(sessionIndex);
            System.out.println(hall.printSeatingPlan(session));
        }
    }

    private void buyTicket() {
        if (cinemaService.getCinemas().isEmpty()) {
            System.out.println("Нет доступных кинотеатров!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        cinemaService.displayAllCinemas();
        
        int cinemaIndex = InputUtils.getIntInput(scanner) - 1;
        Cinema cinema = cinemaService.getCinemas().get(cinemaIndex);
        if (cinema == null) {
            System.out.println("Неверный выбор!");
            return;
        }

        if (cinema.getHalls().isEmpty()) {
            System.out.println("В этом кинотеатре нет залов!");
            return;
        }

        System.out.println("Выберите зал:");
        for (int i = 0; i < cinema.getHalls().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, cinema.getHalls().get(i).getName());
        }

        int hallIndex = InputUtils.getIntInput(scanner) - 1;
        CinemaHall hall = cinema.getHalls().get(hallIndex);
        if (hall == null) {
            System.out.println("Неверный выбор!");
            return;
        }

        if (hall.getSessions().isEmpty()) {
            System.out.println("В этом зале нет сеансов!");
            return;
        }

        System.out.println("Выберите сеанс:");
        for (int i = 0; i < hall.getSessions().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, hall.getSessions().get(i));
        }

        int sessionIndex = InputUtils.getIntInput(scanner) - 1;
        MovieSession session = hall.getSessions().get(sessionIndex);
        if (session == null) {
            System.out.println("Неверный выбор!");
            return;
        }

        System.out.println("\nПлан зала:");
        System.out.println(hall.printSeatingPlan(session));

        System.out.print("Введите номер ряда: ");
        int row = InputUtils.getIntInput(scanner);
        System.out.print("Введите номер места: ");
        int seat = InputUtils.getIntInput(scanner);

        if (cinemaService.bookSeat(cinema, hall, session, row, seat)) {
            System.out.printf("Билет успешно куплен! Цена: %.2f руб.%n", session.getPrice());
        } else {
            System.out.println("Не удалось забронировать место. Возможно, оно уже занято или не существует.");
        }
    }
}