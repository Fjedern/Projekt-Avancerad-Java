package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;

import java.util.Scanner;

public class Librarian extends Person{
    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }



    public static void librarianAddBook(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String titleName= scan.nextLine();
        System.out.println("Enter a short description: ");
        String description = scan.nextLine();
        System.out.println("Enter author name: ");
        String author = scan.nextLine();
        Book book = new Book(titleName,description,author);
        Library.bookList.add(book);

        FileUtils.writeObjectBook(book);
    }

    public static void librarianRemoveBook(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title to remove from library");
        String bookTitleToRemove = scan.nextLine();
        if(Library.bookList.removeIf(book -> book.getTitle().equalsIgnoreCase(bookTitleToRemove))){
            FileUtils.deleteObjectBook(bookTitleToRemove);

        }

    }

    public static void librarianAddUser(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scan.nextLine();
        System.out.println("Enter username: ");
        String username = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();

        User user = new User(name,username,password);
        Library.userlist.add(user);
    }

    public static void librarianRemoveUser(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username of user to remove: ");
        String userToRemove = scan.nextLine();
        Library.userlist.removeIf(user -> user.getUsername().equalsIgnoreCase(userToRemove));
    }

}

