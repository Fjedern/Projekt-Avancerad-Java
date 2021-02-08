package com.company;

import com.company.Entities.Book;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Helpers.FileUtils;
import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.company.Helpers.Color.*;

public class Library implements Serializable {

    private static Library library;
    public Map<String, Book> books = new HashMap<>();
    public Map<String, Person> users = new HashMap<>();

    public Map<String, Book> booksBySearch = new HashMap<>();

    private Boolean isOpen = true;
    private final MenuHelper menuHelper;


    private Library() {
        this.menuHelper = new MenuHelper();
    }

    public static Library getInstance() {
        if (library == null) {
            library = new Library();
        }
        return library;
    }

    //Funktionen som kör igång programmet
    public void openLibrary() {

        if (FileUtils.readObject("src/com/company/Files/Books.ser") == null) {
            FileUtils.addBooks();

        } else if (FileUtils.readObject("src/com/company/Files/User.ser") == null) {
            FileUtils.addUsers();

        } else {
            books = (Map<String, Book>) FileUtils.readObject("src/com/company/Files/Books.ser");
            users = (Map<String, Person>) FileUtils.readObject("src/com/company/Files/User.ser");
        }

        /*FileUtils.addUsers();
        FileUtils.addBooks();*/

        /*FileUtils.writeObject(books, "src/com/company/Files/Books.ser");
        FileUtils.writeObject(users, "src/com/company/Files/User.ser");*/

        menuHelper.runSystem();
    }

    public List<Person> getUsersAsList() {
        Collection<Person> persons = users.values();
        return persons.stream().parallel()
                .filter(person -> person instanceof User)
                .sorted(Comparator.comparing(Person::getName))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksAsList() {
        Collection<Book> bookList = books.values();
        return bookList.stream().parallel()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<Book> getSearchedBooksAsList() {
        Collection<Book> bookList = booksBySearch.values();
        return bookList.stream().parallel()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public void checkLoginV2() {
        Scanner scan = new Scanner(System.in);
        boolean checkLogin = true;
        System.out.println("\n" + RED + "Please login");
        System.out.println(RESET + "[0] to return\n\n" + PURPLE + " == Username ==" + RESET);

        String scanUsername = scan.nextLine();
        if (scanUsername.equals("0")) {

            menuHelper.initMenu(MainMenu.values());
            return;
        }

        boolean isKeyPresent = users.containsKey(scanUsername);
        if (isKeyPresent) {
            Person person = users.get(scanUsername);
            if (person.isLoggedIn()) {
                System.out.println("Already logged in");
            } else {
                do {
                    System.out.println(PURPLE + "== Password ==" + RESET);
                    String scanPass = scan.nextLine();

                    if (scanPass.equals("0")) {
                        checkLogin = false;
                        menuHelper.initMenu(MainMenu.values());

                    }
                    if (scanPass.equals(person.getPassword())) {
                        checkLogin = false;

                        if (person instanceof User) {
                            System.out.println(GREEN + "\nWelcome " + person.getName() + "!\nYou are logged in as a " + YELLOW + "User" + RESET);
                            menuHelper.setCurrentUser(person);
                            person.setLoggedIn(true);

                            menuHelper.initMenu(UserMenu.values());

                        } else {
                            System.out.println(GREEN + "\nWelcome " + person.getName() + "!\nYou are logged in as a " + YELLOW + "Librarian" + RESET);
                            menuHelper.setCurrentLibrarian(person);
                            person.setLoggedIn(true);
                            menuHelper.initMenu(AdminMenu.values());
                        }


                    } else {
                        System.out.println(RED + "\nWrong password! Try again" + RESET);


                    }
                } while (checkLogin);
            }
        } else {
            System.out.println(RED + "\nWrong username! Please try again" + RESET);
            checkLoginV2();
        }

    }

    public void showAllBooks() {
        int i = 1;
        System.out.println(YELLOW + "\n== ALL BOOKS ==" + RESET);

        for (Book book : getBooksAsList()) {
            book.setI(i);
            System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor());
            i++;
        }
    }

    public void sortBooks(List<Book>booksToSort, String compare) {
        int i = 1;
        if (compare.equals("T")) {
            booksToSort.sort(Comparator.comparing(Book::getTitle));

            for (Book book : booksToSort) {
                book.setI(i);
                System.out.println(CYAN + "[" + i + "] " + RESET + book.getTitle() + " by " + book.getAuthor());
                i++;
            }

        } else {
            booksToSort.sort(Comparator.comparing(Book::getAuthor));
            for (Book book : booksToSort) {
                book.setI(i);
                System.out.println(CYAN + "[" + i + "] " + RESET + book.getAuthor() + " - " + book.getTitle());
                i++;
            }
        }
        booksBySearch.clear();
    }

    public void searchBookByTitle() {
        Scanner scan = new Scanner(System.in);
        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by title: ");

        try {
            String regex = scan.nextLine();
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            System.out.println(YELLOW + "\n== TITLE OF BOOKS MATCHING: '" + regex + "' ==" + RESET);

            for (Book book : getBooksAsList()) {
                book.setI(-1);
                Matcher matcher = pattern.matcher(book.getTitle());
                boolean matchFound = matcher.find();

                if (matchFound) {
                    matches++;
                    booksBySearch.put(book.getTitle(), book);
                    book.setI(i);
                    System.out.println(CYAN + "[" + i + "] " + RESET + YELLOW + book.getTitle().toUpperCase() + RESET + " by " + book.getAuthor());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println("\nNo books matches your search");
            }

        } catch (Exception e) {
            searchBookByTitle();
        }
    }

    public void searchBookByAuthor() {
        Scanner scan = new Scanner(System.in);
        List<Book> booksByAuthor = getBooksAsList();
        booksByAuthor.sort(Comparator.comparing(Book::getAuthor));

        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by author: ");

        try {
            String regex = scan.nextLine();
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            System.out.println(YELLOW + "\n== BOOKS BY AUTHORS MATCHING: '" + regex + "' ==" + RESET);

            for (Book book : booksByAuthor) {
                book.setI(-1);
                Matcher matcher = pattern.matcher(book.getAuthor());
                boolean matchFound = matcher.find();

                if (matchFound) {
                    matches++;
                    book.setI(i);
                    System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getAuthor().toUpperCase() + RESET + " - " + book.getTitle());
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

    public void addBookToMap(Book book) {
        books.put(book.getTitle(), book);
    }

    public void addUserToList(User user) {
        users.put(user.getUsername(), user);
    }

    public MenuHelper getMenuHelper() {
        return menuHelper;
    }
}
