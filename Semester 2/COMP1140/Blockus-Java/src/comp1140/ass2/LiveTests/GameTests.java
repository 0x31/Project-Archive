package comp1140.ass2.LiveTests;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Scenes.Game;

/**
 * Created on 1/10/15.
 *
 * The LiveTests package is used as an alternative to JUnit tests to run after the game has ended
 *
 * Why did we not use JUnit tests?
 *      * JUnit doesn't easily allow tests to be run after JavaFX has run. We wanted tests that would run after every
 *        match to confirm that nothing went wrong.
 *      * JUnit tests can't easily be an existing state (eg a finished game) to test
 */
public class GameTests {

    private final Game game;


    /**
     * Instantiate a new set of GameTests
     * @param game
     */
    public GameTests(Game game) {
        this.game = game;
    }

    /**
     * @return true if the board appears to be acting normally
     */
    //@Test
    public boolean testBoard() {
        Board board = game.board;
        boolean assertBool = true;
        assertBool &= (board != null);
        // Should be true no matter what
        assertBool &= !board.legitimateMove("BBAA");
        return assertBool;
    }

    /**
     * @return true if the game did indeed finish and didn't miss any moves
     */
    //@Test
    public boolean testFinishedGame() {
        boolean assertBool = true;
            Board board = game.board;
            for (char piece = 'A'; piece <= 'U'; piece++) {
                for (char orientation = 'A'; orientation <= 'H'; orientation++) {
                    for (char x = 'A'; x <= 'T'; x++) {
                        for (char y = 'A'; y <= 'T'; y++) {
                            String move = "" + piece + orientation + x + y;
                            assertBool &= !board.legitimateMove(move);
                        }
                    }
                }
            }
        return assertBool;
    }

    /**
     * @return true if the PiecePreparer appears to be acting normally
     */
    //@Test
    public boolean testPiecePreparer() {
        Boolean assert1 = (game.piecePreparer != null);
        return assert1;
    }

    /**
     * @return false
     */
    //@Test
    public boolean testIntentionalFail() {
        return false;
    }

    /**
     * @return true
     */
    public boolean testIntentionalPass() {
        return true;
    }
}
