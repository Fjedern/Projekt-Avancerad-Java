package com.company.Entities;

import com.company.Helpers.FileUtils;
import com.company.Library;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

public class User extends Person implements Serializable{
    List<Book> books = new ArrayList<>();

    public User(String name, String username, String password) {
        super(name, username, password);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "User{" +
                "books=" + books +
                '}';
    }

    public void borrowBook(){
        //books.add(book);
        //book.setAvailable(false);

        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        library.showAllBooks();
        System.out.println("\nWhich book would you like to borrow? (Use title)");
        String title = scanner.nextLine();

        for(Book book: library.getBookList()){
            if(book.getTitle().equalsIgnoreCase(title)){
                System.out.print(book.getTitle() + title);
                if(book.isAvailable()){

                    Library.bookList.remove(book);

                    book.setAvailable(false);

                    Library.bookList.add(book);


                    FileUtils.writeObject(Library.bookList, "src/com/company/Files/Books.ser");
                    System.out.print(FileUtils.readObject("src/com/company/Files/Books.ser"));
                    String hej = scanner.nextLine();
                }


            }

        }



    }


    public void showUserBooks(){
        int i = 1;
        books.sort(Comparator.comparing(Book::getTitle));
        for(Book book : books){
            book.setI(i);
            System.out.println("[" + i + "] " + book.getTitle().toUpperCase() + " (" + book.showDaysRemainingOnLoan() + ") ");
            i++;
        }
    }

    public void returnBook(String titleOrAuthor){
        for(Book book : books){
            if(titleOrAuthor.equalsIgnoreCase(book.getAuthor()) || titleOrAuthor.equalsIgnoreCase(book.title)){
                books.remove(book);
                return;
            }
        }
    }
}
