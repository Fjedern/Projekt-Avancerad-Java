package com.company;

import com.company.Entities.Book;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Helpers.FileUtils;
import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library {

    public static List<Book> bookList = new ArrayList<>();
    public static List<Person> userList = new ArrayList<>();
    public Boolean isOpen = true;
    MenuHelper menuHelper = new MenuHelper();


    public Library() {

    }
    //Test

    //Funktionen som kör igång programmet
    public void openLibrary() {
        System.out.println("== Welcome to the library ==");
        //FileUtils.writeObject(bookList, "src/com/company/Files/Books.ser");

        bookList = (List<Book>) FileUtils.readObject("src/com/company/Files/Books.ser");
        //FileUtils.writeObject(userList, "src/com/company/Files/User.ser");
        userList = (List<Person>) FileUtils.readObject("src/com/company/Files/User.ser");


       /* Librarian.librarianRemoveBook();
        for(Book book : bookList) {
            System.out.println(book.getTitle());
        }*/
        menuHelper.runSystem(this);
    }


    public void checkLogin() {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println(RED + "Please login");
            System.out.println("[0] to return\n" + BLACK + " == Username ==");

            String scanUsername = scan.nextLine();

            for (Person person : userList) {
                if (scanUsername.equals(person.getUsername())) {
                    do {
                        System.out.println("Password: ");
                        String scanPassword = scan.nextLine();
                        if (scanPassword.equals(person.getPassword())) {
                            System.out.println("Logged in as: " + person.getName());
                            if (person instanceof User) {
                                System.out.println("User");

                                menuHelper.setCurrentUser(person);
                                menuHelper.initMenu(UserMenu.values());
                            } else {
                                System.out.println("Librarian");
                                menuHelper.initMenu(AdminMenu.values());
                            }
                            person.setLoggedIn(true);
                            break;
                        }
                        if (scanPassword.equals("0")) {
                            menuHelper.initMenu(MainMenu.values());
                            break;
                        }
                    } while (true);
                }
            }
            System.out.println("Wrong username");
            menuHelper.initMenu(MainMenu.values());
        } catch (Exception e){
            System.out.println("Something went wrong");
            menuHelper.initMenu(MainMenu.values());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Colors lol">
    private static final String RED = "\u001B[31m";
    private static final String BLACK = "\u001B[30m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    // </editor-fold>

    public void showAllBooks() {
        int i = 1;
        bookList.sort(Comparator.comparing(Book::getTitle));
        System.out.println("\n== All Books ==");

        for (Book book : bookList) {
            book.setI(i);
            System.out.println("[" + i + "] " + book.getTitle() + " by " + book.getAuthor());
            i++;
        }
    }

    public void searchBookByTitle() {
        Scanner scan = new Scanner(System.in);
        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by title: ");

        try {
            String regex = scan.nextLine();
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            bookList.sort(Comparator.comparing(Book::getTitle));

            System.out.println("\nTitle of books matching: '" + regex + "'");
            for (Book book : bookList) {
                book.setI(-1);
                Matcher matcher = pattern.matcher(book.getTitle());
                boolean matchFound = matcher.find();

                if (matchFound) {
                    matches++;
                    book.setI(i);
                    System.out.println("[" + i + "] " + book.getTitle().toUpperCase() + " by " + book.getAuthor());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println("No books matches your search");
            }

        } catch (Exception e) {
            searchBookByTitle();
        }
    }

    public void searchBookByAuthor() {
        Scanner scan = new Scanner(System.in);
        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by author: ");

        try {
            String regex = scan.nextLine();
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            bookList.sort(Comparator.comparing(Book::getAuthor));

            System.out.println("\nBooks by authors matching: '" + regex + "'");
            for (Book book : bookList) {
                book.setI(-1);
                Matcher matcher = pattern.matcher(book.getAuthor());
                boolean matchFound = matcher.find();

                if (matchFound) {
                    matches++;
                    book.setI(i);
                    System.out.println("[" + i + "] " + book.getTitle() + " by " + book.getAuthor().toUpperCase());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println("No authors matches your search");
            }

        } catch (Exception e) {
            searchBookByTitle();
        }
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Boolean getOpen() {
        return isOpen;
    }


    public void addBookToList(Book book) {
        bookList.add(book);
    }

    public void addUserToList(User user) {
        userList.add(user);
    }

    public static List<Person> getUserList() {
        return userList;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
