package com.company;


import com.company.Entities.Book;
import com.company.Entities.Librarian;
import com.company.Entities.User;

public class Main {
    public static void main(String[] args) {

        Library library = Library.getInstance();

        Book b1 = new Book("Sagan om ringen", "Frodo walking and having fun with his friends", "J.R.R. Tolkien");
        Book b2 = new Book("Sagan om de två tornen", "Frodo having some more fun with his friends", "J.R.R. Tolkien");
        Book b3 = new Book("Sagan om de konungens återkomst", "Frodo having even more fun with his friends", "J.R.R. Tolkien");
        Book b4 = new Book("Papillion", "Papillion having fun on his own", "Henri Charriere");
        Book b5 = new Book("Fakta om Finland", "Interesting fact about Finland", "Erland Loe");
        Book b6 = new Book("Oryx and Crake", "A story about life in the future", "Margaret Atwood");
        Book b7 = new Book("1984", "A story about life in 1984.", "George Orwell");
        Book b8 = new Book("Junky", "A story about the life of a junky", "William S. Burroughs");
        Book b9 = new Book("Brave New World", "A story about life in the future", "Aldous Huxley");
        Book b10 = new Book("Grapes of Wrath", "A story about life in the 1930s", "John Steinbeck");
        Book b11 = new Book("Människohamn", "A story about a coasttown", "John Ajvide Lindqvist");
        Book b12 = new Book("Ek", "Spooky story about an old ship", "Frida Andersson Johansson");
        Book b13 = new Book("Dränkt", "Spooky story about a guy playing the violin", "Frida Andersson Johansson");

        library.addBookToList(b1);
        library.addBookToList(b2);
        library.addBookToList(b3);
        library.addBookToList(b4);
        library.addBookToList(b5);
        library.addBookToList(b6);
        library.addBookToList(b7);
        library.addBookToList(b8);
        library.addBookToList(b9);
        library.addBookToList(b10);
        library.addBookToList(b11);
        library.addBookToList(b12);
        library.addBookToList(b13);

        User user1 = new User("Oskar Andersson", "o-dog", "password123");
        User user2 = new User("David Nilsson", "d-dog", "123456");
        User user3 = new User("Ludvig Anderbeck", "l-dog", "picture1");
        Librarian librarian = new Librarian("Eli Svensson", "admin", "admin");

        library.addUserToList(user1);
        library.addUserToList(user2);
        library.addUserToList(user3);
        library.getUserList().add(librarian);

        user1.borrowBook(b1);
        user1.borrowBook(b2);
        user2.borrowBook(b3);
        user2.borrowBook(b4);
        user3.borrowBook(b5);
        user3.borrowBook(b6);


        library.openLibrary();
    }

}
