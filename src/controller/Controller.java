package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import model.db.Database;
import model.db.DatabaseText;
import view.Observer;
import view.pane.*;

import java.util.List;

public class Controller implements Subject {
    private List<Observer> observers;
    private Database db;
    Stage primaryStage;
    Scene scene;
    AssesMainPane assesMainPane;
    Group root = new Group();

    public Controller() {
         db = new DatabaseText();
    }

    public void enrollCategory(String name, String description, Category category) throws DomainExeption {
        CategoryFactory factory = new CategoryFactory();
        Category c = factory.createCategory(name,description,category);
        addCategory(c);
    }

    public void addCategory(Category category) throws DomainExeption {
        if(category == null) throw new DomainExeption();
        db.add(category);
        notifyObserver();
    }

    public void addQuestion(Question question) {
        if(question == null) throw new DomainExeption();
        db.add(question);
        notifyObserver();
    }

    public ObservableList<Category> getCategories(){
//        db.add(new MainCategory("Design Principles", "The SOLID design principles"));
//        db.add(new MainCategory("Design pattersn", "Design patterns learned this year"));
//        db.add(new MainCategory("Java", "Java extra's"));
//        db.add(new MainCategory("UML", "Technique for drawing class diagrams"));

        return FXCollections.observableArrayList(db.getAllCategories());
    }

    public ObservableList<Question> getQuestions(){
//        db.add(new Question("Welk patroon definieert een familie van algoritmes, kapselt ze in en maakt ze uitwisselbaar ?", db.getCategory(0), "Positive"));
//        db.add(new Question("Welk ontwerp patroon is het minst van toepassing op het strategy patroon ?", db.getCategory(1), "Negative"));

        return FXCollections.observableArrayList(db.getAllQuestions());
    }

    /**
     * The methods for the front UI
     * All the panes
     */
    public QuestionOverviewPane showQuestionOverviewPane(){
        return new QuestionOverviewPane();
    }

    public CategoryOverviewPane showCategoryOverviewPane(){
        return new CategoryOverviewPane();
    }

    public QuestionDetailPane popupQuestionDetailPane(){
        return new QuestionDetailPane();
    }

    public CategoryDetailPane popupCategoryDetailPane(){
        return new CategoryDetailPane();
    }

    public TestPane showtestPane(){
        return new TestPane();
    }

    public MessagePane showMessagePane(){
        return new MessagePane();
    }

    //Methods for the stage component
    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Stage getStage(){
        return primaryStage;
    }

    //Methods for borderPane component
    public Group getGroup(){
        return root;
    }

    //Methods for scene
    public void setScene(Group root, int width, int height) {
        scene = new Scene(root, width, height);
    }

    public Scene getScene(){
        return this.scene;
    }

    //Methods for borderpane
    public void setBorderPane(AssesMainPane mainPane){
        this.assesMainPane = mainPane;
    }
    public BorderPane getBorderPane(){
        return assesMainPane;
    }

    public void showStage(){
        primaryStage.show();
    }

    /**
     * Methods for the observer patterns
     *
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for(Observer o: observers){
            o.notify();
        }
    }
}

