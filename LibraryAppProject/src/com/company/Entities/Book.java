package com.company.Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    String title;
    String description;
    String author;
    private int i;
    boolean available = true;
    long timeStamp;
    private LocalDate returnBookDate;

    public Book() {
    }

    public Book(String title, String description, String author, Boolean available) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }



    public void setAvailable(boolean setAvailable) {
        available = setAvailable;
        setReturnBookDate();
    }
    private void setReturnBookDate() {
        this.returnBookDate = LocalDate.now().plusDays(14);
    }

    public LocalDate getReturnBookDate() {
        return returnBookDate;
    }

    public String showDaysRemainingOnLoan() {
        return LocalDate.now().until(returnBookDate).getDays() + " days left on the loan";

    }




    public void showBookInfo() {
        System.out.println("\n== " + title.toUpperCase() + " ==\nWritten by: " + author + "\nDescription: " + description);
        if (available) {
            System.out.println("* Available");
        }
        else {
            System.out.println("* Not available");
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
