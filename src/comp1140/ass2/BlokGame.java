package comp1140.ass2;

import static comp1140.ass2.Board.splitMoves;
import static comp1140.ass2.Board.legitimateMove;

/**
 * Created by steveb on 12/08/2015.
 *@author Holly ***REMOVED***, ***REMOVED***
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
        String[] moves = splitMoves(game);

        for(String move : moves)
            while ((legitimateMove (String move))==true ) {
                continue;
            }
            return false;
    }

    /**
     * Parse a string representing a game state and return a score for the game.  The game may be in progress
     * (ie incomplete), in which case score should reflect the score at that time (if no further moves were made).
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return An array of four integers reflecting the score for the game.   The scores should be given in the playing
     * order specified in the rules of the game, and the scores should be made according to the rules.
     */
    public static int[] scoreGame(String game) {
        int score [];
        score = new int[4];
        score[0] = 0;
        score[1] = 0;
        score[2] = 0;
        score[3] = 0;

        unplacedPiecesBlue


        return score;
    }

    /**
     * Parse a string representing a game state and return a valid next move.  If no move is possible, return a pass ('.')
     * @param game A string representing the state of the game, as described in the assignment description.
     * @return A four-character string representing the next move.
     */
    public static String makeMove(String game) {
        static String[] possiblemoves = {"AAAA" "ABAA" }

        return ".";
    }

}
