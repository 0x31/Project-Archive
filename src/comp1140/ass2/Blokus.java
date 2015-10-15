package comp1140.ass2;

/**
 * Created by Tim on 23/09/2015.
 * Edited by ***REMOVED***, ***REMOVED***, 25/09/2015 - updated FX
 *
 * This is the Main Class
 */

import comp1140.ass2.Scenes.*;
import comp1140.ass2.Scenes.Menu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Blokus extends Application {


    // Prints debug information
    // Should be disabled for final deliverable
    public final boolean DEBUG = false;

    // A time delay for the bots, so appear a bit more human
    // For future: Should only apply to Easy_Bots
    // Or perhaps a *minimum* delay
    public int GAME_SPEED = 3;

    // time limit for bots in seconds. Set to 0 for no time limit.
    public final int BOT_TIME = 10;

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;

    private Scene menu;
    private Scene instructions;
    private Scene options;

    /**
     * Sets the scene to the Menu
     */
    public void toMenu() {
        this.primaryStage.setScene(this.menu);
        setTitle("Blokus: Main Menu");
    }

    /**
     * Creates a new Game and sets the scene to it
     * @param players the ordinal of the players (eg human, easy bot, etc.) chosen for the game
     */
    public void toGame(int[] players) {
        Game game = new Game(new Group(), 700,700, this);
        this.primaryStage.setScene(game);
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
        Scene gameOptions = new GameOptions(new Group(), 700,700, this);
        this.primaryStage.setScene(gameOptions);
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
        primaryStage.getIcons().add( new Image( Blokus.class.getResourceAsStream( "Assets/blokusbg.png" )));

        menu = new Menu(new Group(), 700,700, this);
        instructions = new Instructions(new Group(), 700,700, this);
        options = new Options(new Group(), 700,700, this);

        primaryStage.show();
        toMenu();
    }
}
