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

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

import static com.company.Helpers.Color.*;

public class MenuHelper implements Serializable {


    User user;
    Librarian librarian;


    public MenuHelper() {
    }

    public void runSystem() {
        System.out.println(CYAN + "\n============================" + BLUE +
                "\n== WELCOME TO THE LIBRARY ==" + PURPLE +
                "\n============================" + RESET);
        initMenu(MainMenu.values());
    }

    public void setCurrentUser(Person loggedInUser) {
        user = (User) loggedInUser;
    }

    public void setCurrentLibrarian(Person loggedInLibrarian) {
        librarian = (Librarian) loggedInLibrarian;
    }

    public <T extends HasDescription> void initMenu(T[] menuItems) {
        if (Library.getInstance().getOpen()) {
            System.out.println();
            int i = 1;

            for (T menuItem : menuItems) {
                System.out.println(CYAN + "[" + i + "] " + RESET + menuItem.getDescription());
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
                Library.getInstance().showAllBooks();
                selectBookOption(MainMenu.values(), Library.getInstance().getBookList());
            }
            case 2 -> {
                Library.getInstance().searchBookByTitle();
                selectBookOption(MainMenu.values(), Library.getInstance().getBookList());
            }
            case 3 -> {
                Library.getInstance().searchBookByAuthor();
                selectBookOption(MainMenu.values(), Library.getInstance().getBookList());
            }
            case 4 -> {
                Library.getInstance().checkLoginV2();
            }
            case 5 -> {
                System.out.println("Shutting down system");
                Library.getInstance().setOpen(false);
                initMenu(MainMenu.values());


            }
        }
    }

    public void adminMenuChoice(int choice) {

        switch (choice) {

            case 1 -> {
                Library.getInstance().showAllBooks();
                selectBookOption(AdminMenu.values(), Library.getInstance().getBookList());
            }

            case 2 -> {
                Library.getInstance().searchBookByTitle();
                selectBookOption(AdminMenu.values(), Library.getInstance().getBookList());
            }

            case 3 -> {
                Library.getInstance().searchBookByAuthor();
                selectBookOption(AdminMenu.values(), Library.getInstance().getBookList());
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
                librarian.setLoggedIn(false);
                initMenu(MainMenu.values());
            }
        }
    }


    public void userMenuChoice(int choice) {

        switch (choice) {
            case 1 -> {
                Library.getInstance().showAllBooks();
                selectBookOption(UserMenu.values(), Library.getInstance().getBookList());
            }

            case 2 -> {
                Library.getInstance().searchBookByTitle();
                selectBookOption(UserMenu.values(), Library.getInstance().getBookList());
            }

            case 3 -> {
                Library.getInstance().searchBookByAuthor();
                selectBookOption(UserMenu.values(), Library.getInstance().getBookList());
            }

            case 4 -> {
                user.borrowBooks();
                generalReturnMenu(UserMenu.values());
            }

            case 5 -> {
                System.out.println(CYAN + "\nUSER: " + RESET + user.getName() + "\n");
                user.showUserBooks();
                selectBookOption(UserMenu.values(), user.getBooks());
            }

            case 6 -> {
                user.returnBookFromMenu();
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
        System.out.print(CYAN + "\n[T]" + RESET + " Sort by Title" + CYAN + "  [A]" + RESET + " Sort by Author" + CYAN + "  [0]" + RESET + " Back to menu \n\nMake a choice: ");

        try {
            String menuChoice = scan.nextLine();

            if (menuChoice.equalsIgnoreCase("T")) {
                System.out.println("\nHÄR SORTERAR VI EFTER TITEL\n");
                generalReturnMenu(menuItems);

            } else if (menuChoice.equalsIgnoreCase("A")) {
                System.out.println("\nHÄR SORTERAR VI EFTER AUTHOR\n");
                generalReturnMenu(menuItems);

            } else {
                try {
                    int intChoice = Integer.parseInt(menuChoice);

                    if (intChoice == 0) {
                        initMenu(menuItems);
                    } else if (intChoice > 0) {

                        for (Book book : booksToChoose) {
                            if (intChoice == book.getI()) {
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
            System.out.println(CYAN + "\nUSER: " + RESET + user.getName() + "\n");
            userBookMenu(book, booksToChoose);
        }
    }


    // General method for returning to main/admin/user menu (depending on type of user)
    // by entering 0
    private <T extends HasDescription> void generalReturnMenu(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        System.out.print(CYAN + "\n[0]" + RESET + " to return: ");

        try {
            int input = scan.nextInt();
            if (input == 0) {
                initMenu(menuItems);
            } else {
                generalReturnMenu(menuItems);
            }
        } catch (Exception e) {
            generalReturnMenu(menuItems);
        }
    }

    private void adminBookMenu(Book book) {
        Scanner scan = new Scanner(System.in);
        System.out.println(CYAN + "\n[1]" + RESET + " Remove book \n" + CYAN + "[0]" + RESET + " Back to menu\n");
        System.out.print("Make a choice: ");

        try {
            int input = scan.nextInt();

            if (input == 0) {
                initMenu(AdminMenu.values());

            } else if (input == 1) {
                if (book.isAvailable()) {
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " removed from system");
                    Library.getInstance().getBookList().remove(book);

                } else {
                    System.out.println(book.getTitle() + " is loaned out and cannot be removed\n" + book.showDaysRemainingOnLoan() + "\n");
                }
                generalReturnMenu(AdminMenu.values());

            } else {
                adminBookMenu(book);
            }

        } catch (Exception e) {
            adminBookMenu(book);
        }
    }


    //Kollar om listan med böcker som skickas in är från biblioteket eller användaren
    //och ger user olika alternativ beroende på det
    private void userBookMenu(Book book, List<Book> books) {
        Scanner scan = new Scanner(System.in);

        if (books.equals(Library.getInstance().getBookList())) {
            System.out.println(CYAN + "\n[1]" + RESET + " Borrow book \n" + CYAN + "[0]" + RESET + " Back to menu\n");

        } else {
            System.out.println(CYAN + "\n[1]" + RESET + " Return book to library \n" + CYAN + "[0]" + RESET + " Back to menu\n");
        }

        System.out.print("Make a choice: ");


        try {
            int input = scan.nextInt();

            if (input == 0) {
                initMenu(UserMenu.values());

            } else if (input == 1 && books.equals(Library.getInstance().getBookList())) {
                if (book.isAvailable()) {
                    System.out.println("\n" + user.getName() + " loans " + book.getTitle());
                    user.borrowBook(book);
                    generalReturnMenu(UserMenu.values());

                } else {
                    System.out.println("\n" + book.getTitle() + " is already loaned out\n" + book.showDaysRemainingOnLoan() + "\n");
                    generalReturnMenu(UserMenu.values());
                }

            } else if (input == 1 && books.equals(user.getBooks())) {
                System.out.println("\n" + book.getTitle() + " by " + book.getAuthor() + " returned to library");
                user.returnBook(book);
                generalReturnMenu(UserMenu.values());

            } else {
                userBookMenu(book, books);
            }

        } catch (Exception e) {
            userBookMenu(book, books);
        }
    }
}
