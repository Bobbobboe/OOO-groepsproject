package controller;

<<<<<<< Updated upstream
public class Controller {

=======
<<<<<<< HEAD
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
=======
public class Controller {

>>>>>>> master
>>>>>>> Stashed changes
}
