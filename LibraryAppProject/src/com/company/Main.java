package com.company;

import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

public class Main {

    public static void main(String[] args) {

        MenuHelper menuHelper = new MenuHelper();

        menuHelper.initMenu(MainMenu.values());
        menuHelper.initMenu(AdminMenu.values());
        menuHelper.initMenu(UserMenu.values());
    }
}
