package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Created by nosha on 25/09/15.
 */
public class Menu extends Scene {
    public Menu(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.LIGHTBLUE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");


        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setLayoutX(200); imv.setLayoutY(100);
        root.getChildren().add(imv);



        Button button2 = new Button("Play");
        button2.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGame();
        }});
        button2.setMinSize(160, 40);
        button2.setLayoutX(350 - button2.getMinWidth() / 2); button2.setLayoutY(360);
        button2.getStyleClass().add("button");
        root.getChildren().add(button2);

        Button button0 = new Button("Instructions");
        button0.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) {parent.toInstructions();}});
        button0.setMinSize(160, 40);
        button0.setLayoutX(350 - button0.getMinWidth() / 2); button0.setLayoutY(420);
        root.getChildren().add(button0);

        Button button1 = new Button("Options");
        button1.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toOptions(); }});
        button1.setMinSize(160, 40);
        button1.setLayoutX(350 - button1.getMinWidth() / 2); button1.setLayoutY(480);
        root.getChildren().add(button1);

    }
}
