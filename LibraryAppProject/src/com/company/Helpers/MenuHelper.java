package com.company.Helpers;

import com.company.Library;
import com.company.Menus.AdminMenu;
import com.company.Menus.HasDescription;
import com.company.Menus.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuHelper {

    private Library library;

    public MenuHelper() {
    }

    public <T extends HasDescription> void initMenu(T[] menuItems) {

        List<T> menuAlternatives = new ArrayList<>();
        System.out.println();

        int i = 1;
        for (T menuItem : menuItems) {
            System.out.println("[" + i + "] " + menuItem.getDescription());
            i++;
            menuAlternatives.add(menuItem);
        }
        setMenuChoice(menuAlternatives);
    }

    private <T extends HasDescription> void setMenuChoice(List<T> menuAlternatives) {
        Scanner scan = new Scanner(System.in);
        int menuInput = -1;

        System.out.print("\nMake a choice: ");

        while (menuInput < 0 || menuInput > menuAlternatives.size()) {
            try {
                menuInput = scan.nextInt();

                if (menuAlternatives.get(0).getClass().equals(MainMenu.class)) {
                    mainMenuChoice(menuInput);
                } else if (menuAlternatives.get(0).getClass().equals(AdminMenu.class)) {
                    adminMenuChoice(menuInput);
                } else {
                    userMenuChoice(menuInput);
                }
            } catch (Exception e) {
                setMenuChoice(menuAlternatives);
            }
        }
    }

    public void mainMenuChoice(int choice) {
        library = new Library();

        switch (choice) {
            case 1:
                library.showAllBooks();
                returnToMenu(MainMenu.values());

                break;

            case 2:
                library.searchBookByTitle();
                returnToMenu(MainMenu.values());

                break;

            case 3:
                library.searchBookByAuthor();
                returnToMenu(MainMenu.values());

                break;

            case 4:
                System.out.println("Main menu");

                break;

            case 5:
                System.out.println("Main menu");

                return;
        }
    }

    public void adminMenuChoice(int choice) {
        library = new Library();

        switch (choice) {
            case 1:
                library.showAllBooks();


                break;

            case 2:
                library.searchBookByTitle();

                break;

            case 3:
                library.searchBookByAuthor();

                break;

            case 4:
                System.out.println("Admin menu");

                break;

            case 5:
                System.out.println("Admin menu");

                break;

            case 6:
                System.out.println("Admin menu");

                break;

            case 7:
                System.out.println("Admin menu");

                break;

            case 8:
                System.out.println("Admin menu");

                return;
        }
    }

    public void userMenuChoice(int choice) {
        library = new Library();

        switch (choice) {
            case 1:
                library.showAllBooks();

                break;

            case 2:
                library.searchBookByTitle();

                break;

            case 3:
                library.searchBookByAuthor();

                break;

            case 4:
                System.out.println("User menu");

                break;

            case 5:
                System.out.println("User menu");

                break;

            case 6:
                System.out.println("User menu");

                return;
        }
    }

    public <T extends HasDescription> void returnToMenu(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        System.out.print("\n[0] to return: ");
        try {
            int menuChoice = scan.nextInt();
            if (menuChoice == 0) {
                initMenu(menuItems);
            } else {
                returnToMenu(menuItems);
            }
        } catch (Exception e) {
            returnToMenu(menuItems);
        }
    }
}
