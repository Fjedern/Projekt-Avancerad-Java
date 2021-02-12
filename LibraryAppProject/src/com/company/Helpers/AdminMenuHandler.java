package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Entities.Librarian;
import com.company.Library;
import com.company.Menus.AdminMenu;

import java.util.Scanner;

import static com.company.Helpers.Color.CYAN;
import static com.company.Helpers.Color.RESET;

public class AdminMenuHandler {

    private Librarian librarian;
    private final MenuHelper menuHelper;

    public AdminMenuHandler(MenuHelper menuHelper) {
        this.menuHelper = menuHelper;
    }

    public void adminMenuChoice(int choice) {

        switch (choice) {

            case 1 -> {
                Library.getInstance().getShowBooksHandler().showAllBooks();
                menuHelper.selectBookOption(AdminMenu.values(), Library.getInstance().getBooksAsList());
            }

            case 2 -> Library.getInstance().getShowBooksHandler().showAvailableBooks(AdminMenu.values());

            case 3 -> {
                librarian.seeAllBorrowedBooks();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }
            //Search by name
            case 4 -> Library.getInstance().getShowBooksHandler().searchBookByTitle(AdminMenu.values());

            //Search by author
            case 5 -> Library.getInstance().getShowBooksHandler().searchBookByAuthor(AdminMenu.values());

            //All users
            case 6 -> {
                librarian.seeAllUsers();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }

            case 7 -> {
                librarian.searchForUserByName();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }

            case 8 -> { //Add user
                librarian.librarianAddUser();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }

            case 9 -> { //Remove user
                librarian.librarianRemoveUser();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }

            case 10 -> { //Add Book
                librarian.librarianAddBook();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }

            case 11 -> { //Remove Book
                librarian.librarianRemoveBookByTitle();
                menuHelper.generalReturnMenu(AdminMenu.values());
            }

            case 12 -> menuHelper.logOutCurrentPerson(librarian);
        }
    }

    // Librarian can choose to remove book or go back to admin menu when viewing a specific book
    public void adminBookMenu(Book book) {
        Scanner scan = new Scanner(System.in);
        System.out.println(CYAN + "\n[1]" + RESET + " Remove book \n" + CYAN + "[0]" + RESET + " Back to menu\n");
        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();

            if (input == 0) {
                menuHelper.initMenu(AdminMenu.values());

            } else if (input == 1) {
                librarian.librarianRemoveBookByChoice(book);
                menuHelper.generalReturnMenu(AdminMenu.values());

            } else {
                adminBookMenu(book);
            }

        } catch (Exception e) {
            adminBookMenu(book);
        }
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }
}
