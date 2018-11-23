package model;

public class MainCategory implements Category  {

    private String name;
    private String description;
    private String type = "main";

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
