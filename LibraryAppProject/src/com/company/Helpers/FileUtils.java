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

public class FileUtils {

    public void readFile(){
        //statiska, behöver inte skapa ett objekt av denna klass för att komma åt

            //hämtar path till textfilen
            Path path = Paths.get("Files/Books.txt");
            //try catch för att hantera exception.
            try {
                List<String> ourFile = Files.readAllLines(path); //strängar -> pga text -> readAllLines, läser varje rad var för sig
                //for-each loop. Loopar igenom listan, där varje rad omvandlas till en string
                for (String line : ourFile) {
                    System.out.println(line);
                }

            } catch (Exception e) { // för alla exceptions som finns
                e.printStackTrace(); //i detta fall för att se om där finns en fil att läsa ifrån.

            }
        }

    public void writeFile(){
        List<String>newText = new ArrayList<>();
        newText.add("");
        newText.add("Vår nya data...");
        newText.add("Manchester United");
        newText.add("Newcastle United");
        newText.add("Burnley"); //skapat ny lista och lagt till text i form av Strings

        try{
            Path path = Paths.get("Files/Books.txt");

            Files.write(path, newText, StandardOpenOption.APPEND);

        }catch(Exception e){
            e.printStackTrace();

        }

    }

    public static Object readObject(String fileName){

        FileInputStream streamIn = null; //För att läsa in fil
        ObjectInputStream objectInputStream = null; //För att läsa in objekt
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
