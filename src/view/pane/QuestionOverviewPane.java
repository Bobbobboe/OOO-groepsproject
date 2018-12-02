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

public class QuestionOverviewPane extends GridPane {
    private TableView table;
    private Button btnNew;

    public QuestionOverviewPane() {
        Controller service = new Controller();

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        //ObservableList<Question> questions = FXCollections.observableArrayList(db.getAllQuestions());
        ObservableList<Question>questions = service.getQuestions();
        System.out.println(questions);
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

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

}
