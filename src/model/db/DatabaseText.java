package model.db;

import model.Category;
import model.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseText implements Database {
    private List<Category> categories = new ArrayList<>();

    @Override
    public void add(Category catergory) {
        categories.add(catergory);
    }

    @Override
    public Category get(int id) {
        return categories.get(id);
    }

    @Override
    public void delete(int id) {
        categories.remove(get(id));
    }

    @Override
    public List<Category> getAll() {
        return categories;
    }

    public void fillListQuestions() {
        try(Scanner scanner = new Scanner(new File("vragen.txt"))){
            while(scanner.hasNextLine()){
                Scanner lijnScanner = new Scanner(scanner.nextLine());
                lijnScanner.useDelimiter(",");
                int id = Integer.parseInt(lijnScanner.next());
                String name = lijnScanner.next();
                String description = lijnScanner.next();
                Category c = new Category(id, name, description);
                this.add(c);

            }
        } catch (FileNotFoundException e) {
            throw new DbException(e.getMessage());
        }


    }
}
