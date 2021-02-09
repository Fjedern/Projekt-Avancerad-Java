package com.company.Menus;

public enum UserMenu implements GetMenuValues {
    SHOW_ALL_BOOKS("Show all books"),
    SHOW_AVAILABLE_BOOKS("Show available books"),
    SEARCH_BOOK_BY_NAME("Search book by title"),
    SEARCH_BOOK_BY_AUTHOR("Search book by author"),
    BORROW_BOOK("Borrow book"),
    SHOW_MY_BOOKS("My loans"),
    RETURN_BOOK("Return book"),

    QUIT("Logout");

    private final String description;


    UserMenu(String description){

        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public String getHeader() {
        return "\n== USER MENU ==";
    }


}
