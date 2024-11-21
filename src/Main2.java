import java.util.*;
import java.util.stream.Collectors;

class LibraryBook {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean isAvailable;

    public LibraryBook(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}

class LibraryReader {
    private String firstName;
    private String lastName;
    private String ticketNumber;

    public LibraryReader(String firstName, String lastName, String ticketNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ticketNumber = ticketNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}

interface LibraryCatalogInterface {
    void addBook(LibraryBook book);
    List<LibraryBook> searchByTitle(String title);
    List<LibraryBook> searchByAuthor(String author);
    List<LibraryBook> searchByGenre(String genre);
}

class LibraryCatalog implements LibraryCatalogInterface {
    private List<LibraryBook> books;

    public LibraryCatalog() {
        this.books = new ArrayList<>();
    }

    public void addBook(LibraryBook book) {
        books.add(book);
    }

    public List<LibraryBook> searchByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<LibraryBook> searchByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<LibraryBook> searchByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().toLowerCase().equals(genre.toLowerCase()))
                .collect(Collectors.toList());
    }
}

interface LibraryLoanSystemInterface {
    void issueBook(LibraryBook book, LibraryReader reader);
    void returnBook(LibraryBook book, LibraryReader reader);
}

class LibraryLoanSystem implements LibraryLoanSystemInterface {
    private Map<LibraryBook, LibraryReader> loanRecords;

    public LibraryLoanSystem() {
        this.loanRecords = new HashMap<>();
    }

    public void issueBook(LibraryBook book, LibraryReader reader) {
        if (book.isAvailable()) {
            book.setAvailability(false);
            loanRecords.put(book, reader);
            System.out.println(reader.getFullName() + " взял(а) книгу: " + book.getTitle());
        } else {
            System.out.println("Книга недоступна: " + book.getTitle());
        }
    }

    public void returnBook(LibraryBook book, LibraryReader reader) {
        if (loanRecords.containsKey(book) && loanRecords.get(book).equals(reader)) {
            book.setAvailability(true);
            loanRecords.remove(book);
            System.out.println(reader.getFullName() + " вернул(а) книгу: " + book.getTitle());
        } else {
            System.out.println("Ошибка при возврате книги: " + book.getTitle());
        }
    }
}

class LibraryLibrarian {
    private String name;
    private LibraryCatalogInterface catalog;
    private LibraryLoanSystemInterface loanSystem;

    public LibraryLibrarian(String name, LibraryCatalogInterface catalog, LibraryLoanSystemInterface loanSystem) {
        this.name = name;
        this.catalog = catalog;
        this.loanSystem = loanSystem;
    }

    public void addBookToCatalog(LibraryBook book) {
        catalog.addBook(book);
        System.out.println("Книга добавлена в каталог: " + book.getTitle());
    }

    public void issueBook(LibraryBook book, LibraryReader reader) {
        loanSystem.issueBook(book, reader);
    }

    public void returnBook(LibraryBook book, LibraryReader reader) {
        loanSystem.returnBook(book, reader);
    }
}

public class Main2 {
    public static void main(String[] args) {
        LibraryCatalog catalog = new LibraryCatalog();
        LibraryLoanSystem loanSystem = new LibraryLoanSystem();
        LibraryLibrarian librarian = new LibraryLibrarian("Анна", catalog, loanSystem);

        LibraryBook book1 = new LibraryBook("Преступление и наказание", "Достоевский", "Роман", "123456");
        LibraryBook book2 = new LibraryBook("Война и мир", "Толстой", "Роман", "654321");
        librarian.addBookToCatalog(book1);
        librarian.addBookToCatalog(book2);

        LibraryReader reader = new LibraryReader("Иван", "Петров", "R12345");

        List<LibraryBook> foundBooks = catalog.searchByAuthor("Толстой");
        System.out.println("Найденные книги:");
        for (LibraryBook book : foundBooks) {
            System.out.println("- " + book.getTitle());
        }

        librarian.issueBook(book1, reader);
        librarian.returnBook(book1, reader);
    }
}
