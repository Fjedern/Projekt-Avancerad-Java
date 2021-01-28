package com.company.Helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

//FileUtils.writeObject(books, "Books.ser");
//List<Book> books = (List<Book>) fileUtils.readObject("Books.ser");

        /*List<Book>books = new ArrayList<>();
        books.add(new Book("hej", "tjena", "hallå"));
        FileUtils.writeObject(books, "Book.ser");
        FileUtils fileUtils = new FileUtils();
        System.out.print(fileUtils.readObject("Book.ser"));*/

public class FileUtils {


    public static Object readFileLogIn(Object object){

            Path path = Paths.get("../Files/LogIn.txt");

            try {
                List<String> ourFile = Files.readAllLines(path);

                for (String line : ourFile) {
                    System.out.println(line);
                }
            } catch (Exception e) { 
                e.printStackTrace();
            }
            return object;
        }


    public static void writeFileLogIn(Object object){
        try{
            Path path = Paths.get("../Files/LogIn.txt");

            Files.write(path, (byte[]) object, StandardOpenOption.APPEND);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Object readObjectBook(){

        FileInputStream streamIn = null;
        ObjectInputStream objectInputStream = null;

        Object object = null;

        try{
            streamIn = new FileInputStream("C:\\project2\\Projekt-Avancerad-Java\\LibraryAppProject\\src\\com\\company\\Files\\Books.ser");
            objectInputStream = new ObjectInputStream(streamIn);
            object = objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return object;
    }

    public static void writeObjectBook(Object object){

        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            fileOutputStream = new FileOutputStream("C:\\project2\\Projekt-Avancerad-Java\\LibraryAppProject\\src\\com\\company\\Files\\Books.ser", true);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteObjectBook(Object object){


    }
}
