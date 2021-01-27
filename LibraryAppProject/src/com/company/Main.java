package com.company;

import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

public class Main {

    public static void main(String[] args) {

        MenuHelper.initMenu(MainMenu.values());
        MenuHelper.initMenu(AdminMenu.values());
        MenuHelper.initMenu(UserMenu.values());
    }
}
