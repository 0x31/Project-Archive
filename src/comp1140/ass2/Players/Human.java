package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 */
public class Human implements Player {

    Game parent;
    Board board;

    /**
     * Gives birth to a new human
     * @param parent the Game class
     */
    public Human(Game parent) {
        this.parent = parent;
    }

    /**
     * Handle clicks on the board
     * @param x the clicked cell's column
     * @param y the clicked cell's row
     */
    @Override
    public void handleClick(int x, int y) {
        if(parent.piecePreparer.getPiece()!=null) {
            Piece piece = parent.piecePreparer.getPiece().clone();
            piece.setXY(new Coordinate(x, y));
            if(board.legitimateMove(piece)) {
                parent.makeMove(this, piece);
            }
            else {
                System.out.print("\rInvalid move!");
            }
        }
    }

    /**
     * The human doesn't think - besides setting the piecePreparer and Panel to active
     * @param board
     */
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

    /**
     * Identifies that this Player uses the PiecePreparer and Panel
     * @return
     */
    @Override
    public boolean isHuman() {
        return true;
    }

    /**
     * Change to have a button requiring clicking before being passed
     * (Only for the first pass)
     */
    @Override
    public void confirmPass() {
        parent.transitionMove();
    }

}
