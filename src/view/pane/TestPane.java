package view.pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.Question;
import view.Observer;

public class TestPane extends GridPane {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private Controller service;
	private int random;


	public TestPane (Question question) {
		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

		questionField = new Label();
		add(questionField, 0, 0, 1, 1);
		
		statementGroup = new ToggleGroup();
		statementGroup.setUserData("statements");



		this.add(new Label(question.toString()),0, 1, 1,1);

		int rowIndex = 3;
		List<String> statemtents = question.getStatments();
		List<String> shuffeled = new ArrayList<>(statemtents);
		Collections.shuffle(shuffeled);

		for (String statement : shuffeled) {
			RadioButton rb = new RadioButton(statement);
			rb.setUserData(statement);
			rb.setToggleGroup(statementGroup);
			this.add(rb, 0, rowIndex, 1, 1);
			rowIndex++;
		}


		submitButton = new Button("Submit");
		add(submitButton,0, 58, 1, 1);

	}

	public void setProcessAnswerAction(EventHandler<ActionEvent> processAnswerAction) {
		submitButton.setOnAction(processAnswerAction);
	}

	public String getSelectedStatements() {
		String selected = "";
		if(this.statementGroup.getSelectedToggle() != null) {
			selected = this.statementGroup.getSelectedToggle().getUserData().toString();
		}
		return selected;
	}
}
