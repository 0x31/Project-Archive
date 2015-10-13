package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

import java.util.ArrayList;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 * There's nothing here so no point in marking yet
 */
public class ExtremelyHardBot implements Player {

    Game parent;

    /**
     * Creates a new ExtremelyHardBot
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
        Board board = new Board(string);
        int playerID = parent.currentPlayerId;
        //Panel myPanel = parent.panels[parent.currentPlayerId];
        //Colour myColour = parent.playerColours[playerID];
        String bestMove = ".";
        int bestScore = 0;
        int currentScore;


        /**
         * builds all possible moves as a string and tests the scores of all the legal moves
         */
        for (String move : playableMoves(board)) {
            Board testBoard = placePieceAndPass(board, move);
            currentScore = getUltimateScore(testBoard, playerID, 1);
            if (currentScore > bestScore) {
                bestMove = move;
                bestScore = currentScore;
            }
        }
        return bestMove;
    }

    private int getUltimateScore(Board board, int playerID, int lookahead) {
        if (lookahead == 0) {
            return scoreBoard(board, playerID);
        } else {
            int bestScore = 0;
            int currentScore;

            for (String move : playableMoves(board)) {
                Board testBoard = placePieceAndPass(board, move);
                currentScore = scoreBoard(testBoard, playerID);

                if (currentScore > bestScore) {
                    bestScore = currentScore;
                }
            }
            return bestScore;
        }
    }

    /**
     * places piece and then inserts three passes for opponents
     */
    private Board placePieceAndPass(Board board, String move) {
        Board retBoard = new Board(board.toString() + move + "...");
        return retBoard;
    }

    /**
     * returns a list of all possible playable moves
     * @param board
     * @return
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

    public int scoreBoard(Board board, int playerID) {
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
        return weightedCornerCells/10;
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
