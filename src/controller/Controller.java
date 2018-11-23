package controller;

import javafx.collections.ObservableList;
import model.Category;
import model.db.Database;
import model.db.DatabaseText;

import java.util.List;

public class Controller {

    private ObservableList<Category> categories;

    public Controller() {


    }

    public ObservableList<Category> loadData() {
      return categories;
    }

    public void addCategorys() {
        Database db = new DatabaseText();
        db.fillListQuestions();
        categories.addAll(db.getAll());
    }
}
