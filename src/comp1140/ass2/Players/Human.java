package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 * Commented by Holly, 14/10/15.
 */
public class Human implements Player {

    private final Game parent;
    private Board board;

    /**
     * Gives birth to a new human
     * @param parent the Game class
     */
    public Human(Game parent) {
        this.parent = parent;
    }

    /**
     * Handle clicks on the board: gets piece when clicked, makes move if legitimate
     * @param x the clicked cell's column
     * @param y the clicked cell's row
     */
    @Override
    public void handleClick(int x, int y) {
        if(parent.piecePreparer.getPiece()!=null) {
            Piece piece = new Piece(parent.piecePreparer.getPiece());
            piece.setXY(new Coordinate(x, y));
            if(board.legitimateMove(piece)) {
                parent.makeMove(piece.toString());
            }
            else {
                System.out.print("\rInvalid move!");
            }
        }
    }

    /**
     * The human doesn't think - besides setting the piecePreparer and Panel to active
     * @param string represents the board as a string
     */
    @Override
    public String think(String string) {
        this.board = new Board(string);
        parent.piecePreparer.setActive(true);
        parent.currentPanel.setActive(true);

        // Wait for click()
        return "";
    }

    /**
     * Identifies if the Player uses the PiecePreparer and Panel
     * @return true if player has used them
     */
    @Override
    public boolean isHuman() {
        return true;
    }

    /**
     * #TODO: Change to have a button requiring clicking before being passed (Only for the first pass)
     */
    @Override
    public void pass(Game parent) {
        parent.makeMove(".");
    }

}
