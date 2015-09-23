package comp1140.ass2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Holly ***REMOVED*** on 23/09/2015.
 */

//just creating a sample player panel in lieu of a working board to hopefully show some transitions
public class Player extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sample Player Panel");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);

        CellState greengrid [][] = new CellState[21][8];
        for(int i=0; i<21; i++) {
            for (int j = 0; j < 8; j++) {
                greengrid[i][j] = CellState.Green;
            }
        }

        //(TO DO #4: Default player panels showing all pieces in correct colour.)
        //can parse game string again ti pick out coloured pieces, or use UnplacedPieces?

        GridPane greenplayer = new GridSprite(21, 8, 210, 80, new Color (1,1,0,0.1)).toFX();
        root.getChildren().add(greenplayer);

        // TO DO #1: Show whose turn.

        private void showTurn() {
            int player = board.getCurrentTurn() //similar to playerId in Board... can make public?
            switch (player) {
                case 1:
                    greenplayer.setOpacity(1.0);
                    break;
                case 2:
                    redplayer.setOpacity(1.0); //correspondingly coloured player panels
                    break;
                case 3:
                    yellowplayer.setOpacity(1.0);
                    break;
                case 4:
                    blueplayer.setOpacity(1.0);
                    break;
            }
        }

        //TO DO #2: Click event, piece goes to PiecePreparer.


        pieces.setOnMouseEntered(new EventHandler<MouseEvent>(){
        public void handle (MouseEvent selectPiece){
        //piece is selected for PiecePreparer to handle
        }
    }

        //TO DO #3: Grey out placed pieces.

            //when put together, when a piece placed,

        private void greyPlacedPieces(String game) {



                }
            }
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//*/