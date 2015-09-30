package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class Human implements Player {

    Game parent;
    int playerId;
    Panel myPanel;
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
        parent.piecePreparer.setActive(true);
        parent.currentPanel.setActive(true);
        //if(stuck())
        //    parent.makeMove(".");
        // HAHAHA! Humans? Thinking?!
        return; // Nope! Do nothing.
        // i.e. Wait for click();
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public void confirmPass() {
        parent.transitionMove();
    }

}
