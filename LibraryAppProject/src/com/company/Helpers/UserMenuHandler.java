package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Entities.User;
import com.company.Library;
import com.company.Menus.UserMenu;

import java.util.List;
import java.util.Scanner;

import static com.company.Helpers.Color.CYAN;
import static com.company.Helpers.Color.RESET;

public class UserMenuHandler {

    private User user;
    private final MenuHelper menuHelper;

    public UserMenuHandler(MenuHelper menuHelper) {
        this.menuHelper = menuHelper;
    }

    // USER MENU
    void userMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                Library.getInstance().getShowBooksHandler().showAllBooks();
                menuHelper.selectBookOption(UserMenu.values(), Library.getInstance().getBooksAsList());
            }

            case 2 -> Library.getInstance().getShowBooksHandler().showAvailableBooks(UserMenu.values());

            case 3 -> Library.getInstance().getShowBooksHandler().searchBookByTitle(UserMenu.values());

            case 4 -> Library.getInstance().getShowBooksHandler().searchBookByAuthor(UserMenu.values());

            case 5 -> {
                user.borrowBooks();
                menuHelper.generalReturnMenu(UserMenu.values());
            }

            case 6 -> user.showUserBooks();

            case 7 -> {
                user.returnBookFromMenu();
                menuHelper.generalReturnMenu(UserMenu.values());
            }

            case 8 -> menuHelper.logOutCurrentPerson(user);
        }
    }

    // Gives logged in User user different options depending on what List<Book> is
    // in use (Users loaned books || Books in library) when viewing a specific book
    public void userBookMenu(Book book, List<Book> books) {
        Scanner scan = new Scanner(System.in);

        if (books.equals(user.getBooks())) {
            System.out.println(CYAN + "\n[1]" + RESET + " Return book to library \n" + CYAN + "[0]" + RESET + " Back to menu\n");

        } else {
            System.out.println(CYAN + "\n[1]" + RESET + " Borrow book \n" + CYAN + "[0]" + RESET + " Back to menu\n");
        }

        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();

            if (input == 0) {
                menuHelper.initMenu(UserMenu.values());

            } else if (input == 1 && books.equals(user.getBooks())) {
                user.returnBook(book);
                menuHelper.generalReturnMenu(UserMenu.values());

            } else if (input == 1 && !books.equals(user.getBooks())) {
                user.borrowBook(book);
                menuHelper.generalReturnMenu(UserMenu.values());

            } else {
                userBookMenu(book, books);
            }

        } catch (Exception e) {
            userBookMenu(book, books);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
