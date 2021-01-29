package com.company.Entities;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void borrowBook(Book book){
        books.add(book);
    }

    public void showUserBooks(){
        for(Book book : books){
            System.out.println(books);
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
