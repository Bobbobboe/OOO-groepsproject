package model;

import model.db.Database;
import model.db.DatabaseText;

public class MainCategory implements Category  {

    private String name;
    private String description;
    private String type = "main";
    private Database database = new DatabaseText();

    public MainCategory(String name, String description) {
        setName(name);
        setDescription(description);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return type;
    }
}
