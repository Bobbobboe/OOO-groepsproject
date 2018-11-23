package application;

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

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            QuestionOverviewPane questionOverviewPane = new QuestionOverviewPane();
            QuestionDetailPane questionDetailPane = new QuestionDetailPane();

            CategoryOverviewPane categoryOverviewPanel = new CategoryOverviewPane();
            CategoryDetailPane categoryDetailPanel = new CategoryDetailPane();

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
