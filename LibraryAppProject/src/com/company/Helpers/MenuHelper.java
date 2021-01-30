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



    public MenuHelper() {
    }

    public <T extends HasDescription> void initMenu(T[] menuItems, Library library) {
        if(library.getOpen()) {
            System.out.println();
            int i = 1;

            for (T menuItem : menuItems) {
                System.out.println("[" + i + "] " + menuItem.getDescription());
                i++;
            }
            setMenuChoice(menuItems, library);
        }
    }


    private <T extends HasDescription> void setMenuChoice(T[] menuItems, Library library) {
        Scanner scan = new Scanner(System.in);
        int menuInput = -1;

        System.out.print("\nMake a choice: ");

        while (menuInput < 0 || menuInput > menuItems.length) {
            try {
                menuInput = scan.nextInt();

                if (menuItems[0].getClass().equals(MainMenu.class)) {
                    mainMenuChoice(menuInput, library);
                } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
                    adminMenuChoice(menuInput, library);
                } else {
                    userMenuChoice(menuInput, library);
                }

            } catch (Exception e) {
                setMenuChoice(menuItems, library);
            }
        }
    }

    public void mainMenuChoice(int choice, Library library) {

        switch (choice) {
            case 1 -> {
                library.showAllBooks();
                selectBookOption(MainMenu.values(), library);
            }
            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(MainMenu.values(), library);
            }
            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(MainMenu.values(), library);
            }
            case 4 -> {
                System.out.println("Main menu");
                library.checkLogin();
            }
            case 5 -> {
                System.out.println("Logging out");
                library.setOpen(false);
                initMenu(MainMenu.values(), library);

            }
        }
    }

    public void adminMenuChoice(int choice, Library library) {

        switch (choice) {

            case 1 -> {
                library.showAllBooks();
                selectBookOption(AdminMenu.values(), library);
            }

            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(AdminMenu.values(), library);
            }

            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(AdminMenu.values(), library);
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
                initMenu(MainMenu.values(), library);

            }
        }
    }


    public void userMenuChoice(int choice, Library library) {

        switch (choice) {
            case 1 -> {
                library.showAllBooks();
                selectBookOption(UserMenu.values(), library);
            }
            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(UserMenu.values(), library);
            }
            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(UserMenu.values(), library);
            }
            case 4 -> System.out.println("User menu");
            case 5 -> System.out.println("User menu");
            case 6 -> {
                System.out.println("Logging out");
                library.setOpen(false);
                initMenu(MainMenu.values(), library);

            }
        }
    }




    public <T extends HasDescription> void selectBookOption(T[] menuItems, Library library) {
        Scanner scan = new Scanner(System.in);
        System.out.print("\n[0] to return. Make a choice: ");
        try {
            int menuChoice = scan.nextInt();

            if (menuChoice == 0) {
                initMenu(menuItems, library);

            } else if (menuChoice > 0) {
                for (Book book : bookList) {
                    if (menuChoice == book.getI()) {
                        book.showBookInfo();
                        initBookMenu(menuItems, book, library);
                    }
                }
            } else {
                selectBookOption(menuItems, library);
            }

        } catch (Exception e) {
            selectBookOption(menuItems, library);
        }
    }

    private <T> void initBookMenu(T[] menuItems, Book book, Library library) {

        if (menuItems[0].getClass().equals(MainMenu.class)) {
            mainBookMenu(library);

        } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
            adminBookMenu(book, library);

        } else {
            userBookMenu(book, library);
        }
    }

    private void mainBookMenu(Library library) {
        Scanner scan = new Scanner(System.in);
        System.out.print("\n[0] to return: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(MainMenu.values(), library);
            }
        } catch (Exception e) {
            mainBookMenu(library);
        }
    }

    private void adminBookMenu(Book book, Library library) {
        //Librarian librarian = new Librarian();
        Scanner scan = new Scanner(System.in);
        System.out.println("\n[1] Remove book \n[0] to return\n");
        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(AdminMenu.values(), library);
            } else if (input == 1) {
                //librarian.removeBook
            }
        } catch (Exception e) {
            adminBookMenu(book, library);
        }
    }

    private void userBookMenu(Book book, Library library) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n[0] to return     [1] Borrow book");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(UserMenu.values(), library);
            } else if (input == 1) {
                //user.borrowBook(book);

            }
        } catch (Exception e) {
            mainBookMenu(library);
        }
    }
}
