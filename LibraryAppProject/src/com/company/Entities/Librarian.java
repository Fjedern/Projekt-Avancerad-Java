package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;
import static com.company.Helpers.Color.*;

import java.io.Serializable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Librarian extends Person implements Serializable {


    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }

    public Librarian() {

    }


    public void librarianAddBook() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String titleName = scan.nextLine();
        System.out.println("Enter a short description: ");
        String description = scan.nextLine();
        System.out.println("Enter author name: ");
        String author = scan.nextLine();

        Book book = new Book(titleName, description, author);
        Library.getInstance().getBookList().add(book);
        FileUtils.writeObject(Library.getInstance().bookList, "src/com/company/Files/Books.ser");
    }

    public void librarianRemoveBookByTitle() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title to remove from library");
        String bookTitleToRemove = scan.nextLine();
        if (Library.getInstance().bookList.removeIf(book -> book.getTitle().equalsIgnoreCase(bookTitleToRemove))) {
            //FileUtils.deleteObjectBook(bookTitleToRemove);
            FileUtils.writeObject(Library.getInstance().bookList, "src/com/company/Files/Books.ser");
        }

    }

    public void librarianRemoveBookByChoice(Book bookToRemove) {
        if (Library.getInstance().bookList.removeIf(book -> book.equals(bookToRemove))) {
            FileUtils.writeObject(Library.getInstance().bookList, "src/com/company/Files/Books.ser");
        }
    }

    public void librarianAddUser() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scan.nextLine();
        while(isNameValid(name) != true){
            System.out.println("Enter name again, this time correct: ");
            name = scan.nextLine();
        }
        System.out.println("Enter username: ");
        String username = scan.nextLine();
        while (isUsernameValid(username)!=true){
            System.out.println("A username can't contain white space!\n"+
                    "Please enter username again: ");
            username = scan.nextLine();
        }
        System.out.println("Enter password(minimum of 6 letters, including one number and one uppercase letter): ");
        String password = scan.nextLine();
        while (isPasswordValid(password)!=true){
            System.out.println("Wrong, enter correct password format!\n"+
                    "(minimum of 6 letters, including one number and one uppercase letter):");
            password = scan.nextLine();
        }
        User user = new User(name, username, password);
        Library.getInstance().userList.add(user);
        FileUtils.writeObject(Library.getInstance().userList, "src/com/company/Files/User.ser");


    }

    public boolean isPasswordValid(String password){
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

    public boolean isUsernameValid(String username){
        String regex = "^(?=\\S+$).{2,20}$";
        Pattern p = Pattern.compile(regex);
        if (username == null) {
            return false;
        }
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public boolean isNameValid(String name){
        String regex = "^[a-zåäöA-ZÅÄÖ\s]{3,40}"+"[^0-9]$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public void librarianRemoveUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username of user to remove: ");
        String userToRemove = scan.nextLine();
        Library.getInstance().userList.removeIf(person -> userToRemove.equalsIgnoreCase(person.getUsername()));
        FileUtils.writeObject(Library.getInstance().userList, "src/com/company/Files/User.ser");
    }

    public void seeAllUsers() {
        System.out.println();
        for (Person p : Library.getInstance().userList) {
            if (p instanceof User) {
                System.out.println("Name of user: " + p.getName() +
                        "\nBorrowed books:");
                for (Book book : ((User) p).getBooks()) {
                    System.out.println(book.getTitle() + book.showDaysRemainingOnLoan());
                }
                System.out.println();
            }
        }
    }

    public void seeAllBorrowedBooks(){
        System.out.println("The following books are out on loan: ");
        for (Book book: Library.getInstance().bookList) {
            if(!book.available){
                System.out.println("* " + YELLOW + book.getTitle() + RESET + " by " + book.getAuthor() + RED + book.showDaysRemainingOnLoan() + RESET);
            }
        }
    }



}

