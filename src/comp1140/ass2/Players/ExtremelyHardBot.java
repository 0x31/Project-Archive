package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 * There's nothing here so no point in marking yet
 */
public class ExtremelyHardBot implements Player {

    Game parent;
    int playerID = parent.currentPlayerId;

    /**
     * Creates a new ExtemelyHardBot
     * @param parent the Game class
     */
    public ExtremelyHardBot(Game parent) {
        this.parent = parent;
    }

    @Override
    public void handleClick(int x, int y) {
    }

    @Override
    public String think(String string) {
        return ".";
    }

    public int scoreMove(Board board, Piece testPiece, int playerID) {
        return 0;
    }

    /**
     * Returns how many cells are currently placed on board by this player, using Board
     * @param board
     * @param playerID
     * @return
     */
    private int altPlacedCellCount(Board board, int playerID) {
        int cellCount = 0;
        for (Colour[] cellColourList : board.getGrid()) {
            for (Colour cellColour : cellColourList) {
                if (Colour.values()[playerID] == cellColour) {
                    cellCount += 1;
                }
            }
        }
        return cellCount;
    }

    /**
     * Returns how many cells are currently unplaced on board by this player, using Board
     * @param board
     * @param playerID
     * @return
     */
    private int altUnplacedCellCount(Board board, int playerID) {
        return 89 - altPlacedCellCount(board, playerID);
    }

    /**
     * Returns how many cells are currently placed on board by this player, using Panels
     * @param board
     * @return
     */
    public int placedCells(Panel myPanel) {
        return 89 - remainingCells(myPanel);
    }

    /**
     * Returns how many unplaced cells are remaining, using Panels
     * @param board
     * @param playerID
     * @return
     */
    public int remainingCells(Panel myPanel) {
        int cellCount = 0;
        for (Shape shape : myPanel.activeShapes)
        {
            cellCount += shape.getCellNumber();
        }
        return cellCount;
    }

    /**
     * Factors in available corners for future moves, as well as how evenly distributed pieces are throughout the board
     *  this would be achieved by weighting the centre tiles heavier than the outer tiles, as well as tiles further from
     *  starting position
     * @param board
     * @return
     */
    public int boardCoverage(Board board, int playerID) {
        return 0;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public void pass() {
        parent.makeMove(".");
    }
}
