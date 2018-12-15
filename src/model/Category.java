package model;

import javafx.collections.ObservableList;
import view.Observer;

import java.io.Serializable;

public abstract class Category implements Serializable, Subject {

    private String name;
    private String description;
    private int score;

    public Category(String name, String description){
        setDescription(description);
        setName(name);
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToScore() {
        this.score += 1;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObserver() {

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Category) {
            Category c = (Category) o;
            if(this.getName().equals(c.getName())) {
                return true;
            }
        }
        return false;
    }
}
