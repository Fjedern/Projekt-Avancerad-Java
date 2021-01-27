package com.company.Helpers;

import com.company.Menus.HasDescription;

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




       // System.out.println(menuAlternatives.get(0).getClass().equals(MainMenu.class));




        while (menuInput < 0 || menuInput > menuAlternatives.size()) {
            try {
                menuInput = scan.nextInt();
                mainMenuChoice(menuInput);

            } catch (Exception e) {
                setMenuChoice(menuAlternatives);
            }
        }
    }

    public void mainMenuChoice(int choice) {

        switch (choice) {
            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

        }
    }


}
