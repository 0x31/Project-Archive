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
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***, on 25/09/15
 */
public class Menu extends Scene {
    /**
     * Creates a new Menu scene
     * @param root required by Scene
     * @param width required by Scene
     * @param height required by Scene
     * @param parent to access scene changing methods
     */
    public Menu(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.LIGHTBLUE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");
        parent.setTitle("Blokus: Main Menu");


        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/blokusbg3.png"));
        imv.setImage(image2);
        imv.setLayoutX(0); imv.setLayoutY(0);
        imv.setFitWidth(700);
        imv.setPreserveRatio(true);
        root.getChildren().add(imv);

        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv1.setImage(image3);
        imv1.setFitWidth(400);
        imv1.setPreserveRatio(true);
        imv1.setLayoutX(350-imv1.getFitWidth()/2); imv1.setLayoutY(40);
        root.getChildren().add(imv1);



        Button button2 = new Button("Play");
        button2.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toGameOptions();
        }});
        button2.setMinSize(160, 60);
        button2.setLayoutX(150 - button2.getMinWidth() / 2); button2.setLayoutY(600);
        button2.getStyleClass().add("button1");
        root.getChildren().add(button2);

        Button button0 = new Button("Instructions");
        button0.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) {parent.toInstructions();}});
        button0.setMinSize(160, 60);
        button0.setLayoutX(350 - button0.getMinWidth() / 2); button0.setLayoutY(600);
        button0.getStyleClass().add("button1");
        root.getChildren().add(button0);

        Button button1 = new Button("Options");
        button1.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e) { parent.toOptions(); }});
        button1.setMinSize(160, 60);
        button1.setLayoutX(550 - button1.getMinWidth() / 2); button1.setLayoutY(600);
        button1.getStyleClass().add("button1");
        root.getChildren().add(button1);




    }
}
