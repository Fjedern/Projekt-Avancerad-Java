package com.company.Entities;

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

        Library.addBook(book);
    }

    public static void librarianRemoveBook(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title to remove from library");
        String bookTitleToRemove = scan.nextLine();
        Library.removeBook(bookTitleToRemove);
    }

}

