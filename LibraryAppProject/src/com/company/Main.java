package com.company;


import com.company.Entities.Book;
import com.company.Helpers.FileUtils;

public class Main {
    public static void main(String[] args) {

        Library library = Library.getInstance();

        //<editor-fold desc="Skräp">
        /*Book b1 = new Book("Sagan om ringen", "Frodo walking and having fun with his friends", "J.R.R. Tolkien");
        Book b2 = new Book("Sagan om de två tornen", "Frodo having some more fun with his friends", "J.R.R. Tolkien");
        Book b3 = new Book("Sagan om konungens återkomst", "Frodo having even more fun with his friends", "J.R.R. Tolkien");
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

        library.books.put(b1.getTitle(),b1);
        library.books.put(b2.getTitle(),b2);
        library.books.put(b3.getTitle(),b3);
        library.books.put(b4.getTitle(),b4);
        library.books.put(b5.getTitle(),b5);
        library.books.put(b6.getTitle(),b6);
        library.books.put(b7.getTitle(),b7);
        library.books.put(b8.getTitle(),b8);
        library.books.put(b9.getTitle(),b9);
        library.books.put(b10.getTitle(),b10);
        library.books.put(b11.getTitle(),b11);
        library.books.put(b12.getTitle(),b12);
        library.books.put(b13.getTitle(),b13);

        FileUtils.addUsers();
        FileUtils.addBooks();

        FileUtils.writeObject(Library.getInstance().books, "src/com/company/Files/Books.ser");
        FileUtils.writeObject(Library.getInstance().users, "src/com/company/Files/User.ser");*/
        //</editor-fold>


        library.openLibrary();


    }

}
