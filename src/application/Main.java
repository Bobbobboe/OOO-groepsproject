package application;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.pane.AssesMainPane;
import view.pane.CategoryDetailPane;
import view.pane.CategoryOverviewPane;
import view.pane.MessagePane;
import view.pane.QuestionDetailPane;
import view.pane.QuestionOverviewPane;
import view.pane.TestPane;

import java.security.SecureRandom;
import java.text.StringCharacterIterator;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            Controller service = new Controller();

            QuestionOverviewPane questionOverviewPane = service.showQuestionOverviewPane();//new QuestionOverviewPane();
            QuestionDetailPane questionDetailPane = service.popupQuestionDetailPane();//new QuestionDetailPane();

            CategoryOverviewPane categoryOverviewPanel = service.showCategoryOverviewPane();//new CategoryOverviewPane();
            CategoryDetailPane categoryDetailPanel = service.popupCategoryDetailPane();//new CategoryDetailPane();

            TestPane testPane = new TestPane();
            MessagePane messagePane = new MessagePane();

            Group root = new Group();
            Scene scene = new Scene(root, 750, 400);

            BorderPane borderPane = new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane);
            borderPane.prefHeightProperty().bind(scene.heightProperty());
            borderPane.prefWidthProperty().bind(scene.widthProperty());

            root.getChildren().add(borderPane);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
