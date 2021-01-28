package com.company.Helpers;

import com.company.Entities.Librarian;
import com.company.Entities.Person;
import com.company.Entities.User;

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


    public static void readFileLogIn() {

        Path path = Paths.get("C:\\project2\\Projekt-Avancerad-Java\\LibraryAppProject\\src\\com\\company\\Files\\LogIn.txt");

        try {
            List<String> ourFile = Files.readAllLines(path);

            for (String line : ourFile) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testPath() {
//        Path currentPath = Paths.get("");
        System.out.println("Test");
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
        String localDir = System.getProperty("user.dir");
        System.out.println(localDir);
        System.out.println(Paths.get(System.getProperty("user.dir") + "\\src\\com\\company\\Files\\LogIn.txt"));
    }

    public static List<Person> readFileLoginV2(){
        Path path = Paths.get(System.getProperty("user.dir") + "\\src\\com\\company\\Files\\LogIn.txt");
        List<Person> arrLogin = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);
            for(String line : lines){
                String[] tempArr = line.split(";");
                String username = tempArr[0];
                String password = tempArr[1];
                String adminOrUser = tempArr[2];
                if(adminOrUser.equalsIgnoreCase("admin")){
                    Librarian newLibrarian = new Librarian(username, username, password);
                    arrLogin.add(newLibrarian);
                } else {
                    User newUser = new User(username, username, password);
                    arrLogin.add(newUser);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return arrLogin;
    }


    public static void writeFileLogIn(String name, String username, String password) {
        List<String> newText = new ArrayList<>();
        newText.add(name + ";" + username + ";" + password);
        try {

            Path path = Paths.get("C:\\project2\\Projekt-Avancerad-Java\\LibraryAppProject\\src\\com\\company\\Files\\LogIn.txt");

            Files.write(path, newText, StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(String fileName) {

        FileInputStream streamIn = null;
        ObjectInputStream objectInputStream = null;

        Object object = null;

        try {
            streamIn = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(streamIn);
            object = objectInputStream.readObject();
            System.out.print(object);
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static void writeObject(Object object, String fileName) {

        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteObjectBook(Object object) {


    }
}
