package com.company.Helpers;

import com.company.Entities.Book;
import com.company.Entities.Librarian;
import com.company.Entities.User;
import com.company.Library;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileUtils {

    public static Object readObject(String fileName) {

        FileInputStream streamIn = null;
        ObjectInputStream objectInputStream = null;

        Object object = null;

        try {
            streamIn = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(streamIn);
            object = objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            return object;
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

    //<editor-fold desc="Backup">
    public static List<String> readFromFile(String path) {
        List<String> test = new ArrayList<>();
        try {
            test = Files.lines(Paths.get(path)).parallel()
                    .flatMap(s -> Stream.of(s.split("@")))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return test;
    }

    public static void addUsers() {
        List<String> test = readFromFile("src/com/company/Files/mockUsers.txt");
        boolean admin = true;

        for (int i = 0; i < test.size() - 1; i += 3) {

            if (admin) {
                Library.getInstance().users.put(test.get(i + 1), new Librarian(test.get(i), test.get(i + 1), test.get(i + 2)));
                admin = false;

            } else {
                Library.getInstance().users.put(test.get(i + 1), new User(test.get(i), test.get(i + 1), test.get(i + 2)));
            }
        }
    }

    public static void addBooks() {
        List<String> test = readFromFile("src/com/company/Files/mockBooks.txt");

        for (int i = 0; i < test.size() - 1; i += 3) {
            Library.getInstance().books.put(test.get(i), new Book(test.get(i), test.get(i + 2), test.get(i + 1)));
        }

    }
    //</editor-fold>
}
