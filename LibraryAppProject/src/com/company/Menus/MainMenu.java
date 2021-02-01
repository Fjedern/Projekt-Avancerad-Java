package com.company.Menus;

public enum MainMenu implements HasDescription {
    SHOW_ALL_BOOKS("Show all books"),
    SEARCH_BOOK_BY_NAME("Search book by title"),
    SEARCH_BOOK_BY_AUTHOR("Search book by author"),
    LOGIN("Login"),
    QUIT("Quit");

    private String description;



    MainMenu(String description){

        this.description = description;
    }


    public String getDescription() {
        return description;
    }


}
