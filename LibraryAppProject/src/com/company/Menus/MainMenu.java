package com.company.Menus;

public enum MainMenu implements GetMenuValues {
    SHOW_ALL_BOOKS("Show all books"),
    SHOW_AVAILABLE_BOOKS("Show available books"),
    SEARCH_BOOK_BY_NAME("Search book by title"),
    SEARCH_BOOK_BY_AUTHOR("Search book by author"),
    LOGIN("Login"),
    QUIT("Quit");

    private final String description;


    MainMenu(String description){

        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public String getHeader() {
        return "\n== MAIN MENU ==";
    }


}
