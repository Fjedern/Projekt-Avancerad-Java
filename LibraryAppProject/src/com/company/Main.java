package com.company;

import com.company.Entities.Librarian;
import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

public class Main {

    public static void main(String[] args) {
        Librarian.librarianAddBook();
        Librarian.librarianAddBook();
        System.out.println(Library.bookList);
        Librarian.librarianRemoveBook();
        System.out.println(Library.bookList);

        MenuHelper.initMenu(MainMenu.values());
        MenuHelper.initMenu(AdminMenu.values());
        MenuHelper.initMenu(UserMenu.values());
    }

}
