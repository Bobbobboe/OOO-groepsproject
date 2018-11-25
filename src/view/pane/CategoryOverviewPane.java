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
import model.db.Database;
import model.db.DatabaseText;


public class CategoryOverviewPane extends GridPane {
    private TableView table;
    private Button btnNew;

    public CategoryOverviewPane() {
        Controller service = new Controller();

//        db.add(new MainCategory("Design Principles", "The SOLID design principles"));
//        db.add(new MainCategory("Design pattersn", "Design patterns learned this year"));
//        db.add(new MainCategory("Java", "Java extra's"));
//        db.add(new MainCategory("UML", "Technique for drawing class diagrams"));

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        this.add(new Label("Categories:"), 0, 0, 1, 1);

        table = new TableView<>();
        table.setPrefWidth(REMAINING);
        //ObservableList<Category> categories = FXCollections.observableArrayList(db.getAllCategories());
        ObservableList<Category> categories = service.getCategories();
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

    public void setNewAction(EventHandler<ActionEvent> newAction) {
        btnNew.setOnAction(newAction);
    }

    public void setEditAction(EventHandler<MouseEvent> editAction) {
        table.setOnMouseClicked(editAction);
    }

}
