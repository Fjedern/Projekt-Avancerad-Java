package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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

    public void borrowBooks(Library library) {
        Scanner scanner = new Scanner(System.in);

        library.showAllBooks();
        System.out.println("\nWhich book would you like to borrow? (Use title)");
        String title = scanner.nextLine();

        for (Book book : library.getBookList()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.print(book.getTitle() + title);
                if (book.isAvailable()) {
                    book.setAvailable(false);
                    library.bookList.add(book);
                    FileUtils.writeObject(library.bookList, "src/com/company/Files/Books.ser");


                    //FileUtils.writeObject(Library.userList, "src/com/company/Files/User.ser");
                    //System.out.print(FileUtils.readObject("src/com/company/Files/Books.ser"));
                    String hej = scanner.nextLine();
                }
            }
        }

    }

    public void borrowBook(Book book) {
        books.add(book);
        book.setAvailable(false);
    }


    public void returnBook(Book bookToReturn, Library mainLibrary) {
        if (books.removeIf(book -> book.equals(bookToReturn))) {
            bookToReturn.setAvailable(true);
            FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
        }

    }

    public void showUserBooks() {
        int i = 1;
        books.sort(Comparator.comparing(Book::getTitle));
        for (Book book : books) {
            book.setI(i);
            System.out.println("[" + i + "] " + book.getTitle().toUpperCase() + " (" + book.showDaysRemainingOnLoan() + ") ");
            i++;
        }
    }

    public void returnBook() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nYour books:");
        books.forEach(book -> System.out.println("* " + book.getTitle() + " [" + book.showDaysRemainingOnLoan() + "]"));
        try {
            System.out.print("\nTitle of book to return: ");
            String titleOfBookToRemove = scan.nextLine();

            for (Book book : books) {
                if (titleOfBookToRemove.equalsIgnoreCase(book.title)) {
                    books.remove(book);
                    book.setAvailable(true);
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " returned to library");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }
}
