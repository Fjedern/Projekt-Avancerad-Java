package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.company.Helpers.Color.*;

public class User extends Person implements Serializable {

    private static final long serialVersionUID = 2L;

    public List<Book> userBooks = new ArrayList<>();

    public User(String name, String username, String password) {
        super(name, username, password);
    }

    public List<Book> getBooks() {
        return userBooks;
    }

    public void borrowBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        Library.getInstance().getBooksAsList()
                .forEach(book -> System.out.println("* " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor() + " (" + book.getLoanStatus() + ")"));

        System.out.print("\nWhich book would you like to borrow? (Use title) ");
        String title = scanner.nextLine();

        for (Book book : Library.getInstance().getBooksAsList()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isAvailable()) {
                    System.out.println("\n" + getName() + " loans " + book.getTitle());
                    userBooks.add(book);
                    book.setAvailable(false);
                    FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");
                }
            }
        }
    }

    public void borrowBook(Book book) {
        userBooks.add(book);
        System.out.println(GREEN + "\n" + getName() + " loans " + book.getTitle() + RESET);
        book.setAvailable(false);
        FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");

    }

    public void returnBook(Book bookToReturn) {
        if (userBooks.removeIf(book -> book.equals(bookToReturn))) {
            System.out.println(GREEN + "\n" + bookToReturn.getTitle() + " by " + bookToReturn.getAuthor() + " returned to library" + RESET);
            bookToReturn.setAvailable(true);
            FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");
        }
    }

    public void showUserBooks() {
        int i = 1;
        userBooks.sort(Comparator.comparing(Book::getTitle));
        System.out.println();
        for (Book book : userBooks) {
            book.setI(i);
            System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle().toUpperCase() + RESET + book.showDaysRemainingOnLoan());
            i++;
        }
    }

    public void returnBookFromMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println();

        userBooks.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(book -> System.out.println("* " + YELLOW + book.getTitle() + RESET + book.showDaysRemainingOnLoan()));
        try {
            System.out.print("\nTitle of the book you want to return: ");
            String titleOfBookToRemove = scan.nextLine();

            for (Book book : userBooks) {
                if (titleOfBookToRemove.equalsIgnoreCase(book.title)) {
                    userBooks.remove(book);
                    book.setAvailable(true);
                    System.out.println(GREEN + "\n" + book.getTitle() + " by " + book.getAuthor() + " returned to library" + RESET);
                    return;
                }
            }

            System.out.println(RED + "\nNo book matches: '" + titleOfBookToRemove + "'");

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    @Override
    public String toString() {
        return getName() + ", " + getUsername() + ", " + getPassword();
    }
}
