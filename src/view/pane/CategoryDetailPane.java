package view.pane;

import com.sun.istack.internal.NotNull;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Category;

public class CategoryDetailPane extends GridPane {
	private Button btnOK, btnCancel;
	private TextField titleField, descriptionField;
	private ComboBox categoryField;

	public CategoryDetailPane(ObservableList<Category> categories) {
		this.setPrefHeight(150);
		this.setPrefWidth(300);
		
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setVgap(5);
		this.setHgap(5);

		this.add(new Label("Title:"), 0, 0, 1, 1);
		titleField = new TextField();
		this.add(titleField, 1, 0, 1, 1);

		this.add(new Label("Description:"), 0, 1, 1, 1);
		descriptionField = new TextField();
		this.add(descriptionField, 1, 1, 1, 1);

		this.add(new Label("Main Category:"), 0, 2, 1, 1);
		categoryField = new ComboBox<>(categories);
		this.add(categoryField, 1, 2, 1, 1);

		btnCancel = new Button("Cancel");
		this.add(btnCancel, 0, 3, 1, 1);

		btnOK = new Button("Save");
		btnOK.isDefaultButton();
		btnOK.disableProperty().bind(Bindings.and(titleField.textProperty().isEmpty(), descriptionField.textProperty().isEmpty()));
		
		this.add(btnOK, 1, 3, 1, 1);
	}

	public void setTitle(String title) {
		this.titleField.setText(title);
	}

	public void setDescription(String description) {
		this.descriptionField.setText(description);
	}

	public void setCategory(Category category) {
		categoryField.getSelectionModel().select(category);
	}

	public TextField getTitleField() {
		return titleField;
	}

	public TextField getDescriptionField() {
		return descriptionField;
	}

	public ComboBox getCategoryField() {
		return categoryField;
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

}
