package model;

import controller.Controller;
import javafx.collections.ObservableList;
import view.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test implements Observer {
    private ObservableList<Question> questions;
    private boolean alreadyPlayed;
    private List<Question> quest;
    private Controller service;

    public Test(ObservableList<Question> questions, Controller controller) {
        this.questions = questions;
        this.alreadyPlayed = false;
        this.quest = new ArrayList<>(questions);
        service = controller;
        service.addObserver(this);

        Collections.shuffle(quest);
    }

    public Question getNextQuestion() {
        if(testIsFinished()) {
            return null;
        } if(quest.size() == 1) {
            this.alreadyPlayed =true;
        }

        Question ret = quest.get(0);
        quest.remove(0);
        return ret;
    }

    public boolean testIsFinished() {
        if(this.quest.size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isAlreadyPlayed() {
        return this.alreadyPlayed;
    }

    public boolean checkAnswers(Question question, String statement) {
        return question.getSolution().equals(statement);
    }

    public void restartTest() {
        this.quest = new ArrayList<>(questions);
        Collections.shuffle(quest);
    }

    @Override
    public void update(ObservableList<Category> categories, ObservableList<Question> questions) {
        this.questions = questions;
    }
}
