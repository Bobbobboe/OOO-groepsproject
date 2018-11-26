package model;

import view.Observer;

import java.io.Serializable;

public class SubCategory extends MainCategory {

    private Category category;

    public SubCategory(String name, String description, Category category) {
        super(name, description);
        setCategory(category);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return this.category.toString();
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
