package view.pane;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Category;
import model.Question;
import view.Observer;

import java.util.List;

public class MessagePane extends GridPane implements Observer {
	private Button testButton;
	private Controller service;
	private List<Category> categories;
	private List<Question> questions;
	private TextArea scoreArea;

	public MessagePane (Controller service) {
		this.service = service;
		service.addObserver(this);

		this.categories = service.getCategories();
		this.questions = service.getQuestions();

	    setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        scoreArea = new TextArea();
		scoreArea.setPrefRowCount(5);
		scoreArea.setEditable(false);


		add(scoreArea, 0, 0, 1, 5);

		testButton = new Button("Evaluate");
		testButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(service.getTest().testIsFinished()) {
					service.showPopupWindow();
				}
				else {
					service.showTestPane();
				}
			}
		});

		add(testButton, 0,6,1,1);
		setHalignment(testButton, HPos.CENTER);
	}


	public void writeEvaluation(boolean done) {
		if(!done) this.scoreArea.setText("\n\n\t\t\t\t\tYou never did this evaluation");
	}

	@Override
	public void update(ObservableList<Category> categories, ObservableList<Question> questions) {
		this.categories = categories;
		this.questions = questions;
		this.scoreArea.setText(service.getMessage());
	}
}
