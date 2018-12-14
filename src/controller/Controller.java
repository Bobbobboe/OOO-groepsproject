package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();
    private Database db;

    Stage primaryStage;
    Stage categoryStage;
    Scene scene;
    AssesMainPane assesMainPane;
    CategoryDetailPane categoryDetailPane;
    QuestionDetailPane questionDetailPane;
    TestPane testPane;
    Stage popup = new Stage();
    Group root = new Group();

    ObservableList<Category> categories;
    ObservableList<Question> questions;

    public Controller() {
         db = new DatabaseText();
         categories = FXCollections.observableArrayList(db.getAllCategories());
         questions = FXCollections.observableArrayList(db.getAllQuestions());

         categories.addListener(new ListChangeListener<Category>() {
             @Override
             public void onChanged(Change<? extends Category> c) {
                 notifyObserver();
             }
         });

         questions.addListener(new ListChangeListener<Question>() {
             @Override
             public void onChanged(Change<? extends Question> c) {
                 notifyObserver();
             }
         });
    }

    public void enrollCategory(String name, String description, Category category) throws DomainExeption {
        CategoryFactory factory = new CategoryFactory();
        Category c = factory.createCategory(name,description,category);
        addCategory(c);
    }

    public void addCategory(Category category) throws DomainExeption {
        if(category == null) throw new DomainExeption();
        categories.add(category);
        db.add(category);
        notifyObserver();
    }

    public void addQuestion(Question question) {
        if(question == null) throw new DomainExeption();
        questions.add(question);
        db.add(question);
        notifyObserver();
    }

    public ObservableList<Category> getCategories(){
//        addCategory(new MainCategory("Design Principles", "The SOLID design principles"));
//        addCategory(new MainCategory("Design patterns", "Design patterns learned this year"));
//        addCategory(new MainCategory("Java", "Java extra's"));
//        addCategory(new MainCategory("UML", "Technique for drawing class diagrams"));

        return categories;
    }

    public ObservableList<Question> getQuestions(){
//        addQuestion(new Question("Welk patroon definieert een familie van algoritmes, kapselt ze in en maakt ze uitwisselbaar ?", db.getCategory(0), "Positive"));
//        addQuestion(new Question("Welk ontwerp patroon is het minst van toepassing op het strategy patroon ?", db.getCategory(1), "Negative"));

        return questions;
    }

    /**
     * The methods for the front UI
     * All the panes
     */
    public QuestionOverviewPane showQuestionOverviewPane(){
        QuestionOverviewPane pane = new QuestionOverviewPane(this);
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
        CategoryOverviewPane pane =  new CategoryOverviewPane(this);
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
        this.questionDetailPane = new QuestionDetailPane(categories);

        questionDetailPane.setCancelAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.close();
            }
        });

        questionDetailPane.setSaveAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Question q = new Question(questionDetailPane.getQuestionField().getText(), (Category) questionDetailPane.getCategoryField().getValue(), questionDetailPane.getFeedbackField().getText());
                q.addStatements(questionDetailPane.getStatements());
                addQuestion(q);
                popup.close();
            }
        });
        return questionDetailPane;
    }

    public CategoryDetailPane popupCategoryDetailPane(){
        this.categoryDetailPane = new CategoryDetailPane(categories);
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

    public TestPane showTestPane(){

        this.testPane = new TestPane(questions, this);

        testPane.setProcessAnswerAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String awnsered_by_user = testPane.getSelectedStatements();
                System.out.println(awnsered_by_user);
                String correct = testPane.getCurrent().getSolution();
                System.out.println(correct);
                popup.close();

                if(testPane.getQuest().size() != 0) {
                    showTestPane();
                } else {

                }

            }
        });

        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        Scene dialogScene = new Scene(testPane);
        popup.setScene(dialogScene);
        popup.show();
        return testPane;
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
            o.update(categories, questions);
        }
    }
}

