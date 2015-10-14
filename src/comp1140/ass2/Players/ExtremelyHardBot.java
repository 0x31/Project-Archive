package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;
import java.util.ArrayList;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 * There's nothing here so no point in marking yet
 */
public class ExtremelyHardBot implements Player {

    private final Game parent;

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


    /**
     * is all broken cause all my functions are side effecting.... :(
     * @param boardStr
     * @return
     */
    @Override
    public String think(String boardStr) {
        Board testBoard = new Board(boardStr);
        int playerID = parent.currentPlayerId;
        //Panel myPanel = parent.panels[parent.currentPlayerId];
        //Colour myColour = parent.playerColours[playerID];
        String bestMove = ".";
        int bestScore = 0;
        int currentScore;

        /**
         * builds all possible moves as a string and tests the scores of all the legal moves
         */
        for (String move : playableMoves(testBoard)) {
            placePieceAndPass(testBoard, move);
            currentScore = getUltimateScore(testBoard, playerID, 0);

            if (currentScore > bestScore) {
                System.out.println("bestMove updated");
                bestScore = currentScore;
                bestMove = move;
            }
        }
        System.out.println(bestMove + " with score " + bestScore);

        return bestMove;
    }

    private int getUltimateScore(Board board, int playerID, int lookahead) {
        Board testBoard = new Board(board.toString());

        if (lookahead == 0) {
            System.out.println("-- ultimateScore: " + scoreBoard(testBoard, playerID));
            //return scoreBoard(board, playerID);
            return placedCellCount(testBoard, playerID);
        } else {
            int bestScore = 0;
            int currentScore;

            for (String move : playableMoves(testBoard)) {
                testBoard = placePieceAndPass(testBoard, move);
                currentScore = scoreBoard(testBoard, playerID);  //replace with getUltimateScore later

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
        return new Board(board.toString() + move + "...");
    }

    /**
     * returns a list of all possible playable moves
     * @param board
     * @return
     */
    private ArrayList<String> playableMoves(Board board) {
        ArrayList<String> moves = new ArrayList<>();
        Board testBoard = new Board(board.toString());
        //four nested for loops to generate all possible (legal and illegal) string representations of moves
        for (char shape = 'A'; shape<'V'; shape++) {
            for (char orientation = 'A'; orientation < 'I'; orientation++) {
                for (char x = 'A'; x < 'U'; x++) {
                    for (char y = 'A'; y < 'U'; y++) {
                        String testMove = "" + shape + orientation + x + y;

                        // if the move is legal, it is added to list
                        if (testBoard.legitimateMove(testMove)) {
                            moves.add(testMove);
                        }
                    }
                }
            }
        }
        return moves;
    }

    private int scoreBoard(Board board, int playerID) {
        Board testBoard = new Board(board.toString());
        return 30*placedCellCount(testBoard, playerID) + weightedBoardCoverage(testBoard, playerID);
    }

    /**
     * Returns how many cells are currently placed on board by this player, using Board
     * @param board
     * @param playerID
     * @return
     */
    private int placedCellCount(Board board, int playerID) {
        Board testBoard = new Board(board.toString());

        int cellCount = 0;
        for (Colour[] cellColourList : testBoard.getGrid()) {
            for (Colour cellColour : cellColourList) {
                if (Colour.values()[playerID] == cellColour) {
                    cellCount += 1;
                }
            }
        }
        System.out.println("-- -- placedCellCount: " + cellCount);
        return cellCount;
    }

    /**
     * Returns how many cells are currently unplaced on board by this player, using Board
     * @param board
     * @param playerID
     * @return
     */
    private int unplacedCellCount(Board board, int playerID) {
        Board testBoard = new Board(board.toString());
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
        Board testBoard = new Board(board.toString());
        int cornerCells = 0;
        for (int x = 0; x < testBoard.getGrid().length; x++) {
            for (int y = 0; y < testBoard.getGrid().length; y++) {
                Coordinate cell = new Coordinate(x,y);
                if(testBoard.cellAt(cell) == Colour.Empty) {
                    boolean touchingSide = false;
                    for (Coordinate sideCell : cell.getSideCells()) {
                        if (testBoard.cellAt(sideCell) == Colour.values()[playerID]) touchingSide = true;
                    }
                    boolean touchingCorner = false;
                    for (Coordinate diagonalCell : cell.getDiagonalCells()) {
                        if (testBoard.cellAt(diagonalCell) == Colour.values()[playerID]) touchingCorner = true;
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
    private int weightedBoardCoverage(Board board, int playerID) {
        Board testBoard = new Board(board.toString());

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

        for (int x = 0; x < testBoard.getGrid().length; x++) {
            for (int y = 0; y < testBoard.getGrid().length; y++) {
                Coordinate cell = new Coordinate(x,y);
                if(testBoard.cellAt(cell) == Colour.Empty) {
                    boolean touchingSide = false;
                    for (Coordinate sideCell : cell.getSideCells()) {
                        if (testBoard.cellAt(sideCell) == Colour.values()[playerID]) touchingSide = true;
                    }
                    boolean touchingCorner = false;
                    for (Coordinate diagonalCell : cell.getDiagonalCells()) {
                        if (testBoard.cellAt(diagonalCell) == Colour.values()[playerID]) touchingCorner = true;
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
    public void pass(Game parent) {
        parent.makeMove(".");
    }
}
