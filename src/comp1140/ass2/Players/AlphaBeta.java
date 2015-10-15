package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Colour;
import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Game.Piece;

import java.util.concurrent.TimeUnit;

/**
 * @author ***REMOVED*** on 15/10/2015
 * heavily based on code by Tim
 *
 * An ALPHABETA implementation of a Blokus bot
 * Key ideas:
 *      1. Sort moves each time and only try top N moves (N=5 for depth=1, N=3 otherwise)
 *      2. Uses HardBot's sorting method
 *      3. Uses complicated Heuristic from HardBot combined with simple score difference
 */
public class AlphaBeta implements Player {

    private boolean DEBUG = false;
    private int myId;

    /**
     * @return false, since AlphaBeta is clearly not a human
     */
    public boolean isHuman() {return false;}

    /**
     * Runs the next depth of alphabeta
     * @param board the board
     * @param depth the current depth (going towards zero)
     * @param starttime to know how long we've going for
     * @param alpha the alpha value for tree pruning
     * @param beta the beta value for tree pruning
     * @return a score for a given move
     */
    private double alphabeta(Board board, int depth, long starttime, double alpha, double beta) {
        long time = (System.nanoTime() - starttime);
        long timeS = TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
        if(depth==0 || timeS>=7 || board.isFinished()) {
            return heuristic(board);
        }
        if (board.getCurrentTurn()==myId) {
            String[] moves = getPotentialMoves(board, 3);
            String boardString = board.toString();
            for(String move : moves) {
                if(move==".") continue;
                Board newBoard = new Board(boardString);
                newBoard.placePiece(move);
                alpha = Math.max(alpha, alphabeta(newBoard,depth-1,starttime, alpha,beta));
                if(beta<=alpha) {
                    break;
                }
            }
            return alpha;
        }
        else {
            String boardString = board.toString();
            Board newBoard = new Board(boardString);
            String[] moves = getPotentialMoves(board, 1);
            newBoard.placePiece(moves[0]);
            beta = Math.min(beta,alphabeta(newBoard,depth-1,starttime, alpha,beta));
            if(beta<=alpha) {
                // There's no loop here, nothing todo
            }
            return beta;
        }
    }

    /**
     * Returns a list of the top potential moves, after ranking and ordering them
     * @param board the board
     * @param n the number of moves to return
     * @return the array of strings to return
     */
    private String[] getPotentialMoves(Board board, int n) {
        String[] moves = new String[n];
        double[] moveScores = new double[n];
        int id = board.getCurrentTurn();
        String boardString = board.toString();
        for(int i=0;i<n;i++) {moveScores[i]=-999999;moves[i]=".";}
        //four nested for loops to generate all possible (legal and illegal) string representations of moves
        for (char shape = 'A'; shape<'V'; shape++) {
            if(!board.getUnplacedPieces()[id][shape-'A']) continue;
            for (char orientation = 'A'; orientation < 'I'; orientation++) {
                for (char x = 'A'; x < 'U'; x++) {
                    for (char y = 'A'; y < 'U'; y++) {
                        String testMove = "" + shape + orientation + x + y;
                        if (board.legitimateMove(testMove)) {
                            //double rank = rankMove(board, testMove);
                            double rank = rankMove2(new Board(boardString), testMove);
                            for(int i=n-1;i>=0;i--) {
                                if(rank>=moveScores[i]) {
                                    for(int j=0;j<i;j++) {
                                        moves[j]=moves[j+1];
                                        moveScores[j]=moveScores[j+1];
                                    }
                                    moves[i]=testMove;
                                    moveScores[i]=rank;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    /**
     * DEPRECATED - create a heatmap of the board to rank moves
     * here for comparison
     * See rankMove2()
     */
    private double rankMove(Board board, String move) {
        //Board newBoard = new Board(board.toString());
        //newBoard.placePiece(move);
        //Piece piece = new Piece(move, Colour.Empty);
        //return piece.shape.getCellNumber();
        int[][] heatmap = new int[24][24];
        Colour[][] grid = board.getGrid();
        Colour myColour = Colour.values()[board.getCurrentTurn()];
        int[][] corners = new int[][] {
                new int[]{-1,-1},
                new int[]{-1,1},
                new int[]{1,-1},
                new int[]{1,1},
        };
        int[][] sides = new int[][] {
                new int[]{0,-1},
                new int[]{0,1},
                new int[]{1,0},
                new int[]{-1,0},
        };
        int[][] surrounding = new int[][] {
                new int[]{-2,-2}, new int[]{-1,-2}, new int[]{0,-2}, new int[]{1,-2}, new int[]{2,-2},
                new int[]{-2,-1},                                                     new int[]{2,-1},
                new int[]{-2,0},                                                      new int[]{2,0},
                new int[]{-2,1},                                                      new int[]{2,1},
                new int[]{-2,2},  new int[]{-1,2},  new int[]{0,2},  new int[]{1,2},  new int[]{2,2}
        };

        for(int i=0;i<20;i++) {
            for(int j=0;j<20;j++) {
                if(grid[i][i]==Colour.Empty) {
                    heatmap[i+2][j+2]+=1;
                    for(int[] corner : corners) heatmap[i+2+corner[0]][j+2+corner[1]]+=3;
                    for(int[] corner : sides) heatmap[i+2+corner[0]][j+2+corner[1]]+=3;
                    for(int[] corner : surrounding) heatmap[i+2+corner[0]][j+2+corner[1]]+=2;
                }
                if(grid[i][i]==myColour) {
                    for(int[] corner : corners) heatmap[i+2+corner[0]][j+2+corner[1]]-=50;
                    for(int[] corner : sides) heatmap[i+2+corner[0]][j+2+corner[1]]-=2;
                }
                else {
                    for(int[] corner : corners) heatmap[i+2+corner[0]][j+2+corner[1]]+=6;
                    for(int[] corner : sides) heatmap[i+2+corner[0]][j+2+corner[1]]+=10;
                }
            }
        }

        Piece piece = new Piece(move, myColour);

        double score = 0;
        for(Coordinate coord : piece.getOccupiedCells()) {
            //score += heatmap[coord.getX()+2][coord.getY()];
            score += heatmap[coord.getY()+2][coord.getX()+2];
        }

        return score;
    }

    /**
     * Returns a score for a given move
     * @param board the board
     * @param move the move to rank
     * @return the score of the move
     */
    private double rankMove2(Board board, String move) {
        int id = board.getCurrentTurn();
        board.placePiece(move);
        return 30 * placedCellCount(board, id) + weightedBoardCoverage(board, id);
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
     * The scoring algorithm
     * @param board the board to score
     * @param playerID the player to score the board for
     * @return the score of the board
     */
    private int weightedBoardCoverage(Board board, int playerID) {
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
     * This is how the Game interfaces with the Bot. When think is called with a Board as a string,
     * AlphaBeta cooks up the next move and returns it as a string
     * @param string the board encoded as a string
     * @return the next move to play, encoded as a string
     */
    @Override
    public String think(String string) {
        Board board = new Board(string);
        myId = board.getCurrentTurn();

        String[] potentials = getPotentialMoves(board, 5);
        //for(String pot :potentials) System.out.println(pot);
        double bestScore = -100000;
        String bestMove  = ".";
        long time = System.nanoTime();
        for(String potential : potentials) {
            if(potential==null) continue;
            Board newBoard = new Board(string);
            newBoard.placePiece(potential);
            double ab = alphabeta(newBoard,4, time, -100000, 10000);
            if(DEBUG) System.out.println("Trying...: "+potential+": "+ab);
            if(ab>bestScore) {
                bestScore = ab;
                bestMove = potential;
            }
        }
        if(DEBUG) System.out.println("Final move:" + bestMove);
        return bestMove;
    }

    /**
     * A way of scoring a board when the depth of the search tree is 0 (a leaf node)
     * @param board the board to score
     * @return the score of the board
     */
    public double heuristic(Board board) {
        int[] score = board.currentScore();
        int myScore = score[myId];
        int h = 20*(myScore - Math.max(score[(myId+1)%4],Math.max(score[(myId+2)%4],score[(myId+3)%4])));
        h+= 30 * placedCellCount(board, myId) + weightedBoardCoverage(board, myId);
        return h;
    }

}
