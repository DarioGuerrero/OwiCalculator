import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Michal N. on 2016-11-16.
 */
public class App extends Application {

    private String pathStyleCss = "/css/view1.css";

    public void setPathStyleCss(String pathStyleCss) {
        this.pathStyleCss = pathStyleCss;

    }

    public void start(Stage primaryStage) throws Exception {


        //Menu
        MenuItem itemCopy = new MenuItem("Copy");
        MenuItem itemView1 = new MenuItem("View Classic");
        MenuItem itemView2 = new MenuItem("View Orange");
        MenuItem itemView3 = new MenuItem("View White");
        MenuItem itemExit = new MenuItem("Exit");
        Menu menu1 = new Menu("Menu");
        menu1.setMnemonicParsing(false);
        menu1.getItems().addAll(itemCopy,new SeparatorMenuItem(),itemView1, itemView2, itemView3, new SeparatorMenuItem(), itemExit);

        MenuItem itemAbout = new MenuItem("About me");
        Menu menu2 = new Menu("Info");
        menu2.getItems().add(itemAbout);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2);



        Pane window1 = FXMLLoader.load(getClass().getResource("fxml/ui.fxml"));
        BorderPane window2 = new BorderPane(window1);
        window2.setPrefHeight(220);
        window2.setTop(menuBar);



//        Parent root = FXMLLoader.load(getClass().getResource("fxml/ui.fxml"));
        Scene scene = new Scene(window2);

        scene.getStylesheets().add(getClass().getResource(pathStyleCss).toExternalForm());


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
