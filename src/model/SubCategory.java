package model;

import java.io.Serializable;

public class SubCategory extends MainCategory implements Category, Serializable {

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
}
