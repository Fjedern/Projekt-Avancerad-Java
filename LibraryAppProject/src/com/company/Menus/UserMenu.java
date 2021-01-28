package com.company.Menus;

public enum UserMenu implements HasDescription {
    SHOW_ALL_BOOKS("Show all books"),
    SEARCH_BOOK_BY_NAME("Search book by title"),
    SEARCH_BOOK_BY_AUTHOR("Search book by author"),
    BORROW_BOOK("Borrow book"),
    SHOW_MY_BOOKS("Show my books"),
    QUIT("Quit"),

    ;

    private String description;



    UserMenu(String description){

        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}