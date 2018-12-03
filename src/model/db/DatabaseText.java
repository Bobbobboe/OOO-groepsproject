package model.db;
import model.Category;
import model.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseText implements Database, Serializable {

    private List<Category> categories = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    public static final String CATEGORY_TXT = "src/model/db/Category.txt";
    public static final String QUESTION_TXT = "src/model/db/Question.txt";

    public DatabaseText() {
        fillListCategories();
        fillListQuestions();
    }

    @Override
    public void add(Category category) {
        categories.add(category);
        updateCategories();
    }

    @Override
    public void add(Question question) {
        questions.add(question);
        updateQuestions();
    }

    @Override
    public Category getCategory(int id) {
        return categories.get(id);
    }

    @Override
    public Question getQuestion(int id) {
        return questions.get(id);
    }


    @Override
    public void deleteCategory(int id) {
        Category category = categories.get(id);
        categories.remove(id);
        updateCategories();
        for (Question question : questions){
            if (category.equals(question.getCategory())){
                questions.remove(question);
            }
        }
        updateQuestions();
    }

    @Override
    public void deleteQuestion(int id) {
        questions.remove(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questions;
    }




    @SuppressWarnings("Duplicates")
    public void fillListCategories() {
        List<Category> c = null;

        try {
            FileInputStream fileIn = new FileInputStream(CATEGORY_TXT);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            c = (List<Category>) in.readObject();


            in.close();
            fileIn.close();
            } catch (Exception ignored) {

            }


        if(c != null) {
            categories = c;
        }

        else {
            System.out.println("No serialized object found in " + CATEGORY_TXT);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void fillListQuestions() {
        List<Question> q = null;

        try {
            FileInputStream fileIn = new FileInputStream(QUESTION_TXT);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            q = (List<Question>) in.readObject();


            in.close();
            fileIn.close();
        } catch (Exception ignored) {

        }


        if(q != null) {
            questions = q;
        }

        else {
            System.out.println("No serialized object found in " + QUESTION_TXT);
        }
    }

    @SuppressWarnings("Duplicates")
    private void updateCategories() {
        try {
            FileOutputStream fileOut = new FileOutputStream(CATEGORY_TXT);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(categories);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved to " + CATEGORY_TXT);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    private void updateQuestions() {
        try {
            FileOutputStream fileOut = new FileOutputStream(QUESTION_TXT);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(questions);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved to " + QUESTION_TXT);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
