
    import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

    class Book {
        private String title;
        private String author;
        private String ISBN;
        private String category;
        private boolean isIssued;

        public Book(String title, String author, String ISBN, String category) {
            this.title = title;
            this.author = author;
            this.ISBN = ISBN;
            this.category = category;
            this.isIssued = false;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getISBN() {
            return ISBN;
        }

        public String getCategory() {
            return category;
        }

        public boolean isIssued() {
            return isIssued;
        }

        public void setIssued(boolean issued) {
            isIssued = issued;
        }

        @Override
        public String toString() {
            return "Title: " + title + ", Author: " + author + ", ISBN: " + ISBN + ", Category: " + category + ", Issued: " + (isIssued ? "Yes" : "No");
        }
    }

    class Library {
        private ArrayList<Book> books = new ArrayList<>();
        private HashMap<String, ArrayList<Book>> issuedBooks = new HashMap<>();

        public void addBook(Book book) {
            books.add(book);
        }

        public void displayBooks() {
            if (books.isEmpty()) {
                System.out.println("No books available.");
            } else {
                for (Book book : books) {
                    System.out.println(book);
                }
            }
        }

        public void browseByCategory(String category) {
            boolean found = false;
            for (Book book : books) {
                if (book.getCategory().equalsIgnoreCase(category)) {
                    System.out.println(book);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No books found in the category: " + category);
            }
        }

        public Book searchBook(String query) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(query) || book.getAuthor().equalsIgnoreCase(query) || book.getISBN().equalsIgnoreCase(query)) {
                    return book;
                }
            }
            return null;
        }

        public void issueBook(String user, String ISBN) {
            Book book = searchBook(ISBN);
            if (book != null && !book.isIssued()) {
                book.setIssued(true);
                if (!issuedBooks.containsKey(user)) {
                    issuedBooks.put(user, new ArrayList<>());
                }
                issuedBooks.get(user).add(book);
                System.out.println("Book issued successfully.");
            } else if (book != null && book.isIssued()) {
                System.out.println("Book is already issued.");
            } else {
                System.out.println("sorry! Book not found.");
            }
        }

        public void returnBook(String user, String ISBN) {
            ArrayList<Book> userBooks = issuedBooks.get(user);
            if (userBooks != null) {
                for (Book book : userBooks) {
                    if (book.getISBN().equals(ISBN)) {
                        book.setIssued(false);
                        userBooks.remove(book);
                        System.out.println("Book returned successfully.");
                        return;
                    }
                }
            }
            System.out.println("Book not found in issued books.");
        }

        public void viewIssuedBooks(String user) {
            if (issuedBooks.containsKey(user)) {
                System.out.println("Books issued by " + user + ":");
                for (Book book : issuedBooks.get(user)) {
                    System.out.println(book);
                }
            } else {
                System.out.println("No books issued by " + user + ".");
            }
        }
    }

    class User {
        private Library library;
        private String email;

        public User(Library library, String email) {
            this.library = library;
            this.email = email;
        }

        public void browseCategories() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter category to browse (Fiction, Non-Fiction, Science, etc.): ");
            String category = sc.nextLine();
            library.browseByCategory(category);
        }

        public void searchBook() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter title, author, or ISBN to search: ");
            String query = sc.nextLine();
            Book book = library.searchBook(query);
            if (book != null) {
                System.out.println(book);
            } else {
                System.out.println("Book not found.");
            }
        }

        public void issueBook(String user) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter ISBN of the book to issue: ");
            String ISBN = sc.nextLine();
            library.issueBook(user, ISBN);
        }

        public void returnBook(String user) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter ISBN of the book to return: ");
            String ISBN = sc.nextLine();
            library.returnBook(user, ISBN);
        }

        public void viewIssuedBooks(String user) {
            library.viewIssuedBooks(user);
        }

        public void sendQuery() {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your query: ");
            String query = sc.nextLine();
            System.out.println("Query sent to: " + email + ". Your message: " + query);
            // Simulating email functionality
        }
    }

    public class LIBRARY_MANAGEMENT {
        public static void main(String[] args) {
            Library library = new Library();
            Scanner sc = new Scanner(System.in);

            // Sample books for the library
            library.addBook(new Book("Harry Potter", "J.K. Rowling", "12345", "Fiction"));
            library.addBook(new Book("A Brief History of Time", "Stephen Hawking", "67890", "Science"));

            System.out.print("Enter user email for queries: ");
            String userEmail = sc.nextLine();
            User user = new User(library, userEmail);

            boolean exit = false;
            String username = "user1";  // Simulating user login

            while (!exit) {
                System.out.println("\n----- User Module -----");
                System.out.println("1. Browse Categories");
                System.out.println("2. Search Book");
                System.out.println("3. Issue Book");
                System.out.println("4. Return Book");
                System.out.println("5. View Issued Books");
                System.out.println("6. Send Query via Email");
                System.out.println("7. Logout");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        user.browseCategories();
                        break;
                    case 2:
                        user.searchBook();
                        break;
                    case 3:
                        user.issueBook(username);
                        break;
                    case 4:
                        user.returnBook(username);
                        break;
                    case 5:
                        user.viewIssuedBooks(username);
                        break;
                    case 6:
                        user.sendQuery();
                        break;
                    case 7:
                        exit = true;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }


