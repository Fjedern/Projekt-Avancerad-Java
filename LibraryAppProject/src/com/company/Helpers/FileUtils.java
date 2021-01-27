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
        

            
            Path path = Paths.get("Files/Books.txt");
            
            try {
                List<String> ourFile = Files.readAllLines(path); 
                
                for (String line : ourFile) {
                    System.out.println(line);
                }

            } catch (Exception e) { 
                e.printStackTrace(); 

            }
        }

    public void writeFile(){
        List<String>newText = new ArrayList<>();
         

        try{
            Path path = Paths.get("Files/Books.txt");

            Files.write(path, newText, StandardOpenOption.APPEND);

        }catch(Exception e){
            e.printStackTrace();

        }

    }

    public static Object readObject(String fileName){

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
