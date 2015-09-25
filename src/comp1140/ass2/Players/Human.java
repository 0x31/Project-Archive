package comp1140.ass2.Players;

import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class Human implements Player {

    Game parent;

    public Human(Game parent) {
        this.parent = parent;
    }

    @Override
    public boolean makeMove(Piece piece) {
        return parent.board.placePiece(piece);
    }

    @Override
    public void handleClick(int x, int y) {
        Piece piece = parent.piecePreparer.getPiece();
        if(piece!=null) {
            piece.setXY(new Coordinate(x, y));
            if(makeMove(piece)) {
                parent.transitionMove();
            }
        }
    }

    @Override
    public void think() {
        // HAHAHA! Humans? Thinking?!
        return; // Nope! Do nothing.
        // i.e. Wait for click();
    }
}
