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
import model.MainCategory;
import model.Question;
import model.db.Database;
import model.db.DatabaseText;
import view.Observer;


public class CategoryOverviewPane extends GridPane implements Observer {
    private TableView table;
    private Button btnNew;
    private Controller service;
    ObservableList<Category> categories;


    public CategoryOverviewPane(Controller controller) {
        service = controller;

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        this.add(new Label("Categories:"), 0, 0, 1, 1);

        table = new TableView<>();
        table.setPrefWidth(REMAINING);

        categories = service.getCategories();
        table.setItems(categories);

        TableColumn nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        table.getColumns().add(nameCol);

        TableColumn descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        table.getColumns().add(descriptionCol);

        this.add(table, 0, 1, 2, 6);
		btnNew = new Button("New");
		this.add(btnNew, 0, 11, 1, 1);
	}

	public Category getCategory() {
        return (Category) table.getSelectionModel().getSelectedItem();
    }

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

    @Override
    public void update(ObservableList<Category> categories, ObservableList<Question> questions) {
        this.categories = categories;
    }
}
