package model.db;
import model.Category;
import model.CategoryFactory;
import model.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class DatabaseText implements Database {

    private List<Category> categories = new ArrayList<>();

    public DatabaseText() {
        fillListQuestions();
    }

    @Override
    public void add(Category catergory) {
        categories.add(catergory);
        updateDb();
    }

    @Override
    public Category get(int id) {
        return categories.get(id);
    }

    @Override
    public void delete(int id) {
        categories.remove(get(id));
        updateDb();
    }

    @Override
    public List<Category> getAll() {
        return categories;
    }

    public void fillListQuestions() {
        List<Category> c = null;

        try {
            FileInputStream fileIn = new FileInputStream("src/model/db/Categorie.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            c = (List<Category>) in.readObject();


            in.close();
            fileIn.close();
            } catch (Exception b) {

            }


        if(c != null) {
            categories = c;
        }

        else {
            System.out.println("No serialized object found in Categorie.txt");
        }
    }

    private void updateDb() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Categorie.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(categories);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved to categorie.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
