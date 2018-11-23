package controller;

import model.Category;
import model.CategoryFactory;
import model.DomainExeption;
import model.Observable;
import view.Observer;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Observable {

    private List<Category> categories;
    private List<Observer> observers;

    public Controller() {
        categories = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void enrollCategory(String name, String description, Category category) throws DomainExeption {
        CategoryFactory factory = new CategoryFactory();
        Category c = factory.createCategory(name,description,category);
        addCategory(c);
    }

    public void addCategory(Category category) throws DomainExeption {
        if(category == null) throw new DomainExeption();
        categories.add(category);
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

