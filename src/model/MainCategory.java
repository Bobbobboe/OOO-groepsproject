package model;

import model.db.Database;
import model.db.DatabaseText;
import view.Observer;

import java.io.Serializable;

public class MainCategory extends Category {
    public static final long serialversionUID = 2L;


    public MainCategory(String name, String description) {
        super(name, description, null);
    }


    @Override
    public String toString() {
        return super.toString();
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
