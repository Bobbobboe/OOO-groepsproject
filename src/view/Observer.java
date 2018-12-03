package view;

import javafx.collections.ObservableList;
import model.Category;
import model.Question;

public interface Observer {
    void update(ObservableList<Category> categories, ObservableList<Question> questions);
}
