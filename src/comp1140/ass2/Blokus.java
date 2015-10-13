package comp1140.ass2;

/**
 * Created by Tim on 23/09/2015.
 * Edited by ***REMOVED***, ***REMOVED***, 25/09/2015 - updated FX
 *
 * This is the Main Class
 */

import comp1140.ass2.Scenes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Blokus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage primaryStage;

    Scene menu;
    Game game;
    Scene instructions;
    Scene options;
    Scene gameOptions;

    public final boolean DEBUG = true;
    public int GAME_SPEED = 3;
    public final int BOT_TIME = 10; // but 0 for no time limit


    /**
     * Sets the scene to the Menu
     */
    public void toMenu() {
        this.primaryStage.setScene(this.menu);
        setTitle("Blokus: Main Menu");
    }

    /**
     * Creates a new Game and sets the scene to it
     * @param players
     */
    public void toGame(int[] players) {
        game = new Game(new Group(), 700,700, this);
        this.primaryStage.setScene(this.game);
        setTitle("Blokus: Play!");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> game.start(players)));
        timeline.play();
        //game.start(players);
    }

    /**
     * The rest of these methods set the scene - is seen in toMenu()
     */
    public void toInstructions() {
        this.primaryStage.setScene(this.instructions);
    }

    public void toOptions() {
        this.primaryStage.setScene(this.options);
    }

    public void toGameOptions() {
        gameOptions = new GameOptions(new Group(), 700,700, this);
        this.primaryStage.setScene(this.gameOptions);
    }

    public void setTitle(String title) {
        this.primaryStage.setTitle(title);
    }

    /**
     * This is THE MAIN FUNCTION that creates EVERYTHING. It's pretty important
     * It's pretty important.
     * @param primaryStage required by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setMaxHeight(722);
        primaryStage.setMaxWidth(700);
        primaryStage.setMinHeight(722);
        primaryStage.setMinWidth(700);

        menu = new Menu(new Group(), 700,700, this);
        instructions = new Instructions(new Group(), 700,700, this);
        options = new Options(new Group(), 700,700, this);

        primaryStage.show();
        toMenu();
    }

}
