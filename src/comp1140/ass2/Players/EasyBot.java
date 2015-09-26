package comp1140.ass2.Players;

import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Game.Panel;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nosha on 25/09/15.
 */
public class EasyBot implements Player {

    Game parent;
    Panel myPanel;

    public EasyBot(Game parent) {
        this.parent = parent;
    }

    @Override
    public boolean makeMove(Piece piece) {
        return parent.board.placePiece(piece);
    }

    @Override
    public void handleClick(int x, int y) {
        // Alert user it's not their turn?
        // Or do nothing
    }

    @Override
    public void think() {
        myPanel = parent.panels[parent.currentPlayer];
        for(Piece piece : shuffle(myPanel.pieces)) {
            for(char orientation : new char[] {'A','B','C','D','E','F','G','H'}) {
                for(int x = 0; x<20; x++) {
                    for(int y = 0; y<20; y++) {
                        Piece testPiece = new Piece(piece.shape, piece.colour);
                        testPiece.initialisePiece(new Coordinate(x,y), orientation);
                        if(makeMove(testPiece)) {
                            parent.transitionMove();
                            myPanel.removePiece(piece);
                            return;
                        }
                    }
                }
            }
        }
        parent.transitionMove();
    }

    static Piece[] shuffle(ArrayList<Piece> pieces)
    {
        Piece[] ar = new Piece[pieces.size()];
        pieces.toArray(ar);
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Piece a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

}
