package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Created by nosha on 25/09/15.
 */
public class GameOptions extends Scene {
    public GameOptions(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.LIGHTBLUE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");
        parent.setTitle("Blokus: Players");


        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Blokus.class.getResourceAsStream("Assets/blokusbg.png"));
        imv1.setImage(image3);
        imv1.setLayoutX(0); imv1.setLayoutY(0);
        imv1.setFitWidth(700);
        imv1.setPreserveRatio(true);
        root.getChildren().add(imv1);


        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setFitWidth(400);
        imv.setPreserveRatio(true);
        imv.setLayoutX(350-200); imv.setLayoutY(40);
        root.getChildren().add(imv);

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Humam","Easy Bot","Medium Bot","Hard Bot","None");
        list.setItems(items);
        list.setMaxHeight(125);
        root.getChildren().add(list);



        Button button0 = new Button("Red?: Bot");
        button0.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGame(); }}); button0.setMinSize(160, 40); button0.setLayoutX(200 - button0.getMinWidth() / 2); button0.setLayoutY(300); button0.getStyleClass().add("button"); root.getChildren().add(button0);
        Button button1 = new Button("Green?: Bot");button1.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGame(); }}); button1.setMinSize(160, 40); button1.setLayoutX(200 - button1.getMinWidth() / 2); button1.setLayoutY(500); button1.getStyleClass().add("button"); root.getChildren().add(button1);
        Button button3 = new Button("Blue: Bot");button3.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGame(); }}); button3.setMinSize(160, 40); button3.setLayoutX(500 - button3.getMinWidth() / 2); button3.setLayoutY(300); button3.getStyleClass().add("button"); root.getChildren().add(button3);
        Button button4 = new Button("Blue Bot");button4.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGame(); }}); button4.setMinSize(160, 40); button4.setLayoutX(500 - button4.getMinWidth() / 2); button4.setLayoutY(500); button4.getStyleClass().add("button"); root.getChildren().add(button4);
        Button button = new Button("Play!");button.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGame(); }}); button.setMinSize(160, 40); button.setLayoutX(350 - button.getMinWidth() / 2); button.setLayoutY(600); button.getStyleClass().add("button"); root.getChildren().add(button);

        // Increment button layoutY by 60 (eg 360, 420, 480, etc)


    }
}
