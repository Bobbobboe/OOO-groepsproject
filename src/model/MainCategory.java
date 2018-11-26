package model;

import model.db.Database;
import model.db.DatabaseText;
import view.Observer;

import java.io.Serializable;

public class MainCategory extends Category {

    private String name;
    private String description;

    public MainCategory(String name, String description) {
        super(name, description);
    }


    @Override
    public String toString() {
        return this.name;
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
}
