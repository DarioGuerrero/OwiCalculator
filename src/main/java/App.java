import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Michal N. on 2016-11-16.
 */
public class App extends Application {



    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/ui.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/view1.css").toExternalForm());


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/graphics/calculator_icon.png"));
        primaryStage.setTitle("OwiCalculator");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
