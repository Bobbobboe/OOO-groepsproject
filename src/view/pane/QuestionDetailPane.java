package view.pane;

import controller.Controller;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Category;

import java.util.ArrayList;
import java.util.List;

public class QuestionDetailPane extends GridPane {
	private Button btnOK, btnCancel;
	private TextArea statementsArea;
	private TextField questionField, statementField, feedbackField;
	private Button btnAdd, btnRemove;
	private ComboBox categoryField;
	private List<String> statements;
	private ObservableList<String> observablestatements;

	public QuestionDetailPane(ObservableList<Category> categories) {
		this.statements = new ArrayList<>();
		this.observablestatements = FXCollections.observableArrayList(statements);

		this.setPrefHeight(300);
		this.setPrefWidth(320);

		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

		add(new Label("Question: "), 0, 0, 1, 1);
		questionField = new TextField();
		add(questionField, 1, 0, 2, 1);

		add(new Label("Statement: "), 0, 1, 1, 1);
		statementField = new TextField();
		add(statementField, 1, 1, 2, 1);

		add(new Label("Statements: "), 0, 2, 1, 1);
		statementsArea = new TextArea();

		addStatementsToTextArea();

		statementsArea.setPrefRowCount(5);
		statementsArea.setEditable(false);
		add(statementsArea, 1, 2, 2, 5);

		Pane addRemove = new HBox();
		btnAdd = new Button("add");
		btnAdd.setOnAction(new AddStatementListener());
		addRemove.getChildren().add(btnAdd);

		btnRemove = new Button("remove");
		btnRemove.setOnAction(new RemoveStatementListener());
		addRemove.getChildren().add(btnRemove);
		add(addRemove, 1, 8, 2, 1);

		add(new Label("Category: "), 0, 9, 1, 1);
		categoryField = new ComboBox(categories);
		add(categoryField, 1, 9, 2, 1);

		add(new Label("Feedback: "), 0, 10, 1, 1);
		feedbackField = new TextField();
		add(feedbackField, 1, 10, 2, 1);

		btnCancel = new Button("Cancel");
		btnCancel.setText("Cancel");
		add(btnCancel, 0, 11, 1, 1);

		btnOK = new Button("Save");
		btnOK.isDefaultButton();
		btnOK.setText("Save");
		btnOK.disableProperty().bind(Bindings.isEmpty(this.observablestatements));

		add(btnOK, 1, 11, 2, 1);

	}

	public void setStatements(List<String> statements) {
		this.statements = statements;
		this.observablestatements.addAll(statements);
		addStatementsToTextArea();
	}

	public void setQuestion(String question) {
		this.questionField.setText(question);
	}

	public void setFeedback(String feedback) {
		this.feedbackField.setText(feedback);
	}

	public void setCategory(Category category) {
		this.categoryField.getSelectionModel().select(category);
	}

	private void addStatementsToTextArea() {
		for(String s : statements) {
			this.statementsArea.appendText(s + "\n");
		}
	}

	private void addStatementToTextArea(String statement) {
		this.statementsArea.appendText(statement + "\n");
	}

	public TextArea getStatementsArea() {
		return statementsArea;
	}

	public TextField getQuestionField() {
		return questionField;
	}

	public TextField getStatementField() {
		return statementField;
	}

	public TextField getFeedbackField() {
		return feedbackField;
	}

	public ComboBox getCategoryField() {
		return categoryField;
	}

	public List<String> getStatements() {
		return statements;
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

	class AddStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			statements.add(getStatementField().getText());
			observablestatements.add(getStatementField().getText());
			addStatementToTextArea(statementField.getText());
			statementField.clear();
		}
	}

	class RemoveStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			statements.clear();
			statementsArea.setText("");
		}
	}
}
