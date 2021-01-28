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


    public static Object readFilePerson(String fileName, Object object){

            Path path = Paths.get(fileName);

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


    public static void writeFilePerson(String fileName, Object object){



        try{
            Path path = Paths.get(fileName);

            Files.write(path, (byte[]) object, StandardOpenOption.APPEND);

        }catch(Exception e){
            e.printStackTrace();

        }

    }





    public Object readObject(String fileName){

        FileInputStream streamIn = null;
        ObjectInputStream objectInputStream = null;


        Object object = null;

        try{
            streamIn = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(streamIn);
            object = objectInputStream.readObject();
            objectInputStream.close();


        }catch(Exception e){
            e.printStackTrace();

        }
        return object;
    }

    public static void writeObject(Object object, String fileName){

        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;

        try{

            fileOutputStream = new FileOutputStream(fileName, true);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();

        }


    }
}
