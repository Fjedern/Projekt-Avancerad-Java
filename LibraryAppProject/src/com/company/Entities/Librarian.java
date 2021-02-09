package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.Helpers.Color.*;

public class Librarian extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }

    public Librarian() {

    }

    public void librarianAddBook() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String titleName = scan.nextLine();
        if(!isBookTitleValid(titleName)){
            System.out.println(YELLOW+"The book does already exist"+RESET);
            return;
        }
        while (titleName.isBlank() || titleName.isEmpty()){
            System.out.println(RED+"Enter a book title, it can't be empty: "+RESET);
            titleName = scan.nextLine();
        }
        System.out.println("Enter a short description: ");
        String description = scan.nextLine();
        while (description.isBlank() || description.isEmpty()){
            System.out.println(RED+"The description can't be empty,"+RESET+" enter again:");
            description = scan.nextLine();
        }
        System.out.println("Enter author name: ");
        String author = scan.nextLine();
        while (author.isBlank() || author.isEmpty()){
            System.out.println(RED + "The book needs to have an author! "+ RESET +"Enter name again:");
            author = scan.nextLine();
        }

        Book book = new Book(titleName, description, author);
        Library.getInstance().addBookToMap(book);
        FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");
    }

    public void librarianRemoveBookByTitle() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title to remove from library");
        String bookTitleToRemove = scan.nextLine();

        if (Library.getInstance().books.containsKey(bookTitleToRemove)) {
            Library.getInstance().books.remove(bookTitleToRemove);
            System.out.println(GREEN + "\n" + bookTitleToRemove.toUpperCase() + " removed from system" + RESET);
            FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");
        } else {
            System.out.println(RED + "\nNo book matches: '" + bookTitleToRemove + "'" + RESET);
        }
    }

    public void librarianRemoveBookByChoice(Book bookToRemove) {
        if (bookToRemove.isAvailable()) {
            System.out.println(GREEN + "\n" + bookToRemove.getTitle() + " by " + bookToRemove.getAuthor() + " removed from system" + RESET);
            Library.getInstance().books.remove(bookToRemove.getTitle());
            FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");

        } else {
            System.out.println(RED + "\n" + bookToRemove.getTitle() + " is loaned out and cannot be removed" + RESET + bookToRemove.showDaysRemainingOnLoan() + "\n");
        }
    }

    public void librarianAddUser() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scan.nextLine();
        while (!isNameValid(name)) {
            System.out.println(RED + "Enter name again,"+RESET+" this time correct: ");
            name = scan.nextLine();
        }
        System.out.println("Enter username: ");
        String username = scan.nextLine();
        while (!isUsernameValid(username)) {
            System.out.println(RED + "A username can't contain white space!\n"+ RESET +
                    "Please enter username again: ");
            username = scan.nextLine();
        }
        System.out.println("Enter password(minimum of 6 letters, including one number and one uppercase letter): ");
        String password = scan.nextLine();
        while (!isPasswordValid(password)) {
            System.out.println(RED + "Wrong, enter correct password format!\n" + RESET +
                    "(minimum of 6 letters, including one number and one uppercase letter):");
            password = scan.nextLine();
        }
        User user = new User(name, username, password);
        Library.getInstance().users.put(user.getName(), user);
        FileUtils.writeObject(Library.getInstance().users, "src/com/company/Files/User.ser");
    }

    public boolean isPasswordValid(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{6,20}$";
        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean isUsernameValid(String username) {
        String regex = "^(?=\\S+$).{2,20}$";
        Pattern p = Pattern.compile(regex);
        if (username == null) {
            return false;
        }
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public boolean isNameValid(String name) {
        String regex = "^[a-zåäöA-ZÅÄÖ\s]{3,40}" + "[^0-9]$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        for (Person person : Library.getInstance().getUsersAsList()) {
            if (name.equalsIgnoreCase(person.getName())) {
                return false;
            }
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public void librarianRemoveUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter username of user to remove: ");
        String userToRemove = scan.nextLine();
        if (Library.getInstance().users.containsKey(userToRemove)) {
            Library.getInstance().users.remove(userToRemove);
            FileUtils.writeObject(Library.getInstance().users, "src/com/company/Files/User.ser");
        } else {
            System.out.println(RED + "\nNo user matches: '" + userToRemove + "'" + RESET);
        }
    }

    public void seeAllUsers() {
        int i = 1;
        System.out.println();
        System.out.println(PURPLE + "---------------------------------------------------------------------------" + RESET);
        for (Person p : Library.getInstance().getUsersAsList()) {
            System.out.println(i + " " + YELLOW + p.getName() + CYAN + "\nUsername: " + RESET + p.getUsername() + CYAN + "\nPassword: " + RESET + p.getPassword() + CYAN +
                    "\nBorrowed books:" + RESET);

            for (Book book : ((User) p).getBooks()) {
                System.out.println("* " + YELLOW + book.getTitle() + RESET + book.showDaysRemainingOnLoan());
            }
            System.out.println(PURPLE + "---------------------------------------------------------------------------" + RESET);
            i++;
        }
    }

    public void seeAllBorrowedBooks() {
        System.out.println("\nThe following books are out on loan: ");
        for (Book book : Library.getInstance().getBooksAsList()) {
            if (!book.isAvailable()) {
                System.out.println("* " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor() + book.showDaysRemainingOnLoan());
            }
        }
    }

    public void searchForUserByName() {

        Scanner scan = new Scanner(System.in);
        System.out.println("\nType in a name: ");
        String regex = scan.nextLine();
        Pattern pa = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        System.out.println(YELLOW + "\n== NAMES OF USERS MATCHING: '" + regex + "' ==" + RESET);
        System.out.println(PURPLE + "---------------------------------------------------------------------------" + RESET);

        for (Person person : Library.getInstance().getUsersAsList()) {
            Matcher matcher = pa.matcher(person.getName());
            boolean matchFound = matcher.find();
            if (matchFound) {
                System.out.println(YELLOW + person.getName() + CYAN + "\nUsername: " + RESET + person.getUsername() + CYAN + "\nPassword: " + RESET + person.getPassword() + CYAN +
                        "\nBorrowed books:" + RESET);
                for (Book book : ((User) person).getBooks()) {
                    System.out.println("* " + YELLOW + book.getTitle() + RESET + book.showDaysRemainingOnLoan());
                }
                System.out.println(PURPLE + "---------------------------------------------------------------------------" + RESET);
            }
        }
    }
    public boolean isBookTitleValid(String title){
        for (Book book : Library.getInstance().getBooksAsList()) {
            if (book.getTitle().equalsIgnoreCase(title)){
                return false;
            }
        }
        return true;
    }
}


