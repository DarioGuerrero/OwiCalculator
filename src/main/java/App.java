import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Michal N. on 2016-11-16.
 */
public class App extends Application {

    private String pathStyleCss0 = "/css/view0.css";
    private String pathStyleCss1 = "/css/view1.css";
    private String pathStyleCss2 = "/css/view2.css";
    private String pathStyleCss3 = "/css/view3.css";


    public void start(Stage primaryStage) throws Exception {

        //RadioMenuItems
        ToggleGroup toggle = new ToggleGroup();
        RadioMenuItem showView1 = new RadioMenuItem("View Classic");
        RadioMenuItem showView2 = new RadioMenuItem("View Orange");
        RadioMenuItem showView3 = new RadioMenuItem("View White");
        RadioMenuItem showView4 = new RadioMenuItem("View Pink");
        showView2.setSelected(true);
        showView1.setToggleGroup(toggle);
        showView2.setToggleGroup(toggle);
        showView3.setToggleGroup(toggle);
        showView4.setToggleGroup(toggle);

        MenuItem itemExit = new MenuItem("Exit");
        //Menu one
        Menu menu1 = new Menu("Menu");
        menu1.setMnemonicParsing(false);
        menu1.getItems().addAll(showView1, showView2, showView3, showView4, new SeparatorMenuItem(), itemExit);
        MenuItem itemAbout = new MenuItem("About me");
        //Menu two
        Menu menu2 = new Menu("Info");
        menu2.getItems().add(itemAbout);
        //MenuBar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2);



        Pane window1 = FXMLLoader.load(getClass().getResource("fxml/ui.fxml"));
        BorderPane window2 = new BorderPane(window1);
        window2.setPrefHeight(222);
        window2.setTop(menuBar);
        Scene scene = new Scene(window2);
        scene.getStylesheets().add(getClass().getResource("/css/view1.css").toExternalForm());

        //Obsluga zdarzen menu
        showView1.setOnAction(e-> {
            scene.getStylesheets().clear();
            setUserAgentStylesheet(null);
            scene.getStylesheets().add(getClass().getResource(pathStyleCss0).toExternalForm());
        });
        showView2.setOnAction(e-> {
            scene.getStylesheets().clear();
            setUserAgentStylesheet(null);
            scene.getStylesheets().add(getClass().getResource(pathStyleCss1).toExternalForm());
        });
        showView3.setOnAction(e-> {
            scene.getStylesheets().clear();
            setUserAgentStylesheet(null);
            scene.getStylesheets().add(getClass().getResource(pathStyleCss2).toExternalForm());
        });
        showView4.setOnAction(e-> {
            scene.getStylesheets().clear();
            setUserAgentStylesheet(null);
            scene.getStylesheets().add(getClass().getResource(pathStyleCss3).toExternalForm());
        });

        itemExit.setOnAction(e-> {
            Platform.exit();
            System.exit(0);
        });
        itemAbout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Stage stage = new Stage();
                Scene scene = new Scene(new VBox());
                stage.setTitle("popup");
                stage.setScene(scene);
                stage.show();
            }
        });


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
