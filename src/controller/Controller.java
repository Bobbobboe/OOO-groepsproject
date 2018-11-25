package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.CategoryFactory;
import model.DomainExeption;
import model.Observable;
import model.db.Database;
import model.db.DatabaseText;
import view.Observer;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Observable {

    private List<Category> categories;
    private List<Observer> observers;
    private Database db;

    public Controller() {
         db = new DatabaseText();
    }

    public void enrollCategory(String name, String description, Category category) throws DomainExeption {
        CategoryFactory factory = new CategoryFactory();
        Category c = factory.createCategory(name,description,category);
        addCategory(c);
    }

    public void addCategory(Category category) throws DomainExeption {
        if(category == null) throw new DomainExeption();
        db.getAllCategories().add(category);
    }

    public ObservableList<Category> getCategories(){
        return FXCollections.observableArrayList(db.getAllCategories());
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for(Observer o: observers){
            o.notify();
        }
    }
}

