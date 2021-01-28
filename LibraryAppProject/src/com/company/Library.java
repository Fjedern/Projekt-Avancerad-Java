package com.company;

import com.company.Entities.Book;
import com.company.Entities.User;
import com.company.Helpers.MenuHelper;

import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;

public class Library {

    public static List<Book> bookList = new ArrayList<>();
    public static List<User> userList = new ArrayList<>();
    MenuHelper menuHelper;



    public Library() {
    }




    //Funktionen som kör igång programmet
    public void openLibrary() {

    }

    public void showAllBooks() {
        bookList.sort(Comparator.comparing(Book::getTitle));
        for (Book book : bookList) {
            System.out.println("* " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    public void searchBookByTitle() {
        Scanner scan = new Scanner(System.in);
        int matches = 0;
        System.out.print("Search books by title: ");

        try {
            String regex = scan.nextLine();
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            bookList.sort(Comparator.comparing(Book::getTitle));

            System.out.println("\nBooks matching: '" + regex + "'");
            for (Book book : bookList) {
                Matcher matcher = pattern.matcher(book.getTitle());
                boolean matchFound = matcher.find();

                if(matchFound) {
                    matches++;
                    System.out.println("* " + book.getTitle().toUpperCase() + " by " + book.getAuthor());
                }
            }

            if (matches == 0) {
                System.out.println("No books matches your search");
            }

        } catch (Exception e) {
            searchBookByTitle();
        }
    }

    public void searchBookByAuthor() {
        Scanner scan = new Scanner(System.in);
        int matches = 0;
        System.out.print("Search books by author: ");

        try {
            String regex = scan.nextLine();
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            bookList.sort(Comparator.comparing(Book::getTitle));

            System.out.println("\nAuthors matching: '" + regex + "'");
            for (Book book : bookList) {
                Matcher matcher = pattern.matcher(book.getAuthor());
                boolean matchFound = matcher.find();

                if(matchFound) {
                    matches++;
                    System.out.println("* " + book.getTitle() + " by " + book.getAuthor().toUpperCase());
                }
            }

            if (matches == 0) {
                System.out.println("No authors matches your search");
            }

        } catch (Exception e) {
            searchBookByTitle();
        }
    }


    public void addBookToList(Book book) {
        bookList.add(book);
    }

    public void addUserToList(User user) {
        userList.add(user);
    }
}
