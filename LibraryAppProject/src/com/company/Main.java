package com.company;


import com.company.Entities.Book;
import com.company.Entities.Librarian;
import com.company.Entities.User;
import com.company.Helpers.FileUtils;

import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Librarian.librarianAddBook();
        Librarian.librarianAddBook();
        System.out.println(Library.bookList);
        Librarian.librarianRemoveBook();
        System.out.println(Library.bookList);
        
        MenuHelper menuHelper = new MenuHelper();

        menuHelper.initMenu(MainMenu.values());
        menuHelper.initMenu(AdminMenu.values());
        menuHelper.initMenu(UserMenu.values());



    }

}
