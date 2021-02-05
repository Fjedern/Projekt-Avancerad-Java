package com.company.Menus;

public enum AdminMenu implements HasDescription {
    SHOW_ALL_BOOKS("Show all books"),
    SHOW_ALL_BORROWED_BOOKS("Show all borrowed books"),
    SEARCH_BOOK_BY_NAME("Search book by title"),
    SEARCH_BOOK_BY_AUTHOR("Search book by author"),
    ALL_USERS("All users"),
    ADD_USER("Add user"),
    REMOVE_USER("Remove user"),
    ADD_BOOK("Add book"),
    REMOVE_BOOK("Remove book"),
    SEARCH_FOR_USER_BY_NAME("Search for a user by name"),
    QUIT("Logout");

    private String description;



    AdminMenu(String description){

        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
