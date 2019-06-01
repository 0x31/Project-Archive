package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created on 25/09/15
 */
public class Options extends Scene {
    /**
     * Creates a new Options scene
     * Not implemented yet - gives the user options to change things like
     * sound (maybe?), theme (maybe?)
     * as well as giving some information about the team of developers behind Blokus - Team Thu09i
     *
     * @param root required by Scene
     * @param width required by Scene
     * @param height required by Scene
     * @param parent to access scene changing methods
     */
    public Options(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.LIGHTBLUE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");
        parent.setTitle("Blokus: Options");

        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Blokus.class.getResourceAsStream("Assets/blokusbg.png"));
        imv1.setImage(image3);
        imv1.setLayoutX(0); imv1.setLayoutY(0);
        imv1.setFitWidth(700);
        imv1.setPreserveRatio(true);
        root.getChildren().add(imv1);



        Button button2 = new Button("<");
        button2.setOnAction(e -> parent.toMenu());
        button2.setMinSize(40, 40);
        button2.setMaxSize(40, 40);
        button2.setLayoutX(30 - button2.getMinWidth() / 2); button2.setLayoutY(10);
        button2.getStyleClass().add("back");
        button2.getStyleClass().add("button1");
        root.getChildren().add(button2);

        Pane pane = new Pane();
        pane.setMinSize(600, 480);
        pane.setMaxSize(600, 480);
        pane.setLayoutX(50);
        pane.setLayoutY(180);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff;" +
                "-fx-background-insets: 0,30;");

        //pane.getChildren().add();
        root.getChildren().add(pane);


        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setFitWidth(400);
        imv.setPreserveRatio(true);
        imv.setLayoutX(350 - 200); imv.setLayoutY(40);
        root.getChildren().add(imv);

        Button button1 = new Button("Disable Options");
        button1.setOnAction(e -> {
            ((Menu) parent.menu).options.setText("84");
            ((Menu) parent.menu).options.setOnAction(a -> parent.toGame(new int[] {1,0,0,0}));
            parent.toMenu();
        });
        button1.setMinSize(160, 60);
        button1.setLayoutX(335 - button1.getMinWidth() / 2); button1.setLayoutY(300);
        button1.getStyleClass().add("button1");
        root.getChildren().add(button1);



    }
}
