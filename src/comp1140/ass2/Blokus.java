package comp1140.ass2;/**
 * Created by Tim on 23/09/2015.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Blokus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 700, 700);

        int boardSize = 550;
        int gameSize = 690;

        /*
         * Layout inspired by existing Blokus implementations (found by searching 'blokus online' in Google Images)
         */
        VBox vbox = new VBox(); //main window
        //Node main = new PiecePreparerSprite(9,9, gameSize-boardSize,gameSize-boardSize,Color.LIGHTGRAY);    //piecePreparer, SMALL
        Node main = new PiecePreparerSprite(9,9, gameSize,gameSize,Color.LIGHTGRAY);                          //piecePreparer, LARGE

        root.getChildren().add(main);

        primaryStage.setTitle("Blokus - Thu09i");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
