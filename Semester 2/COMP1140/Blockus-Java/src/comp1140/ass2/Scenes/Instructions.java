package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

/**
 * @author ***REMOVED***, on 25/09/15
 */
public class Instructions extends Scene {
    /**
     * Creates a new Instruction Scene, which uses a webview to display the instructions, stored in a
     * HTML file. Instructions taken from http://www.educationallearninggames.com/how-to-play-blokus-game-rules.asp
     * @param root required by Scene
     * @param width required by Scene
     * @param height required by Scene
     * @param parent used to accedd the Blokus scene handling methods
     */
    public Instructions(Group root, double width, double height, Scene scene, Blokus parent) {
        super(root, width, height, Color.LIGHTBLUE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");
        parent.setTitle("Blokus: Instructions");

        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Blokus.class.getResourceAsStream("Assets/blokusbg.png"));
        imv1.setImage(image3);
        imv1.setLayoutX(0); imv1.setLayoutY(0);
        imv1.setFitWidth(700);
        imv1.setPreserveRatio(true);
        root.getChildren().add(imv1);

        Button button2 = new Button("<");
        button2.setOnAction(e -> parent.toScene(scene));
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

        String url = Blokus.class.getResource("Assets/Instructions/index.html").toExternalForm();
        WebView web = new WebView();
        web.setMaxSize(540, 420);
        web.setLayoutX(30);
        web.setLayoutY(30);
        web.getEngine().load(url);

        pane.getChildren().add(web);
        root.getChildren().add(pane);


        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setFitWidth(400);
        imv.setPreserveRatio(true);
        imv.setLayoutX(350 - 200); imv.setLayoutY(40);
        root.getChildren().add(imv);



    }
}
