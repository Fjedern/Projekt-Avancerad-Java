package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Entities.Librarian;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Library;
import com.company.Menus.AdminMenu;
import com.company.Menus.GetMenuValues;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

import static com.company.Helpers.Color.*;

public class MenuHelper implements Serializable {


    private User user;
    private Librarian librarian;

    public MenuHelper() {
    }

    public void runSystem() {
        System.out.println(CYAN +
                "\n================================" + BLUE +
                "\n==== " + RESET + "WELCOME TO THE LIBRARY" + BLUE + " ====" + PURPLE +
                "\n================================" + RESET);

        initMenu(MainMenu.values());
    }

    public void setCurrentUser(Person loggedInUser) {
        user = (User) loggedInUser;
    }

    public void setCurrentLibrarian(Person loggedInLibrarian) {
        librarian = (Librarian) loggedInLibrarian;
    }

    public <T extends GetMenuValues> void initMenu(T[] menuItems) {
        if (Library.getInstance().getOpen()) {
            int i = 1;

            System.out.println(YELLOW + menuItems[0].getHeader() + RESET);
            for (T menuItem : menuItems) {
                System.out.println(CYAN + "[" + i + "] " + RESET + menuItem.getDescription());
                i++;
            }
            setMenuChoice(menuItems);
        }
    }


    private <T extends GetMenuValues> void setMenuChoice(T[] menuItems) {
        Scanner scan = new Scanner(System.in);

        System.out.print("\nMake a choice: ");

        try {
           int menuInput = scan.nextInt();
            if (menuInput < 1 || menuInput > menuItems.length) {
                setMenuChoice(menuItems);
            }

            if (menuItems[0].getClass().equals(MainMenu.class)) {
                mainMenuChoice(menuInput);
            } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
                adminMenuChoice(menuInput);
            } else {
                userMenuChoice(menuInput);
            }

        } catch (Exception e) {
            setMenuChoice(menuItems);
        }

    }

    public void mainMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                Library.getInstance().showAllBooks();
                selectBookOption(MainMenu.values(), Library.getInstance().getBooksAsList());
            }
            case 2 -> {
                Library.getInstance().searchBookByTitle();
                selectBookOption(MainMenu.values(), Library.getInstance().getBooksBySearch());
            }
            case 3 -> {
                Library.getInstance().searchBookByAuthor();
                selectBookOption(MainMenu.values(), Library.getInstance().getBooksBySearch());
            }
            case 4 -> Library.getInstance().checkLoginV2();

            case 5 -> exitSystem();
        }
    }

    public void adminMenuChoice(int choice) {

        switch (choice) {

            case 1 -> {
                Library.getInstance().showAllBooks();
                selectBookOption(AdminMenu.values(), Library.getInstance().getBooksAsList());
            }

            case 2 -> {
                librarian.seeAllBorrowedBooks();
                generalReturnMenu(AdminMenu.values());
            }

            case 3 -> { //Search by name
                Library.getInstance().searchBookByTitle();
                selectBookOption(AdminMenu.values(), Library.getInstance().getBooksBySearch());
            }

            case 4 -> { //Search by author
                Library.getInstance().searchBookByAuthor();
                selectBookOption(AdminMenu.values(), Library.getInstance().getBooksBySearch());
            }

            case 5 -> { //All users
                librarian.seeAllUsers();
                generalReturnMenu(AdminMenu.values());
            }

            case 6 -> {
                librarian.searchForUserByName();
                generalReturnMenu(AdminMenu.values());
            }

            case 7 -> { //Add user
                librarian.librarianAddUser();
                generalReturnMenu(AdminMenu.values());
            }

            case 8 -> { //Remove user
                librarian.librarianRemoveUser();
                generalReturnMenu(AdminMenu.values());
            }

            case 9 -> { //Add Book
                librarian.librarianAddBook();
                generalReturnMenu(AdminMenu.values());
            }

            case 10 -> { //Remove Book
                librarian.librarianRemoveBookByTitle();
                generalReturnMenu(AdminMenu.values());
            }

            case 11 -> logOutCurrentPerson();
        }
    }


    public void userMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                Library.getInstance().showAllBooks();
                selectBookOption(UserMenu.values(), Library.getInstance().getBooksAsList());
            }

            case 2 -> {
                Library.getInstance().searchBookByTitle();
                selectBookOption(UserMenu.values(), Library.getInstance().getBooksBySearch());
            }

            case 3 -> {
                Library.getInstance().searchBookByAuthor();
                selectBookOption(UserMenu.values(), Library.getInstance().getBooksBySearch());
            }

            case 4 -> {
                user.borrowBooks();
                generalReturnMenu(UserMenu.values());
            }

            case 5 -> {
                user.showUserBooks();
                selectBookOption(UserMenu.values(), user.getBooks());
            }

            case 6 -> {
                user.returnBookFromMenu();
                generalReturnMenu(UserMenu.values());
            }

            case 7 -> logOutCurrentPerson();

        }
    }

    public <T extends GetMenuValues> void selectBookOption(T[] menuItems, List<Book> booksToChoose) {
        Scanner scan = new Scanner(System.in);
        System.out.print(CYAN + "\n[T]" + RESET + " Sort by Title" + CYAN + "  [A]" + RESET + " Sort by Author" + CYAN + "  [0]" + RESET + " Back to menu \n\nMake a choice: ");

        try {
            String menuChoice = scan.nextLine();

            if (menuChoice.equalsIgnoreCase("T")) {
                System.out.println("\nHÄR SORTERAR VI EFTER TITEL\n");
                Library.getInstance().sortBooks(booksToChoose, menuChoice);
                generalReturnMenu(menuItems);

            } else if (menuChoice.equalsIgnoreCase("A")) {
                System.out.println("\nHÄR SORTERAR VI EFTER AUTHOR\n");
                Library.getInstance().sortBooks(booksToChoose, menuChoice);
                generalReturnMenu(menuItems);

            } else {
                try {
                    int intChoice = Integer.parseInt(menuChoice);

                    if (intChoice == 0) {
                        initMenu(menuItems);
                    } else if (intChoice > 0 && intChoice <= booksToChoose.size()) {

                        for (Book book : booksToChoose) {
                            if (intChoice == book.getI()) {
                                book.showBookInfo();
                                initBookMenu(menuItems, book, booksToChoose);
                                return;
                            }
                        }

                    } else {
                        selectBookOption(menuItems, booksToChoose);
                    }

                } catch (Exception e) {
                    selectBookOption(menuItems, booksToChoose);

                }
            }

        } catch (Exception e) {
            selectBookOption(menuItems, booksToChoose);
        }
    }

    private <T extends GetMenuValues> void initBookMenu(T[] menuItems, Book book, List<Book> booksToChoose) {

        if (menuItems[0].getClass().equals(MainMenu.class)) {
            generalReturnMenu(menuItems);

        } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
            adminBookMenu(book);

        } else {
            userBookMenu(book, booksToChoose);
        }
    }


    // General method for returning to main/admin/user menu (depending on type of user)
    // by entering 0
    private <T extends GetMenuValues> void generalReturnMenu(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        System.out.print(CYAN + "\n[0]" + RESET + " to return: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(menuItems);
            } else {
                generalReturnMenu(menuItems);
            }
        } catch (Exception e) {
            generalReturnMenu(menuItems);
        }
    }

    private void adminBookMenu(Book book) {
        Scanner scan = new Scanner(System.in);
        System.out.println(CYAN + "\n[1]" + RESET + " Remove book \n" + CYAN + "[0]" + RESET + " Back to menu\n");
        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();

            if (input == 0) {
                initMenu(AdminMenu.values());

            } else if (input == 1) {
                librarian.librarianRemoveBookByChoice(book);
                generalReturnMenu(AdminMenu.values());

            } else {
                adminBookMenu(book);
            }

        } catch (Exception e) {
            adminBookMenu(book);
        }
    }

    //If List<Book> books is a
    private void userBookMenu(Book book, List<Book> books) {
        Scanner scan = new Scanner(System.in);

        if (books.equals(Library.getInstance().getBooksAsList())) {
            System.out.println(CYAN + "\n[1]" + RESET + " Borrow book \n" + CYAN + "[0]" + RESET + " Back to menu\n");

        } else {
            System.out.println(CYAN + "\n[1]" + RESET + " Return book to library \n" + CYAN + "[0]" + RESET + " Back to menu\n");
        }

        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();

            if (input == 0) {
                initMenu(UserMenu.values());

            } else if (input == 1 && books.equals(Library.getInstance().getBooksAsList())) {
                if (book.isAvailable()) {
                    user.borrowBook(book);

                } else {
                    System.out.println(RED + "\n" + book.getTitle() + " is already loaned out\n" + RESET + book.showDaysRemainingOnLoan() + "\n");
                }
                generalReturnMenu(UserMenu.values());

            } else if (input == 1 && books.equals(user.getBooks())) {
                user.returnBook(book);
                generalReturnMenu(UserMenu.values());

            } else {
                userBookMenu(book, books);
            }

        } catch (Exception e) {
            userBookMenu(book, books);
        }
    }

    public void logOutCurrentPerson() {
        System.out.println(GREEN + "\nLogging out" + RESET);

        if (user != null) {
            user.setLoggedIn(false);
        }

        if (librarian != null) {
            librarian.setLoggedIn(false);
        }
        FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");
        FileUtils.writeObject(Library.getInstance().users, "src/com/company/Files/User.ser");

        initMenu(MainMenu.values());
    }

    public void exitSystem() {
        System.out.println(GREEN + "\nShutting down system" + RESET);
        Library.getInstance().setOpen(false);
        initMenu(MainMenu.values());
    }
}
