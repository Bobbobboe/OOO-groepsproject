package model.db;

import model.Category;

import java.util.List;

public interface Database {

    void add(Category category);

    Category get(int id);

    void delete(int id);

    List<Category> getAll();
}
