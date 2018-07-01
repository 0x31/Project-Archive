package comp1140.ass2.Scenes;

import comp1140.ass2.Blokus;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * @author ***REMOVED***, on 25/09/15
 */
public class GameOptions extends Scene {
    /**
     * Creates a new GameOptions Scene, which contains four buttons allowing the player to choose number of players and
     * opponents by toggling between None, Human, EasyBot, GreedyBot4.
     * @param root required by Scene
     * @param width required by Scene
     * @param height required by Scene
     * @param parent the main Blokus class
     */
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
        button5.setOnAction(e -> parent.toMenu());
        button5.setMinSize(40, 40);
        button5.setMaxSize(40, 40);
        button5.setLayoutX(30 - button5.getMinWidth() / 2); button5.setLayoutY(10);
        button5.getStyleClass().add("back");
        button5.getStyleClass().add("button1");
        root.getChildren().add(button5);

        int[] buttonState = new int[] {1,0,0,0};

        Button button0 = new Button("Human");
        Button button1 = new Button("+");
        button1.setVisible(false);
        Button button2 = new Button("+");
        Button button3 = new Button("+");
        button3.setVisible(false);
        Button[] buttons = new Button[] {button0,button2,button1,button3};

        button0.setOnAction(e -> updateButtons(buttons, buttonState, 0));
        button0.setMinSize(160, 160);
        button0.setLayoutX(260 - button0.getMinWidth() / 2);
        button0.setLayoutY(260);
        button0.getStyleClass().add("addPlayer");
        button0.getStyleClass().add("blue");
        root.getChildren().add(button0);


        button1.setOnAction(e -> updateButtons(buttons, buttonState, 2));
        button1.setMinSize(160, 160);
        button1.setLayoutX(440 - button1.getMinWidth() / 2);
        button1.setLayoutY(440);
        button1.getStyleClass().add("addPlayer");
        button1.getStyleClass().add("gray");
        root.getChildren().add(button1);

        button2.setOnAction(e -> updateButtons(buttons, buttonState, 1));
        button2.setMinSize(160, 160);
        button2.setLayoutX(440 - button2.getMinWidth() / 2);
        button2.setLayoutY(260);
        button2.getStyleClass().add("addPlayer");
        button2.getStyleClass().add("gray");
        root.getChildren().add(button2);

        button3.setOnAction(e -> updateButtons(buttons, buttonState, 3));
        button3.setMinSize(160, 160);
        button3.setLayoutX(260 - button3.getMinWidth() / 2);
        button3.setLayoutY(440);
        button3.getStyleClass().add("addPlayer");
        button3.getStyleClass().add("gray");
        root.getChildren().add(button3);

        Button button = new Button("Play!");
        button.getStyleClass().add("button1");
        button.setOnAction(e -> parent.toGame(buttonState));
        button.setMinSize(340, 40); button.setLayoutX(350 - button.getMinWidth() / 2); button.setLayoutY(620);
        button.getStyleClass().add("button"); root.getChildren().add(button);

    }

    public void updateButtons(Button[] buttons, int[] buttonState, int change) {
        String[] players = new String[]{
                "+", "Human", "Easy Bot", "Hard Bot", "Max Bot"
        };
        buttonState[change] = (buttonState[change]+1)%players.length;
        if(buttonState[0]==0) buttonState[0]++;
        buttons[change].setText(players[buttonState[change]]);
        if(change==1&& buttonState[1]==0) {
            buttons[1].getStyleClass().remove("yellow");
            buttons[1].getStyleClass().add("gray");
            buttons[2].setVisible(false);
            buttonState[2]=0;
            buttons[2].setText(players[0]);
            buttons[2].getStyleClass().remove("red");
            buttons[2].getStyleClass().add("gray");
            buttons[3].setVisible(false);
            buttonState[3]=0;
            buttons[3].setText(players[0]);
            buttons[3].getStyleClass().remove("green");
            buttons[3].getStyleClass().add("gray");
        }
        if(change==1 && buttonState[1]==1) {
            buttons[1].getStyleClass().remove("gray");
            buttons[1].getStyleClass().add("yellow");
            buttons[2].setVisible(true);
        }
        if(change==2 && buttonState[2]==0) {
            buttons[2].getStyleClass().remove("red");
            buttons[2].getStyleClass().add("gray");
            buttons[3].setVisible(false);
            buttonState[3]=0;
            buttons[3].setText(players[0]);
            buttons[3].getStyleClass().remove("green");
            buttons[3].getStyleClass().add("gray");
        }
        if(change==2 && buttonState[2]==1) {
            buttons[2].getStyleClass().remove("gray");
            buttons[2].getStyleClass().add("red");
            buttons[3].setVisible(true);
        }
        if(change==3 && buttonState[3]==0) {
            buttons[3].getStyleClass().remove("green");
            buttons[3].getStyleClass().add("gray");
        }
        if(change==3 && buttonState[3]==1) {
            buttons[3].getStyleClass().remove("gray");
            buttons[3].getStyleClass().add("green");
        }
    }
}
