package com.company.Helpers;

import com.company.Menus.AdminMenu;
import com.company.Menus.HasDescription;
import com.company.Menus.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuHelper {

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

        switch (choice) {
            case 1:
                System.out.println("Main menu");
                break;

            case 2:
                System.out.println("Main menu");

                break;

            case 3:
                System.out.println("Main menu");

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

        switch (choice) {
            case 1:
                System.out.println("Admin menu");

                break;

            case 2:
                System.out.println("Admin menu");

                break;

            case 3:
                System.out.println("Admin menu");

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

        switch (choice) {
            case 1:
                System.out.println("User menu");

                break;

            case 2:
                System.out.println("User menu");

                break;

            case 3:
                System.out.println("User menu");

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


}
