package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Colour;
import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Game.Panel;
import comp1140.ass2.Scenes.Game;

import java.util.ArrayList;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 * There's nothing here so no point in marking yet
 */
public class GreedyBot4broken implements Player {

    Game parent;

    /**
     * Creates a new ExtremelyHardBot
     * @param parent the Game class
     */
    public GreedyBot4broken(Game parent) {
        this.parent = parent;
    }

    @Override
    public void handleClick(int x, int y) {
    }

    @Override
    public String think(String string) {
        Board board = new Board(string);
        int playerID = parent.currentPlayerId;
        Panel myPanel = parent.panels[parent.currentPlayerId];
        Colour myColour = parent.playerColours[playerID];
        String bestMove = ".";
        int bestScore = 0;
        int currentScore;
        ArrayList<String> moves = playableMoves(board, playerID);

        /**
         * builds all possible moves as a string and tests the scores of all the legal moves
         */

        for(String move : moves) {
            Board testBoard = new Board(move);
            currentScore = scoreMove(testBoard, move, playerID);
            System.out.println("Current score is: " + currentScore);
            if (currentScore > bestScore) {
                System.out.println("Move updated");
                bestMove = move;
                bestScore = currentScore;
            }
            System.out.println("Best score found: " + bestScore);
        }
        System.out.println("Returning move...");
        System.out.println(bestMove);
        return bestMove;
    }

    private ArrayList<String> playableMoves(Board board, int playerID) {
        ArrayList<String> moves = new ArrayList<>();
        for(char shape = 'A'; shape<'V'; shape++) {
            for (char orientation : new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'}) {
                for (char x = 'A'; x < 'U'; x++) {
                    for (char y = 'A'; y < 'U'; y++) {
                        String testMove = "" + shape + orientation + x + y;
                        if (board.legitimateMove(testMove)) {
                            //System.out.println(testMove);
                            moves.add(testMove);
                        }
                    }
                }
            }
        }
        if (moves.size() == 0) {
            System.out.println("no moves found");
            moves.add(".");
        }
        System.out.println("list made");
        return moves;
    }

    public int scoreMove(Board board, String testMove, int playerID) {
        board.placePiece(testMove);
        return 30*placedCellCount(board, playerID) + weightedBoardCoverage(board, playerID);
    }

    /**
     * Returns how many cells are currently placed on board by this player, using Board
     * @param board
     * @param playerID
     * @return
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
        System.out.println("---- Placed cell count: " + cellCount);
        return cellCount;
    }

    /**
     * Returns how many cells are currently unplaced on board by this player, using Board
     * @param board
     * @param playerID
     * @return
     */
    private int unplacedCellCount(Board board, int playerID) {
        return 89 - placedCellCount(board, playerID);
    }

    /**
     * Factors in available corners for future moves, as well as how evenly distributed pieces are throughout the board
     *  this would be achieved by weighting the centre tiles heavier than the outer tiles, as well as tiles further from
     *  starting position
     * @param board
     * @return
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
     * @param board
     * @return
     */
    public int weightedBoardCoverage(Board board, int playerID) {
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
                    if (touchingCorner && !(touchingSide)) weightedCornerCells += Math.abs(homeX - x) + Math.abs(homeY - y);
                }
            }
        }
        System.out.println("---- Weighted corner cell count: " + weightedCornerCells);
        return weightedCornerCells;
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
