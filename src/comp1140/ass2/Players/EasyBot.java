package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nosha on 25/09/15.
 */
public class EasyBot implements Player {

    Game parent;
    Panel myPanel;
    int playerId;

    public EasyBot(int playerId, Game parent) {
        this.parent = parent;
        this.playerId = playerId;
    }

    @Override
    public void handleClick(int x, int y) {
        // Alert user it's not their turn?
        // Or do nothing
    }

    @Override
    public void think(Board board) {
        myPanel = parent.panels[parent.currentPlayer];
        Colour colour = parent.playerColours[parent.currentPlayer];
        for(Shape shape : shuffle(myPanel.shapes)) {
            for(char orientation : new char[] {'A','B','C','D','E','F','G','H'}) {
                parent.piecePreparer.addShape(shape, colour, orientation);
                for(int x = 0; x<20; x++) {
                    for(int y = 0; y<20; y++) {
                        //Piece testPiece = new Piece(piece.shape, piece.colour);
                        Piece testPiece = new Piece(shape, colour);
                        testPiece.initialisePiece(new Coordinate(x,y), orientation);
                        if(parent.board.legitimateMove(testPiece)) {
                            parent.makeMove(this, testPiece);
                            return;
                        }
                    }
                }
            }
        }
        skip();
    }

    public void skip() {
        parent.skip[parent.currentPlayer] = true;
        parent.transitionMove();
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    static Shape[] shuffle(ArrayList<Shape> pieces)
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
