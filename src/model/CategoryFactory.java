package model;

public class CategoryFactory {

    public Category createCategory(String name, String description, Category category) {
        Category c = null;
        if(category.equals(null)){
            c = new MainCategory(name, description);
        }else if(category != null){
            c = new SubCategory(name, description, category);
        }else{
            throw new DomainExeption();
        }

        return c;
    }
}
