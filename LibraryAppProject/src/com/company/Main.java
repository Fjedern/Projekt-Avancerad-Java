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
        /*Librarian.librarianAddBook();
        Librarian.librarianAddBook();
        Librarian.librarianAddBook();
        System.out.println(Library.bookList);
        Librarian.librarianRemoveBook();
        System.out.println(Library.bookList);






        menuHelper.initMenu(AdminMenu.values());
        menuHelper.initMenu(UserMenu.values());*/
        Library library = new Library();


        MenuHelper menuHelper = new MenuHelper();

        Book b1 = new Book("Sagan om ringen", "Frodo walking and having fun with his friends","J.R.R. Tolkien");
        Book b2 = new Book("Sagan om de två tornen", "Frodo having some more fun with his friends","J.R.R. Tolkien");
        Book b3 = new Book("Sagan om de konungens återkomst", "Frodo having even more fun with his friends","J.R.R. Tolkien");
        Book b4 = new Book("Papillion", "Papillion having fun on his own","Henri Charriere");
        Book b5 = new Book("Fakta om Finland", "Interesting fact about Finland","Erland Loe");
        Book b6 = new Book("Oryx and Crake", "A story about life in the future","Margaret Atwood");

        library.addBookToList(b1);
        library.addBookToList(b2);
        library.addBookToList(b3);
        library.addBookToList(b4);
        library.addBookToList(b5);
        library.addBookToList(b6);

        /*library.showAllBooks();
        library.searchBookByTitle();
        library.searchBookByAuthor();
*/

        menuHelper.initMenu(MainMenu.values());









    }

}
