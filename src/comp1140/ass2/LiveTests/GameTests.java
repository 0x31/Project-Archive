package comp1140.ass2.LiveTests;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Scenes.Game;
import org.junit.Test;

/**
 * Created by nosha on 1/10/15.
 */
public class GameTests {
    Game game;


    public GameTests(Game game) {
        this.game = game;
    }

    @Test
    public boolean testBoard() {
        Board board = game.board;
        boolean assertBool = true;
        assertBool &= (board != null);
        // Should be true no matter what
        assertBool &= board.legitimateMove("BBAA")==false;
        return assertBool;
    }
    @Test
    public boolean testFinishedGame() {
        Board board = game.board;
        boolean assertBool = true;
        for(char piece = 'A'; piece<='U'; piece++) {
            for(char orientation = 'A'; orientation<='H'; orientation++) {
                for(char x = 'A'; x<='T'; x++) {
                    for(char y = 'A'; y<='T'; y++) {
                        String move = "" + piece + orientation + x + y;
                        assertBool &= !board.legitimateMove(move);
                    }
                }
            }
        }
        return assertBool;
    }
    @Test
    public boolean testPiecePreparer() {
        Boolean assert1 = (game.piecePreparer != null);
        return assert1;
    }
    @Test
    public boolean testIntentionalFail() {
        return false;
    }
    @Test
    public boolean testIntentionalPass() {
        return true;
    }


}
