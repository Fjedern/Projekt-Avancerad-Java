package com.company;

import com.company.Entities.Book;
import com.company.Entities.Person;
import com.company.Entities.User;
import com.company.Helpers.MenuHelper;
import com.company.Menus.AdminMenu;
import com.company.Menus.MainMenu;
import com.company.Menus.UserMenu;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.Helpers.Color.*;

public class Library {


    public static List<Book> bookList = new ArrayList<>();
    public static List<Person> userList = new ArrayList<>();
    Map<String, Person> userListAsMap = new HashMap<>();

    public Boolean isOpen = true;
    MenuHelper menuHelper = new MenuHelper();


    public Library() {

    }
    //Test

    //Funktionen som kör igång programmet
    public void openLibrary() {
        System.out.println(CYAN + "\n============================" + BLUE +
                "\n== WELCOME TO THE LIBRARY ==" + PURPLE +
                "\n============================" + RESET);
        //FileUtils.writeObject(bookList, "src/com/company/Files/Books.ser");
        //bookList = (List<Book>) FileUtils.readObject("src/com/company/Files/Books.ser");
        //FileUtils.writeObject(userList, "src/com/company/Files/User.ser");
        //userList = (List<Person>) FileUtils.readObject("src/com/company/Files/User.ser");

        //FileUtils.writeObject(userList, "src/com/company/Files/User.ser");

        logOutAllUsers();
       /* Librarian.librarianRemoveBook();
        for(Book book : bookList) {
            System.out.println(book.getTitle());
        }*/
        menuHelper.runSystem(this);
    }

    public void fillUserListMap(){
        for(Person person : userList){
            userListAsMap.put(person.getUsername(), person);
        }
    }
  
  public void logOutAllUsers() {
        for (Person person : userList) {
            person.setLoggedIn(false);
        }
    }


    public void checkLoginV2(){
        Scanner scan = new Scanner(System.in);

        System.out.println("\n" +RED + "Please login");
        System.out.println(RESET + "[0] to return\n\n" + PURPLE + " == Username ==" + RESET);

        String scanUsername = scan.nextLine();
        if(scanUsername.equals("0")){
            menuHelper.initMenu(MainMenu.values());
            return;
        }

        boolean isKeyPresent = userListAsMap.containsKey(scanUsername);
        if(isKeyPresent){
            Person person = userListAsMap.get(scanUsername);
            if(person.isLoggedIn()){
                System.out.println("Already logged in");
            } else {
                do {
                    System.out.println(PURPLE + "== Password ==" + RESET);
                    String scanPass = scan.nextLine();
                    if(scanPass.equals(person.getPassword())){
                        if(person instanceof User){
                            System.out.println(GREEN + "\nWelcome " + person.getName() +  "!\nYou are logged in as a " + YELLOW +  "User" + RESET);
                            menuHelper.initMenu(UserMenu.values());
                        }else{
                            System.out.println(GREEN + "\nWelcome " + person.getName() +  "!\nYou are logged in as a " + YELLOW +  "Librarian" + RESET);
                            menuHelper.initMenu(AdminMenu.values());
                        }
                        person.setLoggedIn(true);
                        menuHelper.setCurrentUser(person);
                        break;
                    }else {
                        System.out.println(RED + "Wrong password please try again" + RESET);
                    }
                } while(true);
            }
        } else {
            System.out.println(RED + "Wrong username please try again" + RESET);
            checkLoginV2();
        }
    }
  
    public void showAllBooks() {
        Scanner scan = new Scanner(System.in);
        int i = 1;
        bookList.sort(Comparator.comparing(Book::getTitle));
        System.out.println("\n== All Books ==");

        for (Book book : bookList) {
            book.setI(i);
            System.out.println(BLUE + "[" + i + "] " + RESET + book.getTitle() + " by " + book.getAuthor());
            i++;
        }

        System.out.println("\nWould you like to sort list by Author or Title?");
        System.out.printf("Type: ");

        String sortOption = scan.nextLine();
        if (sortOption.equalsIgnoreCase("Author") || sortOption.equalsIgnoreCase("Title")) {
            sortBooks(sortOption);

        }


    }

    public void sortBooks(String compare) {
        int i = 1;
        if (compare == "T") {
            bookList.sort(Comparator.comparing(Book::getTitle));


            for (Book book : bookList) {
                book.setI(i);
                System.out.println(BLUE + "[" + i + "] " + RESET + book.getTitle() + " by " + book.getAuthor());
                i++;
            }

        } else {
            bookList.sort(Comparator.comparing(Book::getAuthor));
            for (Book book : bookList) {
                book.setI(i);
                System.out.println(BLUE + "[" + i + "] " + RESET + book.getAuthor() + " - " + book.getTitle());
                i++;
            }


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
                    System.out.println(BLUE + "[" + i + "] " + RESET + book.getTitle().toUpperCase() + " by " + book.getAuthor());
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
                    System.out.println(BLUE + "[" + i + "] " + RESET + book.getTitle() + " by " + book.getAuthor().toUpperCase());
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

    public List<Person> getUserList() {
        return userList;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
