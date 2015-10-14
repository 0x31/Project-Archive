package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Colour;
import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Scenes.Game;

import java.util.ArrayList;

/**
 * @author Tim ***REMOVED***, ***REMOVED*** 13/10/15
 * Commented by Holly, 14/10/15
 * #TODO: Commenting unfinished.
 */
public class GreedyBot4 implements Player {

    /**
     * Creates a new ExtremelyHardBot
     */
    public GreedyBot4() {
    }

    /**
     * Nothing happens if a user clicks on the board, as it is not their turn
     * @param x the clicked cell's x value in grid
     * @param y the clicked cell's y value in grid
     */
    @Override
    public void handleClick(int x, int y) {
    }

    /**
     * GreedyBot4 thinks of the best next move
     * @param string representing the current game of played pieces
     * @return a string representing the next best move
     */
    @Override
    public String think(String string) {
        Board board = new Board(string);
        //int playerID = parent.currentPlayerId;
        int playerID = board.getCurrentTurn();
        //Panel myPanel = parent.panels[parent.currentPlayerId];
        //Colour myColour = parent.playerColours[playerID];
        String bestMove = ".";
        int bestScore = 0;
        int currentScore;

        /**
         * builds all possible moves as a string and tests the scores of all the legal moves to take
         * the move with highest score
         */
        for (String move : playableMoves(board)) {
            Board testBoard = new Board(string);
            currentScore = scoreMove(testBoard, move, playerID);
            if (currentScore > bestScore) {
                bestMove = move;
                bestScore = currentScore;
            }
        }
        return bestMove;
    }

    /**
     * returns a list of all possible playable moves
     * @param board the Board class
     * @return an array of strings representing moves
     */
    private ArrayList<String> playableMoves(Board board) {
        ArrayList<String> moves = new ArrayList<>();

        //four nested for loops to generate all possible (legal and illegal) string representations of moves
        for (char shape = 'A'; shape<'V'; shape++) {
            for (char orientation = 'A'; orientation < 'I'; orientation++) {
                for (char x = 'A'; x < 'U'; x++) {
                    for (char y = 'A'; y < 'U'; y++) {
                        String testMove = "" + shape + orientation + x + y;

                        // if the move is legal, it is added to list
                        if (board.legitimateMove(testMove)) {
                            moves.add(testMove);
                        }
                    }
                }
            }
        }
        return moves;
    }

    private int scoreMove(Board board, String testMove, int playerID) {
        board.placePiece(testMove);
        return 30*placedCellCount(board, playerID) + weightedBoardCoverage(board, playerID);
    }

    /**
     * Returns how many cells are currently placed on board by this player
     * @param board the Board class
     * @param playerID integer representing the current player
     * @return integer cellCount
     */
    private int placedCellCount(Board board, int playerID) {
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
     * Returns how many cells are currently unplaced on board by this player
     * @param board the Board class
     * @param playerID integer representing the current player
     * @return integer count of unplaced cells
     */
    private int unplacedCellCount(Board board, int playerID) {
        return 89 - placedCellCount(board, playerID);
    }

    /**
     *  Factors in available corners for future moves, as well as how evenly distributed pieces are throughout the board
     *  this would be achieved by weighting the centre tiles heavier than the outer tiles, as well as tiles further from
     *  starting position
     * @param board the Board class
     * @return integer
     */
    public int boardCoverage(Board board, int playerID) {
        int cornerCells = 0;
        for (int x = 0; x < board.getGrid().length; x++) {
            for (int y = 0; y < board.getGrid().length; y++) {
                Coordinate cell = new Coordinate(x,y);
                if(board.cellAt(cell) == Colour.Empty) {
                    boolean touchingSide = false;
                    for (Coordinate sideCell : cell.getSideCells()) {
                        if (board.cellAt(sideCell) == Colour.values()[playerID]) touchingSide = true;
                    }
                    boolean touchingCorner = false;
                    for (Coordinate diagonalCell : cell.getDiagonalCells()) {
                        if (board.cellAt(diagonalCell) == Colour.values()[playerID]) touchingCorner = true;
                    }
                    if (touchingCorner && !(touchingSide)) cornerCells++;
                }
            }
        }
        return cornerCells;
    }


    /**
     * Factors in available corners for future moves, as well as how evenly distributed pieces are throughout the board
     *  this would be achieved by weighting the centre tiles heavier than the outer tiles, as well as tiles further from
     *  starting position
     * @param board the Board class
     * @return integer
     */
    private int weightedBoardCoverage(Board board, int playerID) {
        int cornerCells = 0;
        int weightedCornerCells = 0;

        //setting the starting position
        int homeX = 0;
        int homeY = 0;
        switch (playerID) {
            case 0: homeX = 0;
                    homeY = 0;
                    break;

            case 1: homeX = 19;
                    homeY = 0;
                    break;

            case 2: homeX = 19;
                    homeY = 19;
                    break;

            case 3: homeX = 0;
                    homeY = 19;
                    break;
        }

        for (int x = 0; x < board.getGrid().length; x++) {
            for (int y = 0; y < board.getGrid().length; y++) {
                Coordinate cell = new Coordinate(x,y);
                if(board.cellAt(cell) == Colour.Empty) {
                    boolean touchingSide = false;
                    for (Coordinate sideCell : cell.getSideCells()) {
                        if (board.cellAt(sideCell) == Colour.values()[playerID]) touchingSide = true;
                    }
                    boolean touchingCorner = false;
                    for (Coordinate diagonalCell : cell.getDiagonalCells()) {
                        if (board.cellAt(diagonalCell) == Colour.values()[playerID]) touchingCorner = true;
                    }

                    // the product of the distance is chosen in order to give preference to heading to the centre
                    // as well as spreading to the other side of the board as quickly as possible
                    // the bot relies on being blocked eventually in order to start to spread outwards
                    if (touchingCorner && !(touchingSide)) weightedCornerCells += Math.abs(homeX - x) * Math.abs(homeY - y);
                }
            }
        }
        return weightedCornerCells/10;
    }

    /**
     * GreedyBot4 is not human
     * @return false
     */
    @Override
    public boolean isHuman() {
        return false;
    }

    /**
     * Passes when no moves can be made
     * @param parent the Game class
     */
    @Override
    public void pass(Game parent) {
        parent.makeMove(".");
    }
}
