package comp1140.ass2.Scenes;

import comp1140.ass2.*;
import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.GridSprite;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Game.PiecePreparerSprite;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.junit.Test;


/**
 * Created by steveb on 12/08/2015.
 */
public class Game extends Scene {

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

    public Board board;
    public PiecePreparerSprite piecePreparerSprite;

    public void watchMouse() {


        board.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            }
        });


    }


    public Game(Group root, double width, double height, Blokus parent) {
        super(root, width, height);


        /**primaryStage.setOnCloseRequest(event -> {
            closingTests(didFinish);
        }); */

        int boardSize = 550;
        int gameSize = 690;



        /*
         * Layout inspired by existing Blokus implementations (found by searching 'blokus online' in Google Images)
         */
        VBox vbox = new VBox();

        HBox top = new HBox();
        VBox topLeft = new VBox();
        Node topLeftTop = new GridSprite(20, 10, gameSize-boardSize,boardSize/2, new Color(0,0,1,0.1));
        Node topLeftBottom = new GridSprite(20, 10, gameSize-boardSize,boardSize/2, new Color(1,1,0,0.1));
        topLeft.getChildren().addAll(topLeftTop, topLeftBottom);
        topLeft.setMargin(topLeftTop, new Insets(5, 5, 10, 5));
        topLeft.setMargin(topLeftBottom, new Insets(10, 5, 5, 5));
        board = new Board(20, 20, boardSize,boardSize, Color.LIGHTGRAY);
        top.getChildren().addAll(topLeft, board);
        top.setMargin(board, new Insets(5, 5, 5, 5));


        HBox bottom = new HBox();
        Node piecePreparerSprite = new PiecePreparerSprite(9,9, gameSize-boardSize,gameSize-boardSize,Color.LIGHTGRAY);
        Node bottomMiddle = new GridSprite(10,20, boardSize/2,gameSize-boardSize, new Color(1,0,0,0.1));
        Node bottomRight = new GridSprite(10,20, boardSize/2,gameSize-boardSize, new Color(0,1,0,0.1));
        bottom.getChildren().addAll(piecePreparerSprite, bottomMiddle, bottomRight);
        bottom.setMargin(piecePreparerSprite, new Insets(5,5,5,5));
        bottom.setMargin(bottomMiddle, new Insets(5,10,5,5));
        bottom.setMargin(bottomRight, new Insets(5, 5, 5, 10));

        vbox.getChildren().addAll(top, bottom);
        vbox.setMargin(top, new Insets(5, 5, 0, 5));
        vbox.setMargin(bottom, new Insets(0, 5, 5, 5));

        root.getChildren().add(vbox);

    }

    public void closingTests(boolean finished) {

        boolean allPassed = true;

        // testBoard
        System.out.print("\u001B[34m" + "Running 'testBoard()'" + "\u001B[0m");
        if(!testBoard()) {System.out.println("\r\u001B[31m" + "Running 'testBoard()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testFinishedGame
        System.out.print("\u001B[34m" + "Running 'testFinishedGame()'" + "\u001B[0m");
        if(finished && !testFinishedGame()) {System.out.println("\r\u001B[31m" + "Running 'testFinishedGame()' ✖"); allPassed = false;}
        else if(!finished) System.out.println("\u001B[34m" + " ✖"  + "\u001B[0m");
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testpiecePreparerSprite
        System.out.print("\u001B[34m" + "Running 'testpiecePreparerSprite()'" + "\u001B[0m");
        if(!testpiecePreparerSprite()) {System.out.println("\r\u001B[31m" + "Running 'testpiecePreparerSprite()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testIntentionalFail
        System.out.print("\u001B[34m" + "Running 'testIntentionalFail()'" + "\u001B[0m");
        if(!testIntentionalFail()) {System.out.println("\r\u001B[31m" + "Running 'testIntentionalFail()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testIntentionalPass
        System.out.print("\u001B[34m" + "Running 'testIntentionalPass()'" + "\u001B[0m");
        if(!testIntentionalPass()) {System.out.println("\r\u001B[31m" + "Running 'testIntentionalPass()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");


        if(allPassed) {
            System.out.println("\n\u001B[34mAll tests passed!" + "\u001B[0m");
        }
        else {
            System.out.println("\n\u001B[31mSome tests failed!" + "\u001B[0m");
        }

        /*
        JUnitCore junit = new JUnitCore();
        Request test = Request.method(BlokGame.class,"test");
        Result result = junit.run(ClosingTest);
        List<Failure> failures = result.getFailures();
        if(failures.isEmpty()) {
            System.out.println("\u001B[32mAll closing tests passed!");
        }
        else {
            for (Failure failure : failures) {
                System.out.println(failure.toString());
            }
        }
        */
    }


    @Test
    public boolean testBoard() {
        boolean assertBool = true;
        assertBool &= (board != null);
        // Should be true no matter what
        assertBool &= board.legitimateMove("BBAA")==false;
        return assertBool;
    }
    @Test
    public boolean testFinishedGame() {
        // Adjust for remaining pieces arrays
        boolean assertBool = true;
        for(char piece = 'A'; piece<='U'; piece++) {
            for(char orientation = 'A'; orientation<='H'; orientation++) {
                for(char x = 'A'; x<='T'; x++) {
                    for(char y = 'A'; y<='T'; y++) {
                        String move = "" + piece + orientation + x + y;
                        assertBool &= !board.legitimateMove(move);
                    }
                }
            }
        }
        return assertBool;
    }
    @Test
    public boolean testpiecePreparerSprite() {
        Boolean assert1 = (piecePreparerSprite != null);
        return assert1;
    }
    @Test
    public boolean testIntentionalFail() {
        return false;
    }
    @Test
    public boolean testIntentionalPass() {
        return true;
    }


}
