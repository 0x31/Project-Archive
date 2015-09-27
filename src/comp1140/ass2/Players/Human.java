package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class Human implements Player {

    Game parent;
    int playerId;
    Board board;

    public Human(int playerId, Game parent) {
        this.parent = parent;
        this.playerId = playerId;
    }

    @Override
    public void handleClick(int x, int y) {
        if(parent.piecePreparer.getPiece()!=null) {
            Piece piece = parent.piecePreparer.getPiece().clone();
            piece.setXY(new Coordinate(x, y));
            if(board.legitimateMove(piece)) {
                parent.makeMove(this, piece);
            }
            else {
                System.out.println("Invalid move!");
            }
        }
    }

    @Override
    public void think(Board board) {
        this.board = board;
        // HAHAHA! Humans? Thinking?!
        return; // Nope! Do nothing.
        // i.e. Wait for click();
    }

    @Override
    public void skip() {
        parent.skip[parent.currentPlayer] = true;
        parent.transitionMove();
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
