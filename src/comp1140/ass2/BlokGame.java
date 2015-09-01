package comp1140.ass2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.charset.Charset;

/**
 * Created by steveb on 12/08/2015.
 */
public class BlokGame extends Application {

    /**
     * Parse a string representing a game state and determine whether it is legitimate.  The game may be in progress
     * (ie incomplete).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return True if the string represents a legitimate game state, according to the encoding defined in the
     * assignment description and the rules of the game.
     */
    public static boolean legitimateGame(String game) {
        /* FIXME */
        String[] moves = Board.splitMoves(game);
        Board board = new Board("");
        for(String move : moves) {
            if(!board.legitimateMove(move)) {
                return false;
            }
            board.placePiece(move);
        }
        return true;
    }

    /**
     * Parse a string representing a game state and return a score for the game.  The game may be in progress
     * (ie incomplete), in which case score should reflect the score at that time (if no further moves were made).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return An array of four integers reflecting the score for the game.   The scores should be given in the playing
     * order specified in the rules of the game, and the scores should be made according to the rules.
     */
    public static int[] scoreGame(String game) {
        /* FIXME */
        int[] scores = new int[4];
        Board board = new Board(game);
        for(int i=0;i<4;i++) {
            for(Piece piece : board.getUnplacedPieces()[i]) {
                if(piece!=null) {
                    scores[i]-=piece.shape.getCellNumber();
                }
            }
            if(scores[i]==0) {
                if(board.getLastMove()[i]) scores[i]+=20;
                else scores[i] += 15;
            }
        }
        return scores;
    }

    /**
     * Parse a string representing a game state and return a valid next move.  If no move is possible, return a pass ('.')
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return A four-character string representing the next move.
     */
    public static String makeMove(String game) {
        /* FIXME */
        Board board = new Board(game);
        for(char piece = 'A'; piece<='U'; piece++) {
            for(char orientation = 'A'; orientation<='H'; orientation++) {
                for(char x = 'A'; x<='T'; x++) {
                    for(char y = 'A'; y<='T'; y++) {
                        String move = "" + piece + orientation + x + y;
                        if (board.legitimateMove(move)) return move;
                    }
                }
            }
        }
        return ".";
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
        VBox vbox = new VBox();

        HBox top = new HBox();
        VBox topLeft = new VBox();
        Node topLeftTop = new GridSprite(20, 10, gameSize-boardSize,boardSize/2, new Color(0,0,1,0.1)).toFX();
        Node topLeftBottom = new GridSprite(20, 10, gameSize-boardSize,boardSize/2, new Color(1,1,0,0.1)).toFX();
        topLeft.getChildren().addAll(topLeftTop, topLeftBottom);
        topLeft.setMargin(topLeftTop, new Insets(5,5,10,5));
        topLeft.setMargin(topLeftBottom, new Insets(10, 5, 5, 5));
        Node board = new GridSprite(20, 20, boardSize,boardSize, Color.LIGHTGRAY,
                "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . BBBK . . . AACF"
        ).toFX();
        top.getChildren().addAll(topLeft, board);
        top.setMargin(board, new Insets(5,5,5,5));

        HBox bottom = new HBox();
        Node bottomLeft = new GridSprite(5,5, gameSize-boardSize,gameSize-boardSize,Color.LIGHTGRAY).toFX();
        Node bottomMiddle = new GridSprite(10,20, boardSize/2,gameSize-boardSize, new Color(1,0,0,0.1)).toFX();
        Node bottomRight = new GridSprite(10,20, boardSize/2,gameSize-boardSize, new Color(0,1,0,0.1)).toFX();
        bottom.getChildren().addAll(bottomLeft, bottomMiddle, bottomRight);
        bottom.setMargin(bottomLeft, new Insets(5,5,5,5));
        bottom.setMargin(bottomMiddle, new Insets(5,10,5,5));
        bottom.setMargin(bottomRight, new Insets(5,5,5,10));

        vbox.getChildren().addAll(top, bottom);
        vbox.setMargin(top, new Insets(5, 5, 0, 5));
        vbox.setMargin(bottom, new Insets(0, 5, 5, 5));

        root.getChildren().add(vbox);

        primaryStage.setTitle("Blokus - Thu9i");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}
