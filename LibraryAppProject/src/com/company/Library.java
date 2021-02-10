package com.company;

import com.company.Entities.Book;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Helpers.FileUtils;
import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.GetMenuValues;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.company.Helpers.Color.*;

public class Library implements Serializable {

    private static Library library;
    public Map<String, Book> books = new HashMap<>();
    public Map<String, Person> users = new HashMap<>();
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

    // Checks if it can read the .ser-files. If not it clears hashmaps 'users' and 'books'
    // and fills them with new users and books from backup text-files
    // Then calls method to start the "System"
    public void openLibrary() {

        if (FileUtils.readObject("src/com/company/Files/Books.ser") == null || FileUtils.readObject("src/com/company/Files/User.ser") == null) {
            users.clear();
            books.clear();
            FileUtils.addBooks();
            FileUtils.addUsers();
            FileUtils.writeObject(books, "src/com/company/Files/Books.ser");
            FileUtils.writeObject(users, "src/com/company/Files/User.ser");

        } else {
            books = (Map<String, Book>) FileUtils.readObject("src/com/company/Files/Books.ser");
            users = (Map<String, Person>) FileUtils.readObject("src/com/company/Files/User.ser");
        }

        menuHelper.runSystem();
    }

    public List<Person> getUsersAsList() {
        Collection<Person> persons = users.values();
        return persons.stream().parallel()
                .filter(person -> person instanceof User)
                .sorted(Comparator.comparing(p -> p.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksAsList() {
        Collection<Book> bookList = books.values();
        return bookList.stream().parallel()
                .sorted(Comparator.comparing(b -> b.getTitle().toUpperCase()))
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
                            menuHelper.setCurrentPerson(person);
                            person.setLoggedIn(true);
                            if (((User) person).getBooks().size() > 0) {
                                reminder(((User) person).userBooks);
                                return;
                            } else {
                                menuHelper.initMenu(UserMenu.values());

                            }


                        } else {
                            System.out.println(GREEN + "\nWelcome " + person.getName() + "!\nYou are logged in as a " + YELLOW + "Librarian" + RESET);
                            menuHelper.setCurrentPerson(person);
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

    private void reminder(List<Book> books) {
        List<Book> overdueBooks = new ArrayList<>();
        for (Book book : books) {
            if (LocalDate.now().until(book.getReturnBookDate()).getDays() < 0) {
                overdueBooks.add(book);
            }
        }
        if (overdueBooks.size() > 0) {
            System.out.println("\n--------------------------------------------");
            System.out.println(RED + "YOU HAVE OVERDUE BOOKS!" + RESET);
            overdueBooks.forEach(book -> System.out.println("* " + YELLOW + book.getTitle() + RESET));
            System.out.println("--------------------------------------------");
            menuHelper.generalReturnMenu(UserMenu.values());

        } else {
            menuHelper.initMenu(UserMenu.values());
        }

    }

    public <T extends GetMenuValues> void showAvailableBooks(T[] menuItems) {
        List<Book> booksAvailable = new ArrayList<>();
        int matches = 0;
        int i = 1;
        System.out.println(YELLOW + "\n== AVAILABLE BOOKS ==" + RESET);
        for (Book book : getBooksAsList()) {
            if (book.isAvailable()) {
                System.out.print("Check");
                book.setI(i);
                booksAvailable.add(book);
                System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor());
                matches++;
                i++;
            }
        }

        if (matches == 0) {
            System.out.print("There are no books available");
        } else {
            menuHelper.selectBookOption(menuItems, booksAvailable);
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

    public <T extends GetMenuValues> void sortBooks(List<Book> booksToSort, String compare, T[] menuItems) {
        int i = 1;

        if (compare.equalsIgnoreCase("T")) {
            booksToSort.sort(Comparator.comparing(Book::getTitle));
            System.out.println(YELLOW + "\n== SORTED BY TITLE ==" + RESET);

            for (Book book : booksToSort) {
                book.setI(i);
                System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getTitle().toUpperCase() + RESET + " by " + book.getAuthor());
                i++;
            }
            menuHelper.selectBookOption(menuItems, booksToSort);

        } else if (compare.equalsIgnoreCase("A")) {
            booksToSort.sort(Comparator.comparing(Book::getAuthor));
            System.out.println(YELLOW + "\n== SORTED BY AUTHOR ==" + RESET);

            for (Book book : booksToSort) {
                book.setI(i);
                System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getAuthor().toUpperCase() + RESET + " - " + book.getTitle());
                i++;
            }
            menuHelper.selectBookOption(menuItems, booksToSort);
        }
    }

    public <T extends GetMenuValues> void searchBookByTitle(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        List<Book> booksByTitle = new ArrayList<>();
        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by title: ");

        try {
            String input = scan.nextLine();
            System.out.println(YELLOW + "\n== TITLE OF BOOKS MATCHING: '" + input + "' ==" + RESET);

            for (Book book : getBooksAsList()) {
                book.setI(-1);

                if (book.getTitle().toUpperCase().contains(input.toUpperCase())) {
                    matches++;
                    book.setI(i);
                    booksByTitle.add(book);
                    System.out.println(CYAN + "[" + i + "] " + RESET + YELLOW + book.getTitle().toUpperCase() + RESET + " by " + book.getAuthor());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println(RED + "\nNo books matches your search" + RESET);
            } else {
                menuHelper.selectBookOption(menuItems, booksByTitle);
            }
        } catch (Exception e) {
            searchBookByTitle(menuItems);
        }
    }

    public <T extends GetMenuValues> void searchBookByAuthor(T[] menuItems) {
        Scanner scan = new Scanner(System.in);
        List<Book> tempList = getBooksAsList();
        List<Book> booksByAuthor = new ArrayList<>();
        tempList.sort(Comparator.comparing(Book::getAuthor));

        int i = 1;
        int matches = 0;
        System.out.print("\nSearch books by author: ");

        try {
            String input = scan.nextLine();

            System.out.println(YELLOW + "\n== BOOKS BY AUTHORS MATCHING: '" + input + "' ==" + RESET);
            for (Book book : tempList) {
                book.setI(-1);

                if (book.getAuthor().toUpperCase().contains(input.toUpperCase())) {
                    matches++;
                    booksByAuthor.add(book);
                    book.setI(i);
                    System.out.println(CYAN + "[" + i + "] " + YELLOW + book.getAuthor().toUpperCase() + RESET + " - " + book.getTitle());
                    i++;
                }
            }

            if (matches == 0) {
                System.out.println(RED + "\nNo authors matches your search" + RESET);
                menuHelper.generalReturnMenu(menuItems);

            } else {
                menuHelper.selectBookOption(menuItems, booksByAuthor);
            }

        } catch (Exception e) {
            searchBookByTitle(menuItems);
        }
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
