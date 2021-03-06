package view.pane;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Category;
import model.Question;
import model.db.Database;
import model.db.DatabaseText;
import view.Observer;

public class QuestionOverviewPane extends GridPane implements Observer {
    private TableView table;
    private Button btnNew;
    private Controller service;
    ObservableList<Question>questions;

    public QuestionOverviewPane(Controller controller) {
        service = controller;
        service.addObserver(this);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        //ObservableList<Question> questions = FXCollections.observableArrayList(db.getAllQuestions());
        questions = service.getQuestions();
        this.add(new Label("Questions:"), 0, 0, 1, 1);

        table = new TableView<>();
        table.setItems(questions);
        table.setPrefWidth(REMAINING);
        TableColumn nameCol = new TableColumn<>("Question");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        table.getColumns().add(nameCol);
        TableColumn descriptionCol = new TableColumn<>("Category");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("category"));
        table.getColumns().add(descriptionCol);
        this.add(table, 0, 1, 2, 6);

        btnNew = new Button("New");
        this.add(btnNew, 0, 11, 1, 1);
    }

    public Question getSelectedQuestion() {
        return (Question) table.getSelectionModel().getSelectedItem();
    }

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

    @Override
    public void update(ObservableList<Category> categories, ObservableList<Question> questions) {
        this.questions = questions;
    }
}
