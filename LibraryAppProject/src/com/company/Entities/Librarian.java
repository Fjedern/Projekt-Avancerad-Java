package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;

import java.io.Serializable;
import java.util.Scanner;

public class Librarian extends Person implements Serializable {

    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }

    public Librarian() {

    }


    public void librarianAddBook(Library mainLibrary) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String titleName = scan.nextLine();
        System.out.println("Enter a short description: ");
        String description = scan.nextLine();
        System.out.println("Enter author name: ");
        String author = scan.nextLine();
        Book book = new Book(titleName, description, author);
        mainLibrary.getBookList().add(book);
        FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
    }

    public void librarianRemoveBookByTitle(Library mainLibrary) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title to remove from library");
        String bookTitleToRemove = scan.nextLine();
        if (mainLibrary.bookList.removeIf(book -> book.getTitle().equalsIgnoreCase(bookTitleToRemove))) {
            //FileUtils.deleteObjectBook(bookTitleToRemove);
            FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
        }

    }

    public void librarianRemoveBookByChoice(Book bookToRemove, Library mainLibrary) {
        if (mainLibrary.bookList.removeIf(book -> book.equals(bookToRemove))) {
            FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
        }
    }

    public void librarianAddUser(Library mainLibrary) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scan.nextLine();
        System.out.println("Enter username: ");
        String username = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();

        User user = new User(name, username, password);
        mainLibrary.userList.add(user);
        FileUtils.writeObject(mainLibrary.userList, "src/com/company/Files/User.ser");


    }

    public void librarianRemoveUser(Library mainLibrary) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username of user to remove: ");
        String userToRemove = scan.nextLine();
        mainLibrary.userList.removeIf(person -> userToRemove.equalsIgnoreCase(person.getUsername()));
        FileUtils.writeObject(mainLibrary.userList, "src/com/company/Files/User.ser");
    }

    public void seeAllUsers(Library mainLibrary) {
        System.out.println();
        for (Person p : mainLibrary.userList) {
            if (p instanceof User) {
                System.out.println("Name of user: " + p.getName() +
                        "\nBorrowed books:");
                for (Book book : ((User) p).getBooks()) {
                    System.out.println(book.getTitle() + " (" + book.showDaysRemainingOnLoan() + ")");
                }
                System.out.println();
            }
        }
    }

}

