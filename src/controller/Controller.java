package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import model.db.Database;
import model.db.DatabaseText;
import model.db.PropertiesFileLoader;
import view.Observer;
import view.pane.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
    Properties properties;

    ObservableList<Category> categories;
    ObservableList<Question> questions;

    public Controller() {
         db = new DatabaseText();
         properties = new Properties();
         properties = PropertiesFileLoader.loadEvalutationProperties();

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

    private void enrollCategory(String name, String description, Category category) throws DomainExeption {
        CategoryFactory factory = new CategoryFactory();
        Category c = factory.createCategory(name,description,category);
        addCategory(c);
    }

    private void addCategory(Category category) throws DomainExeption {
        if(category == null) throw new DomainExeption();
        categories.add(category);
        db.add(category);
        notifyObserver();
    }

    private void addQuestion(Question question) {
        if(question == null) throw new DomainExeption();
        questions.add(question);
        db.add(question);
        notifyObserver();
    }

    public ObservableList<Category> getCategories(){
        return categories;
    }

    public ObservableList<Question> getQuestions(){
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

    public void showTestPane() {
        this.testPane = new TestPane(questions, this);

        testPane.setProcessAnswerAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String answered_by_user = testPane.getSelectedStatements();
                String correct = testPane.getCurrent().getSolution();

                popup.close();

                if(answered_by_user.equals(correct)) {
                    addScore(testPane.getCurrent().getCategory());
                }

                if(!answered_by_user.equals(correct) && properties.getProperty("evaluation.mode").equals("score")) {
                    showFeedbackPopup(testPane.getCurrent());
                }

                notifyObserver();

                if(testPane.getQuest().size() != 0) {

                } else {
                    showMessagePane();
                }

            }
        });

        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        Scene dialogScene = new Scene(testPane);
        popup.setScene(dialogScene);
        popup.show();
    }

    private void showFeedbackPopup(Question current) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(popup);

        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(current.getFeedback()));

        Button button = new Button("Ok");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        dialogVbox.getChildren().add(button);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        dialog.show();
    }

    private void addScore(Category category) {
        for(Category c : categories) {
            if (c.equals(category)) {
                c.addToScore();
            }
        }
    }

    public int totalScore() {
        int max = 0;
        for(Category c : this.categories) {
            max += c.getScore();
        }
        return max;
    }

    public int getTotalMaxScore() {
        int max = 0;
        for(Question q : db.getAllQuestions()) {
            max += 1;
        }
        return max;
    }

    public int getMaxScore(Category c) {
        int max = 0;
        for(Question q : db.getAllQuestions()) {
            if(q.getCategory().equals(c)) {
                max += 1;
            }
        }
        return max;
    }

    public MessagePane showMessagePane(){
        return new MessagePane(this);
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

