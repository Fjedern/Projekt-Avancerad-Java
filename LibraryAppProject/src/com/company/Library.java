package com.company;

import com.company.Entities.Book;
import com.company.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class Library {
    public Library() { }

    public static List<Book> bookList = new ArrayList<>();
    public static List <User> userlist = new ArrayList<>();

    public static void addBook(Book book){
        bookList.add(book);
    }
    public static void removeBook(String bookTitleToRemove){
        bookList.removeIf(book -> book.getTitle().equalsIgnoreCase(bookTitleToRemove));
    }

}
