package com.company;

import com.company.Entities.Book;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Helpers.FileUtils;
import com.company.Helpers.MenuHelper;
import com.company.Helpers.ShowBooksHandler;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.Helpers.Color.*;

public class Library implements Serializable {

    private static Library library;
    public Map<String, Book> books = new HashMap<>();
    public Map<String, Person> users = new HashMap<>();
    private final MenuHelper menuHelper;
    private final ShowBooksHandler showBooksHandler;


    private Library() {
        this.menuHelper = new MenuHelper();
        this.showBooksHandler = new ShowBooksHandler();
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
        return users.values().stream().parallel()
                .filter(p -> p instanceof User)
                .sorted(Comparator.comparing(p -> p.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksAsList() {
        return books.values().stream().parallel()
                .sorted(Comparator.comparing(b -> b.getTitle().toUpperCase()))
                .collect(Collectors.toList());
    }

    public void checkLogin() {
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
                                overdueBookReminder(((User) person).userBooks);
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
            checkLogin();
        }
    }

    private void overdueBookReminder(List<Book> books) {
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

    public ShowBooksHandler getShowBooksHandler() {
        return showBooksHandler;
    }

    public void addBookToMap(Book book) {
        books.put(book.getTitle(), book);
    }

    public MenuHelper getMenuHelper() {
        return menuHelper;
    }
}