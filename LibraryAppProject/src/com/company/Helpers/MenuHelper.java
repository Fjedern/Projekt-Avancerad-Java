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

    private final AdminMenuHandler adminMenuHandler;
    private final UserMenuHandler userMenuHandler;

    public MenuHelper() {
        this.adminMenuHandler = new AdminMenuHandler(this);
        this.userMenuHandler = new UserMenuHandler(this);
    }

    public void runSystem() {
        System.out.println(CYAN +
                "\n================================" + BLUE +
                "\n==== " + RESET + "WELCOME TO THE LIBRARY" + BLUE + " ====" + PURPLE +
                "\n================================" + RESET);
        logOutAllPersons();
        initMenu(MainMenu.values());
    }

    // Logs out all users when the program start. Just for backup.
    public void logOutAllPersons() {
        Library.getInstance().users.values().forEach(person -> person.setLoggedIn(false));
    }

    public void setCurrentPerson(Person loggedInPerson) {
        if (loggedInPerson instanceof User) {
            User user = (User) loggedInPerson;
            userMenuHandler.setUser(user);

        } else {
            Librarian librarian = (Librarian) loggedInPerson;
            adminMenuHandler.setLibrarian(librarian);
        }
        FileUtils.writeObject(Library.getInstance().users, "src/com/company/Files/User.ser");
    }

    public void logOutCurrentPerson(Person person) {
        System.out.println(GREEN + "\nLogging out" + RESET);
        person.setLoggedIn(false);
        FileUtils.writeObject(Library.getInstance().users, "src/com/company/Files/User.ser");
        initMenu(MainMenu.values());
    }

    // Prints out the main/admin/user menu
    public <T extends GetMenuValues> void initMenu(T[] menuItems) {
        int i = 1;
        System.out.println(YELLOW + menuItems[0].getHeader() + RESET);

        for (T menuItem : menuItems) {
            System.out.println(CYAN + "[" + i + "] " + RESET + menuItem.getDescription());
            i++;
        }
        setMenuChoice(menuItems);
    }

    // Gets user input from main/admin/user menu and sends user to the right switch
    private <T extends GetMenuValues> void setMenuChoice(T[] menuItems) {
        Scanner scan = new Scanner(System.in);

        System.out.print("\nMake a choice: ");

        try {
            int menuInput = scan.nextInt();

            if (menuInput < 1 || menuInput > menuItems.length) {
                setMenuChoice(menuItems);
            }

            if (menuItems[0] instanceof MainMenu) {
                mainMenuChoice(menuInput);

            } else if (menuItems[0] instanceof AdminMenu) {
                adminMenuHandler.adminMenuChoice(menuInput);

            } else {
                userMenuHandler.userMenuChoice(menuInput);
            }

        } catch (Exception e) {
            setMenuChoice(menuItems);
        }
    }

    // MAIN MENU
    private void mainMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                Library.getInstance().getShowBooksHandler().showAllBooks();
                selectBookOption(MainMenu.values(), Library.getInstance().getBooksAsList());
            }

            case 2 -> Library.getInstance().getShowBooksHandler().showAvailableBooks(MainMenu.values());

            case 3 -> Library.getInstance().getShowBooksHandler().searchBookByTitle(MainMenu.values());

            case 4 -> Library.getInstance().getShowBooksHandler().searchBookByAuthor(MainMenu.values());

            case 5 -> Library.getInstance().checkLogin();

            case 6 -> System.out.println(GREEN + "\nShutting down system" + RESET);
        }
    }

    // Gives the programs user options to sort books by author/title, select a book from the list by number or return
    public <T extends GetMenuValues> void selectBookOption(T[] menuItems, List<Book> booksToChoose) {
        Scanner scan = new Scanner(System.in);
        System.out.print(CYAN + "\n[T]" + RESET + " Sort by Title" + CYAN + "  [A]" + RESET + " Sort by Author" + CYAN + "  [0]" + RESET + " Back to menu \n\nMake a choice: ");

        try {
            String menuChoice = scan.nextLine();

            if (menuChoice.equalsIgnoreCase("T")) {
                Library.getInstance().getShowBooksHandler().sortBooks(booksToChoose, menuChoice, menuItems);

            } else if (menuChoice.equalsIgnoreCase("A")) {
                Library.getInstance().getShowBooksHandler().sortBooks(booksToChoose, menuChoice, menuItems);

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

    //Checks what type of menu-object is in use and sends the programs user to the right book-menu
    private <T extends GetMenuValues> void initBookMenu(T[] menuItems, Book book, List<Book> booksToChoose) {

        if (menuItems[0] instanceof MainMenu) {
            generalReturnMenu(menuItems);

        } else if (menuItems[0] instanceof AdminMenu) {
            adminMenuHandler.adminBookMenu(book);

        } else {
            userMenuHandler.userBookMenu(book, booksToChoose);
        }
    }

    // General method for returning to main/admin/user menu (depending on type of user)
    // by entering '0'
    public <T extends GetMenuValues> void generalReturnMenu(T[] menuItems) {
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
}
