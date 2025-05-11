import java.util.*;

public class Library {
    private List<Book> books;
    private Set<String> authors;
    private Map<String, Integer> authorBookCount;

    public Library() {
        books = new ArrayList<>();
        authors = new HashSet<>();
        authorBookCount = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        authors.add(book.getAuthor());
        authorBookCount.merge(book.getAuthor(), 1, Integer::sum);
    }

    public void removeBook(Book book) {
        if (books.remove(book)) {
            String author = book.getAuthor();
            int count = authorBookCount.get(author) - 1;
            if (count == 0) {
                authors.remove(author);
                authorBookCount.remove(author);
            } else {
                authorBookCount.put(author, count);
            }
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findBooksByYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getYear() == year) {
                result.add(book);
            }
        }
        return result;
    }

    public void printAllBooks() {
        System.out.println("Все книги в библиотеке:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void printUniqueAuthors() {
        System.out.println("Уникальные авторы:");
        for (String author : authors) {
            System.out.println(author);
        }
    }

    public void printAuthorStatistics() {
        System.out.println("Статистика по авторам:");
        for (Map.Entry<String, Integer> entry : authorBookCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " книг");
        }
    }
} 