package model;

import view.Observer;

import java.io.Serializable;

public class SubCategory extends Category {
    public static final long serialversionUID = 3L;

    private Category category;
    private String name;
    private String description;

    public SubCategory(String name, String description, Category category) {
        super(name, description);
        setCategory(category);
        this.name = name;
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
