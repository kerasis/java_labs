public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // Создаем несколько книг
        Book book1 = new Book("Война и мир", "Лев Толстой", 1869);
        Book book2 = new Book("Анна Каренина", "Лев Толстой", 1877);
        Book book3 = new Book("Преступление и наказание", "Федор Достоевский", 1866);
        Book book4 = new Book("Идиот", "Федор Достоевский", 1869);
        Book book5 = new Book("Отцы и дети", "Иван Тургенев", 1862);

        // Добавляем книги в библиотеку
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        // Выводим все книги
        library.printAllBooks();
        System.out.println();

        // Выводим уникальных авторов
        library.printUniqueAuthors();
        System.out.println();

        // Выводим статистику по авторам
        library.printAuthorStatistics();
        System.out.println();

        // Ищем книги по автору
        System.out.println("Книги Льва Толстого:");
        for (Book book : library.findBooksByAuthor("Лев Толстой")) {
            System.out.println(book);
        }
        System.out.println();

        // Ищем книги по году
        System.out.println("Книги 1869 года:");
        for (Book book : library.findBooksByYear(1869)) {
            System.out.println(book);
        }
        System.out.println();

        // Удаляем книгу
        library.removeBook(book1);
        System.out.println("После удаления книги 'Война и мир':");
        library.printAllBooks();
        System.out.println();

        // Обновляем статистику
        library.printAuthorStatistics();
    }
} 