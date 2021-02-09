package com.company.Entities;

import java.io.Serializable;
import java.time.LocalDate;

import static com.company.Helpers.Color.*;

public class Book implements Serializable {

    private String title;
    private String description;
    private String author;
    private int i;
    private boolean available = true;
    private LocalDate returnBookDate;
    private static final long serialVersionUID = 3L;


    public Book() {
    }

    public Book(String title, String description, String author) {
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
        if (!available) {
            setReturnBookDate();
        }
    }

    public String getLoanStatus() {
        if (available) {
            return GREEN + "Available" + RESET;
        } else {
            return RED + "Not available" + RESET;
        }
    }

    private void setReturnBookDate() {

        this.returnBookDate = LocalDate.now().plusDays(14);
    }

    public LocalDate getReturnBookDate() {

        return returnBookDate;
    }

    public String showDaysRemainingOnLoan() {

        if (LocalDate.now().until(returnBookDate).getDays() == 0) {
            return " [" + CYAN + LocalDate.now().until(returnBookDate).getDays() + " days left on the loan!" + RESET + "]";

        } else if (LocalDate.now().until(returnBookDate).getDays() < 0) {
            int days = LocalDate.now().until(returnBookDate).getDays();
            days *= days;
            days = (int) Math.sqrt(days);

            return " [" + RED + "THE BOOK IS OVERDUE BY " + days + " DAY(S)!" + RESET + "]";
        }

        return " [" + GREEN + LocalDate.now().until(returnBookDate).getDays() + RESET + " days left on the loan]";
    }

    public void showBookInfo() {
        System.out.println(YELLOW + "\n== " + title.toUpperCase() + " ==\n" + CYAN + "Written by: " + RESET + author + CYAN + "\nDescription: " + RESET + description);
        System.out.println("[" + getLoanStatus() + "]");
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
