package com.company.Helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileUtils {



    public static Object readObject(String fileName) {

        FileInputStream streamIn = null;
        ObjectInputStream objectInputStream = null;

        Object object = null;

        try {
            streamIn = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(streamIn);
            object = objectInputStream.readObject();
            //System.out.print(object);
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

}
