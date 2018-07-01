package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author ***REMOVED*** on 25/09/15, with segments taken from Holly's code
 * Commented by Holly, 14/10/15
 */
public class EasyBot implements Player {

    final Game parent;

    /**
     * Creates new EasyBot
     * @param parent the Game class
     */
    public EasyBot(Game parent) {
        this.parent = parent;
    }

    /**
     * Easybot produces a next move
     * @param string representing the current game of played pieces
     * @return a string which represents the next move
     */
    @Override
    public String think(String string) {
        Board board = new Board(string);
        Panel myPanel = parent.panels[parent.currentColourId];
        Colour colour = parent.playerColours[parent.currentColourId];
        Shape[] shuffled = shuffle(myPanel.activeShapes);
        // Loop through available shapes (shuffled for non-deterministic moves)
        for(Shape shape : shuffled) {
            // For each shape, loop through every orientation
            for(char orientation : new char[] {'A','B','C','D','E','F','G','H'}) {
                // For each shape and orientation, loop through every coordinate
                for(int x = 0; x<20; x++) {
                    for(int y = 0; y<20; y++) {
                        Piece testPiece = new Piece(shape, colour);
                        testPiece.initialisePiece(new Coordinate(x,y), orientation);
                        if(board.legitimateMove(testPiece)) {
                            return testPiece.toString();
                        }
                    }
                }
            }
        }
        return ".";
        //parent.makeMove(".");
    }

    /**
     * Easybot is not human
     * @return false
     */
    @Override
    public boolean isHuman() {
        return false;
    }

    /**
     * Randomly shuffles array of Pieces
     * @param pieces game pieces
     * @return a shuffled array of Pieces, where the same Pieces are in a different order
     */
    private static Shape[] shuffle(ArrayList<Shape> pieces)
    {
        Shape[] ar = new Shape[pieces.size()];
        pieces.toArray(ar);
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Shape a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

}
