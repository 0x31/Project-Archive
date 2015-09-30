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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***, on 25/09/15
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

        Button button5 = new Button("<");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                parent.toMenu();
            }
        });
        button5.setMinSize(40, 40);
        button5.setMaxSize(40, 40);
        button5.setLayoutX(30 - button5.getMinWidth() / 2); button5.setLayoutY(10);
        button5.getStyleClass().add("back");
        button5.getStyleClass().add("button1");
        root.getChildren().add(button5);

        String[] players = new String[]{
                "None", "Human", "Easy Bot", "Hard Bot"
        };
        int[] buttonState = new int[] {0,0,0,0};

        Button button0 = new Button("+");
        button0.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                buttonState[0]=(buttonState[0]+1)%4;
                button0.setText(players[buttonState[0]]);
            }
        });
        button0.setMinSize(160, 160);
        button0.setLayoutX(260 - button0.getMinWidth() / 2);
        button0.setLayoutY(260);
        button0.getStyleClass().add("addPlayer");
        button0.getStyleClass().add("blue");
        root.getChildren().add(button0);

        Button button1 = new Button("+");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                buttonState[2]=(buttonState[2]+1)%4;
                button1.setText(players[buttonState[2]]);
            }
        });
        button1.setMinSize(160, 160);
        button1.setLayoutX(260 - button1.getMinWidth() / 2);
        button1.setLayoutY(440);
        button1.getStyleClass().add("addPlayer");
        button1.getStyleClass().add("red");
        root.getChildren().add(button1);

        Button button2 = new Button("+");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                buttonState[1]=(buttonState[1]+1)%4;
                button2.setText(players[buttonState[1]]);
            }
        });
        button2.setMinSize(160, 160);
        button2.setLayoutX(440 - button2.getMinWidth() / 2);
        button2.setLayoutY(260);
        button2.getStyleClass().add("addPlayer");
        button2.getStyleClass().add("yellow");
        root.getChildren().add(button2);

        Button button3 = new Button("+");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                buttonState[3] = (buttonState[3] + 1) % 4;
                button3.setText(players[buttonState[3]]);
            }
        });
        button3.setMinSize(160, 160);
        button3.setLayoutX(440 - button3.getMinWidth() / 2);
        button3.setLayoutY(440);
        button3.getStyleClass().add("addPlayer");
        button3.getStyleClass().add("green");
        root.getChildren().add(button3);

        Button button = new Button("Play!");
        button.getStyleClass().add("button1");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //if(buttonState[0]+buttonState[1]+buttonState[2]+buttonState[3]==0) {
                //}
                //else {
                    parent.toGame(buttonState);
                //}
            }
        }); button.setMinSize(160, 40); button.setLayoutX(350 - button.getMinWidth() / 2); button.setLayoutY(620);
        button.getStyleClass().add("button"); root.getChildren().add(button);



    }
}
