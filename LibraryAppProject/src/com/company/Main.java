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

        List<Book>books = new ArrayList<>();
        books.add(new Book("hej", "tjena", "hallå"));
        FileUtils.writeObject(books, "Book.ser");
        FileUtils fileUtils = new FileUtils();
        System.out.print(fileUtils.readObject("Book.ser"));




        MenuHelper menuHelper = new MenuHelper();

        menuHelper.initMenu(MainMenu.values());
        menuHelper.initMenu(AdminMenu.values());
        menuHelper.initMenu(UserMenu.values());



    }
}
