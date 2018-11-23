package model;

public class SubCategory extends MainCategory implements Category{

    private Category category;
    private String type = "sub";

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

    public String getType() {
        return type;
    }
}
