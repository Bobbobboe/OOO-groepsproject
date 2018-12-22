package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
    MessagePane messagePane;
    Stage popup = new Stage();
    Group root = new Group();
    Properties properties;

    ObservableList<Category> categories;
    ObservableList<Question> questions;

    Test test;
    Question current;
    String message;

    public Controller() {
         db = new DatabaseText();
         properties = new Properties();
         properties = PropertiesFileLoader.loadEvalutationProperties();

         categories = FXCollections.observableArrayList(db.getAllCategories());
         questions = FXCollections.observableArrayList(db.getAllQuestions());

         test = new Test(questions, this);

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

        pane.setEditAction(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(primaryStage);

                Scene popupScene = new Scene(popupQuestionDetailPane(pane.getSelectedQuestion()));
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

        pane.setEditAction(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);
                popup.initOwner(primaryStage);

                Scene dialogScene = new Scene(popupCategoryDetailPane(pane.getCategory()));
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

    public QuestionDetailPane popupQuestionDetailPane(Question question) {
        int id = questions.indexOf(question);
        this.questionDetailPane = new QuestionDetailPane(categories);
        this.questionDetailPane.setQuestion(question.getQuestion());
        this.questionDetailPane.setCategory(question.getCategory());
        this.questionDetailPane.setFeedback(question.getFeedback());
        this.questionDetailPane.setStatements(question.getStatements());

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
                db.updateQuestion(id, q);
                db.updateQuestions();
                questions.set(id, q);
                notifyObserver();
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

    public CategoryDetailPane popupCategoryDetailPane(Category category) {
        int id = categories.indexOf(category);
        this.categoryDetailPane = new CategoryDetailPane(categories);
        categoryDetailPane.setTitle(category.getName());
        categoryDetailPane.setDescription(category.getDescription());
        categoryDetailPane.setCategory(category.getMainCategory());
        categoryDetailPane.setCancelAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.close();
            }
        });

        categoryDetailPane.setSaveAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CategoryFactory factory = new CategoryFactory();
                Category c = factory.createCategory(categoryDetailPane.getTitleField().getText(), categoryDetailPane.getDescriptionField().getText(), (Category) categoryDetailPane.getCategoryField().getValue());
                db.updateCategory(id, c);
                db.updateCategories();
                categories.set(id, c);
                notifyObserver();
                popup.close();
            }
        });

        return categoryDetailPane;
    }

    public void showTestPane() {
        if(test.testIsFinished()) {
            test.restartTest();
            resetAllScores();
        }

        this.current = test.getNextQuestion();
        this.testPane = new TestPane(current);

        testPane.setProcessAnswerAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String answered_by_user = testPane.getSelectedStatements();
                popup.close();

                if(test.checkAnswers(current, answered_by_user)) {
                    addScore(current.getCategory());
                    message = getScoreOverview();
                }

                if(!test.checkAnswers(current, answered_by_user) && properties.getProperty("evaluation.mode").equals("score")) {
                    message = current.getFeedback();
                }

                if(test.testIsFinished()) {
                    message = getScoreOverview();
                }

                if(test.testIsFinished() && totalScore() == getTotalMaxScore()) {
                    message = "\n\n\t\t\t\t\tSchitterend! Alles perfect!";
                }

                notifyObserver();
            }
        });

        popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        Scene dialogScene = new Scene(testPane);
        popup.setScene(dialogScene);
        popup.show();
    }

    public void showPopupWindow() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);

        Button okButton = new Button();
        okButton.setText("Yes");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                test.restartTest();
                resetAllScores();
                dialog.close();
                showTestPane();
            }
        });

        Button cancelButton = new Button();
        cancelButton.setText("No");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                popup.close();
            }
        });

        dialogVbox.getChildren().add(new Text("Would you like to restart the test"));
        dialogVbox.getChildren().add(okButton);
        dialogVbox.getChildren().add(cancelButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private String getScoreOverview() {
        String ret = "Your score is : " + totalScore() + "/" + getTotalMaxScore() +"\n";
        for(Category c : categories) {
                ret += c.getName() + " : " + c.getScore() + "/" + getMaxScore(c) + "\n";
        }
        return ret;
    }

    private void resetAllScores() {
        for(Category c: categories) {
            c.setScore(0);
        }
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

    public MessagePane showMessagePane() {
        this.messagePane = new MessagePane(this);
        this.messagePane.writeEvaluation(this.test.isAlreadyPlayed());
        return this.messagePane;
    }


    //Methods for the stage component
    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Test getTest() {
        return this.test;
    }

    public String getMessage() {
        return this.message;
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

