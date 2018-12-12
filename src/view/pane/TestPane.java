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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.Category;
import model.Question;
import view.Observer;

public class TestPane extends GridPane implements Observer {
	private Label questionField;
	private Button submitButton;
	private ToggleGroup statementGroup;
	private List<Question> questions;
	private List<Question>quest;
	private Controller service;
	
	public TestPane (List<Question> questions, Controller controller){
		service = controller;
		service.addObserver(this);
		this.questions = questions;
		this.quest = questions;

		this.setPrefHeight(300);
		this.setPrefWidth(750);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

		questionField = new Label();
		add(questionField, 0, 0, 1, 1);
		
//		statementGroup = new ToggleGroup();
//		statementGroup.setUserData(question.toString());
		try {
			int random = new Random().nextInt(quest.size());
			this.add(new Label(quest.get(random).toString()),0, 1, 1,1);

			int rowIndex = 3;
			List<String> statemtents = quest.get(random).getStatments();
			List<String> shuffeled = new ArrayList<>();
			shuffeled.addAll(statemtents);

			Collections.shuffle(shuffeled);

			for (String statement : shuffeled){
				this.add(new RadioButton(statement), 0, rowIndex, 1, 1);
				rowIndex ++;
			}
			quest.remove(random);
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
			//TODO fix score when everything is answererd
		}

		submitButton = new Button("Submit");
		add(submitButton,0, 58, 1, 1);

	}

	public void setProcessAnswerAction(EventHandler<ActionEvent> processAnswerAction) {
		submitButton.setOnAction(processAnswerAction);
	}

	public List<String> getSelectedStatements() {
		 List<String> selected = new ArrayList<String>();
		if(statementGroup.getSelectedToggle()!=null){
			selected.add(statementGroup.getSelectedToggle().getUserData().toString());
		}
		return selected;
	}

	@Override
	public void update(ObservableList<Category> categories, ObservableList<Question> questions) {
		this.questions = questions;
		this.quest = questions;
	}
}
