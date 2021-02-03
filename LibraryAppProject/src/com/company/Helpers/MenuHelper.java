package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Entities.Librarian;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Library;
import com.company.Menus.AdminMenu;
import com.company.Menus.HasDescription;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.util.List;
import java.util.Scanner;

import static com.company.Helpers.Color.*;

public class MenuHelper {

    Library library;
    User user;
    Librarian librarian;


    public MenuHelper() {
    }

    public void runSystem(Library openLibrary) {
        library = openLibrary;
        initMenu(MainMenu.values());
    }

    public <T extends HasDescription> void initMenu(T[] menuItems) {
        if (library.getOpen()) {
            System.out.println();
            int i = 1;

            for (T menuItem : menuItems) {
                System.out.println(BLUE + "[" + i + "] " + RESET + menuItem.getDescription());
                i++;
            }
            setMenuChoice(menuItems);
        }
    }


    private <T extends HasDescription> void setMenuChoice(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        int menuInput = -1;

        System.out.print("\nMake a choice: ");

        while (menuInput < 0 || menuInput > menuItems.length) {
            try {
                menuInput = scan.nextInt();

                if (menuItems[0].getClass().equals(MainMenu.class)) {
                    mainMenuChoice(menuInput);
                } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
                    adminMenuChoice(menuInput);
                } else {
                    userMenuChoice(menuInput);
                }

            } catch (Exception e) {
                setMenuChoice(menuItems);
            }
        }
    }

    public void mainMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                library.showAllBooks();
                selectBookOption(MainMenu.values(), library.getBookList());
            }
            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(MainMenu.values(), library.getBookList());
            }
            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(MainMenu.values(), library.getBookList());
            }
            case 4 -> {
                System.out.println();
                library.checkLoginV2();
            }
            case 5 -> {
                System.out.println("Shutting down system");
                library.setOpen(false);
                initMenu(MainMenu.values());


            }
        }
    }

    public void adminMenuChoice(int choice) {

        switch (choice) {

            case 1 -> {
                library.showAllBooks();
                selectBookOption(AdminMenu.values(), library.getBookList());
            }

            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(AdminMenu.values(), library.getBookList());
            }

            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(AdminMenu.values(), library.getBookList());
            }


            case 4 -> {
                librarian.seeAllUsers();
                generalReturnMenu(AdminMenu.values());
            }
            case 5 -> {
                librarian.librarianAddUser();
                generalReturnMenu(AdminMenu.values());
            }
            case 6 -> {
                librarian.librarianRemoveUser();
                generalReturnMenu(AdminMenu.values());
            }
            case 7 -> {
                librarian.librarianAddBook();
                generalReturnMenu(AdminMenu.values());
            }


            case 8 -> {
                librarian.librarianRemoveBookByTitle();
                generalReturnMenu(AdminMenu.values());

            }

            case 9 -> {
                System.out.println("Logging out");

                initMenu(MainMenu.values());

            }
        }
    }


    public void userMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                library.showAllBooks();
                selectBookOption(UserMenu.values(), library.getBookList());
            }

            case 2 -> {
                library.searchBookByTitle();
                selectBookOption(UserMenu.values(), library.getBookList());
            }

            case 3 -> {
                library.searchBookByAuthor();
                selectBookOption(UserMenu.values(), library.getBookList());
            }

            case 4 -> {
                user.borrowBooks(library);
                generalReturnMenu(UserMenu.values());
            }

            case 5 -> {
                user.showUserBooks();
                selectBookOption(UserMenu.values(), user.getBooks());
            }

            case 6 -> {
                user.returnBook();
                generalReturnMenu(UserMenu.values());

            }

            case 7 -> {
                System.out.println("Logging out");
                user.setLoggedIn(false);
                initMenu(MainMenu.values());

            }
        }
    }


    public <T extends HasDescription> void selectBookOption(T[] menuItems, List<Book> booksToChoose) {
        Scanner scan = new Scanner(System.in);
        System.out.print(BLUE + "\n[0]" + RESET + " to return. Make a choice: ");
        try {
            int menuChoice = scan.nextInt();

            if (menuChoice == 0) {
                initMenu(menuItems);

            } else if (menuChoice > 0) {
                for (Book book : booksToChoose) {
                    if (menuChoice == book.getI()) {
                        book.showBookInfo();
                        initBookMenu(menuItems, book, booksToChoose);
                    }
                }
            } else {
                selectBookOption(menuItems, booksToChoose);
            }

        } catch (Exception e) {
            selectBookOption(menuItems, booksToChoose);
        }
    }

    private <T extends HasDescription> void initBookMenu(T[] menuItems, Book book, List<Book> booksToChoose) {

        if (menuItems[0].getClass().equals(MainMenu.class)) {
            generalReturnMenu(menuItems);

        } else if (menuItems[0].getClass().equals(AdminMenu.class)) {
            adminBookMenu(book);

        } else {
            userBookMenu(book, booksToChoose);
        }
    }


    // General method for returning to main/admin/user menu (depending on type of user)
    // by entering 0
    private <T extends HasDescription> void generalReturnMenu(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        System.out.print(BLUE + "\n[0]" + RESET + " to return: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(menuItems);
            }
        } catch (Exception e) {
            generalReturnMenu(menuItems);
        }
    }

    private void adminBookMenu(Book book) {
        Librarian librarian = new Librarian();
        Scanner scan = new Scanner(System.in);
        System.out.println(BLUE + "\n[1]" + RESET + " Remove book \n" + BLUE + "[0]" + RESET + " to return\n");
        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();

            if (input == 0) {
                initMenu(AdminMenu.values());
            } else if (input == 1) {

                if (book.isAvailable()) {
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " removed from system");
                    library.getBookList().remove(book);
                } else {
                    System.out.println(book.getTitle() + " is loaned out and cannot be removed\n" + book.showDaysRemainingOnLoan() + "\n");
                }
                generalReturnMenu(AdminMenu.values());
            }
        } catch (Exception e) {
            adminBookMenu(book);
        }
    }


    //Kollar om listan med böcker som skickas in är från biblioteket eller användaren
    //och ger user olika alternativ beroende på det
    private void userBookMenu(Book book, List<Book> books) {
        Scanner scan = new Scanner(System.in);

        if (books.equals(library.getBookList())) {
            System.out.println(BLUE + "\n[1]" + RESET + " Borrow book \n" + BLUE + "[0]" + RESET + " to return\n");
        } else {
            System.out.println(BLUE + "\n[1]" + RESET + " Return book to library \n" + BLUE + "[0]" + RESET + " to return\n");
        }

        System.out.print("Make a choice: ");


        try {
            int input = scan.nextInt();

            if (input == 0) {
                initMenu(UserMenu.values());
            } else if (input == 1 && books.equals(library.getBookList())) {
                System.out.println("In libraries books");

                if (book.isAvailable()) {
                    System.out.println(user.getName() + " loans " + book.getTitle());
                    user.borrowBook(book);

                } else {
                    System.out.println(book.getTitle() + " is already loaned out\n(" + book.showDaysRemainingOnLoan() + ")\n");
                    generalReturnMenu(UserMenu.values());
                }

            } else if (input == 1 && books.equals(user.getBooks())) {
                System.out.println(book.getTitle() + " by " + book.getAuthor() + " returned to library");
                user.returnBook(book, library);
                generalReturnMenu(UserMenu.values());

            } else {
                userBookMenu(book, books);
            }
        } catch (Exception e) {
            userBookMenu(book, books);
        }
    }

    public void setCurrentUser(Person loggedInUser) {
        user = (User) loggedInUser;
    }

    public void setCurrentLibrarian(Person loggedInLibrarian) {
        librarian = (Librarian) loggedInLibrarian;
        librarian.setLibrary(library);
    }
}
