package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;
import java.util.regex.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Librarian extends Person implements Serializable {

    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }

    public Librarian() {

    }


    public void librarianAddBook(Library mainLibrary) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String titleName = scan.nextLine();
        System.out.println("Enter a short description: ");
        String description = scan.nextLine();
        System.out.println("Enter author name: ");
        String author = scan.nextLine();
        Book book = new Book(titleName, description, author);
        mainLibrary.getBookList().add(book);
        FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
    }

    public void librarianRemoveBookByTitle(Library mainLibrary) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter book title to remove from library");
        String bookTitleToRemove = scan.nextLine();
        if (mainLibrary.bookList.removeIf(book -> book.getTitle().equalsIgnoreCase(bookTitleToRemove))) {
            //FileUtils.deleteObjectBook(bookTitleToRemove);
            FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
        }

    }

    public void librarianRemoveBookByChoice(Book bookToRemove, Library mainLibrary) {
        if (mainLibrary.bookList.removeIf(book -> book.equals(bookToRemove))) {
            FileUtils.writeObject(mainLibrary.bookList, "src/com/company/Files/Books.ser");
        }
    }

    public void librarianAddUser(Library mainLibrary) {

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
        mainLibrary.userList.add(user);
        FileUtils.writeObject(mainLibrary.userList, "src/com/company/Files/User.ser");


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

    public void librarianRemoveUser(Library mainLibrary) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter username of user to remove: ");
        String userToRemove = scan.nextLine();
        mainLibrary.userList.removeIf(person -> userToRemove.equalsIgnoreCase(person.getUsername()));
        FileUtils.writeObject(mainLibrary.userList, "src/com/company/Files/User.ser");
    }

    public void seeAllUsers(Library mainLibrary) {
        System.out.println();
        for (Person p : mainLibrary.userList) {
            if (p instanceof User) {
                System.out.println("Name of user: " + p.getName() +
                        "\nBorrowed books:");
                for (Book book : ((User) p).getBooks()) {
                    System.out.println(book.getTitle() + " (" + book.showDaysRemainingOnLoan() + ")");
                }
                System.out.println();
            }
        }
    }

    public static void seeAllBorrowedBooks(){
        System.out.println("The following books are out on loan: ");
        for (Book book: Library.bookList) {
            if(!book.available){
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

}

