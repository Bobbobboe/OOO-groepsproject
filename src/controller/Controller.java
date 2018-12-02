package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
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
    Stage categoryStage;
    Scene scene;
    AssesMainPane assesMainPane;
    Group root = new Group();
    CategoryDetailPane categoryDetailPane;
    QuestionDetailPane questionDetailPane;
    Stage popup;

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
//        notifyObserver();
    }

    public void addQuestion(Question question) {
        if(question == null) throw new DomainExeption();
        db.add(question);
//        notifyObserver();
    }

    public ObservableList<Category> getCategories(){
//        addCategory(new MainCategory("Design Principles", "The SOLID design principles"));
//        addCategory(new MainCategory("Design patterns", "Design patterns learned this year"));
//        addCategory(new MainCategory("Java", "Java extra's"));
//        addCategory(new MainCategory("UML", "Technique for drawing class diagrams"));

        return FXCollections.observableArrayList(db.getAllCategories());
    }

    public ObservableList<Question> getQuestions(){
//        addQuestion(new Question("Welk patroon definieert een familie van algoritmes, kapselt ze in en maakt ze uitwisselbaar ?", db.getCategory(0), "Positive"));
//        addQuestion(new Question("Welk ontwerp patroon is het minst van toepassing op het strategy patroon ?", db.getCategory(1), "Negative"));

        return FXCollections.observableArrayList(db.getAllQuestions());
    }

    /**
     * The methods for the front UI
     * All the panes
     */
    public QuestionOverviewPane showQuestionOverviewPane(){
        QuestionOverviewPane pane = new QuestionOverviewPane();
        pane.setNewAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(primaryStage);

                Scene popupScene = new Scene(popupQuestionDetailPane());
                popup.setScene(popupScene);
                popup.show();
            }
        });
        return pane;
    }



    public CategoryOverviewPane showCategoryOverviewPane(){
        CategoryOverviewPane pane =  new CategoryOverviewPane();
        pane.setNewAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(primaryStage);

                Scene dialogScene = new Scene(popupCategoryDetailPane());
                popup.setScene(dialogScene);
                popup.show();
            }
        });
        return pane;
    }

    public QuestionDetailPane popupQuestionDetailPane(){
        this.questionDetailPane = new QuestionDetailPane(FXCollections.observableArrayList(db.getAllCategories()));

        questionDetailPane.setCancelAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.close();
            }
        });

        questionDetailPane.setSaveAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addQuestion(new Question(questionDetailPane.getQuestionField().getText(), (Category) questionDetailPane.getCategoryField().getValue(), questionDetailPane.getFeedbackField().getText()));
                popup.close();
            }
        });
        return questionDetailPane;
    }

    public CategoryDetailPane popupCategoryDetailPane(){
        this.categoryDetailPane = new CategoryDetailPane(FXCollections.observableArrayList(db.getAllCategories()));
        categoryDetailPane.setCancelAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.close();
            }
        });

        categoryDetailPane.setSaveAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enrollCategory(categoryDetailPane.getTitleField().getText(), categoryDetailPane.getDescriptionField().getText(), (Category) categoryDetailPane.getCategoryField().getValue());
                popup.close();
            }
        });
        return categoryDetailPane;
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

