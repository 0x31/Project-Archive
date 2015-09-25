package comp1140.ass2;/**
 * Created by Tim on 23/09/2015.
 */

import comp1140.ass2.Scenes.Game;
import comp1140.ass2.Scenes.Menu;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Blokus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage primaryStage;
    Scene menu;
    Scene game;
    Scene instructions;
    Scene options;

    public void toMenu() {
        this.primaryStage.setScene(this.menu);
    }

    public void toGame() {
        this.primaryStage.setScene(this.game);
    }

    public void toInstructions() {
        this.primaryStage.setScene(this.instructions);
    }

    public void toOptions() {
        this.primaryStage.setScene(this.options);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        menu = new Menu(new Group(), 700,700, this);
        game = new Game(new Group(), 700,700, this);

        primaryStage.setTitle("Blokus - Thu09i");
        primaryStage.show();

        toMenu();


        /*
        Group root = new Group();
        Scene scene = new Scene(root, 700, 700);

        int boardSize = 550;
        int gameSize = 690;

        /*
         * Layout inspired by existing Blokus implementations (found by searching 'blokus online' in Google Images)
         *
        VBox vbox = new VBox(); //main window
        //Node main = new PiecePreparerSprite(9,9, gameSize-boardSize,gameSize-boardSize,Color.LIGHTGRAY);    //piecePreparer, SMALL
        Node main = new PiecePreparerSprite(9,9, gameSize,gameSize,Color.LIGHTGRAY);                          //piecePreparer, LARGE

        root.getChildren().add(main);

        primaryStage.setTitle("Blokus - Thu09i");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }
}
