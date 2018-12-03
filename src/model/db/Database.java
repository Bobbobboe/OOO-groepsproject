package model.db;

import model.Category;
import model.Question;

import java.util.List;

public interface Database {

    void add(Category category);
    void add(Question question);

    Category getCategory(int id);
    Question getQuestion(int id);

    void deleteCategory(int id);
    void deleteQuestion(int id);

    List<Category> getAllCategories();
    List<Question> getAllQuestions();

    void fillListCategories();
    void fillListQuestions();

    void updateCategories();
    void updateQuestions();
}
