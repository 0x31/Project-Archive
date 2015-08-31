package comp1140.ass2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Tim on 31/08/2015.
 */
public class PiecePreparer extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        final int CELL_DIM = 20;

        primaryStage.setTitle("Square");
        final Group root = new Group();
        //StackPane root2 = new StackPane();

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);



        final Piece myPiece = new Piece(Shape.G, Colour.Blue);
        myPiece.shape.initialisePiece(new Coordinate(150, 150), 'A');

        final Queue<PieceSprite> queue = new LinkedBlockingQueue<>();

        //@ToDo Have clickable buttons
        //@ToDo Have pretty border

        scene.setOnKeyTyped(event -> {
            if (((int) event.getCharacter().charAt(0)) == 97) {
                myPiece.shape.movePiece(new Coordinate(-CELL_DIM, 0), 0, false);
            }
            if (event.getCharacter().equals("d")) {
                myPiece.shape.movePiece(new Coordinate(CELL_DIM, 0), 0, false);
            }
            if (event.getCharacter().equals("w")) {
                myPiece.shape.movePiece(new Coordinate(0, -CELL_DIM), 0, false);
            }
            if (event.getCharacter().equals("s")) {
                myPiece.shape.movePiece(new Coordinate(0, CELL_DIM), 0, false);
            }
            if (event.getCharacter().equals(".")) {
                myPiece.shape.movePiece(1, false);
            }
            if (event.getCharacter().equals(",")) {
                myPiece.shape.movePiece(3, false);
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100),
                ae -> {
                    PieceSprite mySprite = new PieceSprite(CELL_DIM, myPiece);
                    if (! queue.isEmpty()) {
                        PieceSprite myOldSprite = queue.remove();
                        myOldSprite.RemoveShape(root);
                    }
                    mySprite.AddShape(root);
                    queue.add(mySprite);
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        primaryStage.show();

    }
}
