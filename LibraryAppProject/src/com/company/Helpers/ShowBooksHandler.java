package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Library;
import com.company.Menus.GetMenuValues;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.company.Helpers.Color.*;
import static com.company.Helpers.Color.RESET;

public class ShowBooksHandler {

    public <T extends GetMenuValues> void showAvailableBooks(T[] menuItems) {
        List<Book> booksAvailable = new ArrayList<>();
        int matches = 0;
        int i = 1;
        System.out.println(YELLOW + "\n== AVAILABLE BOOKS ==" + RESET);
        for (Book book : Library.getInstance().getBooksAsList()) {
            if (book.isAvailable()) {
                book.setI(i);
                booksAvailable.add(book);
                System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor());
                matches++;
                i++;
            }
        }

        if (matches == 0) {
            System.out.print("There are no books available");
        } else {
            Library.getInstance().getMenuHelper().selectBookOption(menuItems, booksAvailable);
        }
    }

    public void showAllBooks() {
        int i = 1;
        System.out.println(YELLOW + "\n== ALL BOOKS ==" + RESET);

        for (Book book : Library.getInstance().getBooksAsList()) {
            book.setI(i);
            System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor());
            i++;
        }
    }

    public <T extends GetMenuValues> void sortBooks(List<Book> booksToSort, String compare, T[] menuItems) {
        int i = 1;

        if (compare.equalsIgnoreCase("T")) {
            booksToSort.sort(Comparator.comparing(Book::getTitle));
            System.out.println(YELLOW + "\n== SORTED BY TITLE ==" + RESET);

            for (Book book : booksToSort) {
                book.setI(i);
                System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle().toUpperCase() + RESET + " by " + book.getAuthor());
                i++;
            }
            Library.getInstance().getMenuHelper().selectBookOption(menuItems, booksToSort);

        } else if (compare.equalsIgnoreCase("A")) {
            booksToSort.sort(Comparator.comparing(Book::getAuthor));
            System.out.println(YELLOW + "\n== SORTED BY AUTHOR ==" + RESET);

            for (Book book : booksToSort) {
                book.setI(i);
                System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getAuthor().toUpperCase() + RESET + " - " + book.getTitle());
                i++;
            }
            Library.getInstance().getMenuHelper().selectBookOption(menuItems, booksToSort);
        }
    }

    public <T extends GetMenuValues> void searchBookByTitle(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        List<Book> booksByTitle = new ArrayList<>();
        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by title: ");

        try {
            String input = scan.nextLine();
            System.out.println(YELLOW + "\n== TITLE OF BOOKS MATCHING: '" + input + "' ==" + RESET);

            for (Book book : Library.getInstance().getBooksAsList()) {

                if (book.getTitle().toUpperCase().contains(input.toUpperCase())) {
                    matches++;
                    book.setI(i);
                    booksByTitle.add(book);
                    System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle().toUpperCase() + RESET + " by " + book.getAuthor());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println(RED + "\nNo books matches your search" + RESET);
                Library.getInstance().getMenuHelper().generalReturnMenu(menuItems);

            } else {
                Library.getInstance().getMenuHelper().selectBookOption(menuItems, booksByTitle);
            }

        } catch (Exception e) {
            searchBookByTitle(menuItems);
        }
    }

    public <T extends GetMenuValues> void searchBookByAuthor(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        List<Book> tempList = Library.getInstance().getBooksAsList();
        List<Book> booksByAuthor = new ArrayList<>();
        tempList.sort(Comparator.comparing(Book::getAuthor));

        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by author: ");

        try {
            String input = scan.nextLine();

            System.out.println(YELLOW + "\n== BOOKS BY AUTHORS MATCHING: '" + input + "' ==" + RESET);
            for (Book book : tempList) {

                if (book.getAuthor().toUpperCase().contains(input.toUpperCase())) {
                    matches++;
                    booksByAuthor.add(book);
                    book.setI(i);
                    System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getAuthor().toUpperCase() + RESET + " - " + book.getTitle());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println(RED + "\nNo authors matches your search" + RESET);
                Library.getInstance().getMenuHelper().generalReturnMenu(menuItems);

            } else {
                Library.getInstance().getMenuHelper().selectBookOption(menuItems, booksByAuthor);
            }

        } catch (Exception e) {
            searchBookByTitle(menuItems);
        }
    }
}
