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
    List<Book> books = new ArrayList<>();

    public User(String name, String username, String password) {
        super(name, username, password);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "User{" +
                "books=" + books +
                '}';
    }

    public void borrowBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(CYAN + "\n== All books ==" + RESET);

        Library.getInstance().bookList.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(book -> System.out.println("* " + YELLOW + book.getTitle().toUpperCase() + RESET + " by " + book.getAuthor() + " (" + book.getLoanStatus() + ")"));

        System.out.print("\nWhich book would you like to borrow? (Use title) ");
        String title = scanner.nextLine();

        for (Book book : Library.getInstance().getBookList()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isAvailable()) {
                    System.out.println("\n" + getName() + " loans " + book.getTitle());
                    books.add(book);
                    book.setAvailable(false);
                    FileUtils.writeObject(Library.getInstance().bookList, "src/com/company/Files/Books.ser");

                    //FileUtils.writeObject(Library.userList, "src/com/company/Files/User.ser");
                    //System.out.print(FileUtils.readObject("src/com/company/Files/Books.ser"));
                }
            }
        }

    }

    public void borrowBook(Book book) {
        books.add(book);
        book.setAvailable(false);
    }


    public void returnBook(Book bookToReturn) {
        if (books.removeIf(book -> book.equals(bookToReturn))) {
            bookToReturn.setAvailable(true);
            FileUtils.writeObject(Library.getInstance().bookList, "src/com/company/Files/Books.ser");
        }

    }

    public void showUserBooks() {
        int i = 1;
        books.sort(Comparator.comparing(Book::getTitle));
        for (Book book : books) {
            book.setI(i);
            System.out.println(BLUE + "[" + i + "] " + YELLOW + book.getTitle().toUpperCase() + RESET + book.showDaysRemainingOnLoan());
            i++;
        }
    }

    public void returnBookFromMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n== Your loans ==");
        books.forEach(book -> System.out.println("* " + book.getTitle() + book.showDaysRemainingOnLoan()));

        books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(book -> System.out.println("* " + YELLOW + book.getTitle().toUpperCase() + book.showDaysRemainingOnLoan()));
        try {
            System.out.print("\nTitle of the book you want to return: ");
            String titleOfBookToRemove = scan.nextLine();

            for (Book book : books) {
                if (titleOfBookToRemove.equalsIgnoreCase(book.title)) {
                    books.remove(book);
                    book.setAvailable(true);
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " returned to library");
                    return;
                }
            }
            System.out.println("No book matches '" + titleOfBookToRemove + "'");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }
}
