package com.company.Helpers;

import com.company.Menus.HasDescription;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    public MenuHelper() {
    }

    public static <T extends HasDescription> void initMenu(T[] menuItems) {
        System.out.println();
        int i = 1;
        for (T menuItem : menuItems) {
            System.out.println("[" + i + "] " + menuItem.getDescription());
            i++;

        }





    }


}
