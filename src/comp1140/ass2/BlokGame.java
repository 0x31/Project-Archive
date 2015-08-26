package comp1140.ass2;

import java.nio.charset.Charset;

/**
 * Created by steveb on 12/08/2015.
 */
public class BlokGame {

    /**
     * Parse a string representing a game state and determine whether it is legitimate.  The game may be in progress
     * (ie incomplete).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return True if the string represents a legitimate game state, according to the encoding defined in the
     * assignment description and the rules of the game.
     */
    public static boolean legitimateGame(String game) {
        /* FIXME */
        String[] moves = Board.splitMoves(game);
        Board board = new Board("");
        for(String move : moves) {
            if(!board.legitimateMove(move)) {
                return false;
            }
            board.placePiece(move);
        }
        return true;
    }

    /**
     * Parse a string representing a game state and return a score for the game.  The game may be in progress
     * (ie incomplete), in which case score should reflect the score at that time (if no further moves were made).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return An array of four integers reflecting the score for the game.   The scores should be given in the playing
     * order specified in the rules of the game, and the scores should be made according to the rules.
     */
    public static int[] scoreGame(String game) {
        /* FIXME */
        int[] scores = new int[4];
        Board board = new Board(game);
        for(int i=0;i<4;i++) {
            for(Piece piece : board.getUnplacedPieces()[i]) {
                if(piece!=null) {
                    scores[i]-=piece.shape.getCellNumber();
                }
            }
            if(scores[i]==0) {
                if(board.getLastMove()[i]) scores[i]+=20;
                else scores[i] += 15;
            }
        }
        return scores;
    }

    /**
     * Parse a string representing a game state and return a valid next move.  If no move is possible, return a pass ('.')
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return A four-character string representing the next move.
     */
    public static String makeMove(String game) {
        /* FIXME */
        Board board = new Board(game);
        for(char piece = 'A'; piece<='U'; piece++) {
            for(char orientation = 'A'; orientation<='H'; orientation++) {
                for(char x = 'A'; x<='T'; x++) {
                    for(char y = 'A'; y<='T'; y++) {
                        String move = "" + piece + orientation + x + y;
                        if (board.legitimateMove(move)) return move;
                    }
                }
            }
        }
        return ".";
    }
}
