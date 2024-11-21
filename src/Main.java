import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Book {
    private String title;
    private String isbn;
    private List<Author> authors;
    private int publicationYear;
    private boolean isAvailable;

    public Book(String title, String isbn, List<Author> authors, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getBookInfo() {
        StringBuilder authorNames = new StringBuilder();
        for (Author author : authors) {
            if (authorNames.length() > 0) authorNames.append(", ");
            authorNames.append(author.getName());
        }
        return title + " (" + publicationYear + ") - " + authorNames;
    }
}

abstract class User {
    protected int id;
    protected String name;
    protected String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public abstract void performAction();
}

class Reader extends User {
    public Reader(int id, String name, String email) {
        super(id, name, email);
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.setAvailability(false);
            System.out.println(name + " взял(а) книгу: " + book.getTitle());
        } else {
            System.out.println("Книга недоступна: " + book.getTitle());
        }
    }

    @Override
    public void performAction() {
        System.out.println(name + " читает книги.");
    }
}

class Librarian extends User {
    public Librarian(int id, String name, String email) {
        super(id, name, email);
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
        System.out.println("Книга добавлена: " + book.getTitle());
    }

    @Override
    public void performAction() {
        System.out.println(name + " управляет библиотекой.");
    }
}

class Loan {
    private Book book;
    private Reader reader;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Book book, Reader reader, LocalDate loanDate) {
        this.book = book;
        this.reader = reader;
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
        book.setAvailability(true);
        System.out.println("Книга возвращена: " + book.getTitle());
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchBooks(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public void addUser(User user) {
        users.add(user);
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Librarian librarian = new Librarian(1, "Анна", "anna@example.com");
        Reader reader = new Reader(2, "Иван", "ivan@example.com");

        Author author1 = new Author("Достоевский");
        Author author2 = new Author("Толстой");

        List<Author> authors1 = new ArrayList<>();
        authors1.add(author1);
        List<Author> authors2 = new ArrayList<>();
        authors2.add(author2);

        Book book1 = new Book("Преступление и наказание", "123456", authors1, 1866);
        Book book2 = new Book("Война и мир", "654321", authors2, 1869);

        librarian.addBook(library, book1);
        librarian.addBook(library, book2);

        reader.borrowBook(book1);
        reader.borrowBook(book2);

        Loan loan = new Loan(book1, reader, LocalDate.now());
        loan.returnBook();

        List<Book> searchResults = library.searchBooks("Война");
        for (Book book : searchResults) {
            System.out.println("Найдено: " + book.getBookInfo());
        }
    }
}
