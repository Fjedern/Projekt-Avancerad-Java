package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Entities.User;
import com.company.Library;
import com.company.Menus.AdminMenu;
import com.company.Menus.HasDescription;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;


import java.util.Scanner;



import static com.company.Library.bookList;

public class MenuHelper {

    Library library;
    User user;


    public MenuHelper() {
    }

    public void runSystem(Library openLibrary) {
        library = openLibrary;
        initMenu(MainMenu.values());
    }

    public <T extends HasDescription> void initMenu(T[] menuItems) {
        if (library.getOpen()) {
            System.out.println();
            int i = 1;

            for (T menuItem : menuItems) {
                System.out.println("[" + i + "] " + menuItem.getDescription());
                i++;
            }
            setMenuChoice(menuItems);
        }
    }


    private <T extends HasDescription> void setMenuChoice(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        int menuInput = -1;

        System.out.print("\nMake a choice: ");

        while (menuInput < 0 || menuInput > menuItems.length) {
            try {
                menuInput = scan.nextInt();

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
    }

    public void mainMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                library.showAllBooks();
                selectBookOption(MainMenu.values());
            }
            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(MainMenu.values());
            }
            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(MainMenu.values());
            }
            case 4 -> {
                System.out.println("Main menu");
                library.checkLogin();
            }
            case 5 -> {
                System.out.println("Logging out");
                library.setOpen(false);
                initMenu(MainMenu.values());


            }
        }
    }

    public void adminMenuChoice(int choice) {

        switch (choice) {

            case 1 -> {
                library.showAllBooks();
                selectBookOption(AdminMenu.values());
            }

            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(AdminMenu.values());
            }

            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(AdminMenu.values());
            }


            case 4 -> {
                System.out.println("Admin menu");
            }
            case 5 -> {
                System.out.println("Admin menu");
            }
            case 6 -> {
                System.out.println("Admin menu");
            }
            case 7 -> {
                System.out.println("Admin menu");
            }


            case 8 -> {
                System.out.println("Admin menu");

            }

            case 9 -> {
                System.out.println("Logging out");
                library.setOpen(false);
                initMenu(MainMenu.values());

            }
        }
    }


    public void userMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                library.showAllBooks();
                selectBookOption(UserMenu.values());
            }

            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(UserMenu.values());
            }

            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(UserMenu.values());
            }

            case 4 -> {

                selectBookOption(UserMenu.values());

                user.borrowBook();
            }

            case 5 -> System.out.println("User menu");

            case 6 -> {
                System.out.println("Logging out");
                library.setOpen(false);
                initMenu(MainMenu.values());

            }
        }
    }


    public <T extends HasDescription> void selectBookOption(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        System.out.print("\n[0] to return. Make a choice: ");
        try {
            int menuChoice = scan.nextInt();

            if (menuChoice == 0) {
                initMenu(menuItems);

            } else if (menuChoice > 0) {
                for (Book book : bookList) {
                    if (menuChoice == book.getI()) {
                        book.showBookInfo();
                        initBookMenu(menuItems, book);
                    }
                }

            } else {
                selectBookOption(menuItems);
            }

        } catch (Exception e) {
            selectBookOption(menuItems);
        }
    }

    private <T> void initBookMenu(T[] menuItems, Book book) {

        if (menuItems[0].getClass().equals(MainMenu.class)) {
            mainBookMenu();

        } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
            adminBookMenu(book);

        } else {
            userBookMenu(book);
        }
    }

    private void mainBookMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\n[0] to return: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(MainMenu.values());
            }
        } catch (Exception e) {
            mainBookMenu();
        }
    }

    private void adminBookMenu(Book book) {
        //Librarian librarian = new Librarian();
        Scanner scan = new Scanner(System.in);
        System.out.println("\n[1] Remove book \n[0] to return\n");
        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(AdminMenu.values());
            } else if (input == 1) {
                //librarian.removeBook
            }
        } catch (Exception e) {
            adminBookMenu(book);
        }
    }

    private void userBookMenu(Book book) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n[1] Borrow book\n[0] to return");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(UserMenu.values());
            } else if (input == 1) {
                //user.borrowBook(book);

            }
        } catch (Exception e) {
            mainBookMenu();
        }
    }
}
