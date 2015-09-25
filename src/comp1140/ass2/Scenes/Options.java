package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Created by nosha on 25/09/15.
 */
public class Options extends Scene {
    public Options(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.LIGHTBLUE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");
        parent.setTitle("Blokus: Options");

        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setLayoutX(200); imv.setLayoutY(100);
        root.getChildren().add(imv);



        Button button2 = new Button("Back");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                parent.toMenu();
            }
        });
        button2.setMinSize(160, 40);
        button2.setLayoutX(100 - button2.getMinWidth() / 2); button2.setLayoutY(10);
        button2.getStyleClass().add("button");
        root.getChildren().add(button2);

        Label label = new Label("Options:");
        label.setMinSize(160, 40);
        label.setLayoutX(350 - label.getMinWidth() / 2); label.setLayoutY(300);
        root.getChildren().add(label);

    }
}
