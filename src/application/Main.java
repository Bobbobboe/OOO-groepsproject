package application;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
            service.setStage(primaryStage);

            QuestionOverviewPane questionOverviewPane = service.showQuestionOverviewPane();//new QuestionOverviewPane();
            QuestionDetailPane questionDetailPane = service.popupQuestionDetailPane();//new QuestionDetailPane();

            CategoryOverviewPane categoryOverviewPanel = service.showCategoryOverviewPane();//new CategoryOverviewPane();
            CategoryDetailPane categoryDetailPanel = service.popupCategoryDetailPane();//new CategoryDetailPane();

            TestPane testPane = service.showtestPane();//new TestPane();
            MessagePane messagePane = service.showMessagePane();new MessagePane();

            Group root = service.getGroup(); //new Group();
            service.setScene(root, 750, 400);//new Scene(root, 750, 400);
            Scene scene = service.getScene();

            service.setBorderPane(new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane));//new AssesMainPane(messagePane, categoryOverviewPanel, questionOverviewPane);
            BorderPane borderPane = service.getBorderPane();
            borderPane.prefHeightProperty().bind(scene.heightProperty());
            borderPane.prefWidthProperty().bind(scene.widthProperty());

            root.getChildren().add(borderPane);
            service.getStage().setScene(scene);
            service.getStage().sizeToScene();
            service.showStage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
