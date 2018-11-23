package controller;

import model.Category;
import model.CategoryFactory;
import model.DomainExeption;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Category> categories = new ArrayList<>();

    public void enrollCategory(String name, String description, Category category) throws DomainExeption {
        CategoryFactory factory = new CategoryFactory();
        Category c = factory.createCategory(name,description,category);
        addCategory(c);
    }

    public void addCategory(Category category) throws DomainExeption {
        if(category == null) throw new DomainExeption();
        categories.add(category);
    }

}

