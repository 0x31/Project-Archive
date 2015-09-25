package comp1140.ass2;

import org.junit.Test;

/**
 * Created by nosha on 25/09/15.
 */
public class BlokusEndTest {
    /*

    public void closingTests(boolean finished) {

        boolean allPassed = true;

        // testBoard
        System.out.print("\u001B[34m" + "Running 'testBoard()'" + "\u001B[0m");
        if(!testBoard()) {System.out.println("\r\u001B[31m" + "Running 'testBoard()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testFinishedGame
        System.out.print("\u001B[34m" + "Running 'testFinishedGame()'" + "\u001B[0m");
        if(finished && !testFinishedGame()) {System.out.println("\r\u001B[31m" + "Running 'testFinishedGame()' ✖"); allPassed = false;}
        else if(!finished) System.out.println("\u001B[34m" + " ✖"  + "\u001B[0m");
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testpiecePreparerSprite
        System.out.print("\u001B[34m" + "Running 'testpiecePreparerSprite()'" + "\u001B[0m");
        if(!testpiecePreparerSprite()) {System.out.println("\r\u001B[31m" + "Running 'testpiecePreparerSprite()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testIntentionalFail
        System.out.print("\u001B[34m" + "Running 'testIntentionalFail()'" + "\u001B[0m");
        if(!testIntentionalFail()) {System.out.println("\r\u001B[31m" + "Running 'testIntentionalFail()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");

        // testIntentionalPass
        System.out.print("\u001B[34m" + "Running 'testIntentionalPass()'" + "\u001B[0m");
        if(!testIntentionalPass()) {System.out.println("\r\u001B[31m" + "Running 'testIntentionalPass()' ✖"); allPassed = false;}
        else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");


        if(allPassed) {
            System.out.println("\n\u001B[34mAll tests passed!" + "\u001B[0m");
        }
        else {
            System.out.println("\n\u001B[31mSome tests failed!" + "\u001B[0m");
        }

        /*
        JUnitCore junit = new JUnitCore();
        Request test = Request.method(BlokGame.class,"test");
        Result result = junit.run(ClosingTest);
        List<Failure> failures = result.getFailures();
        if(failures.isEmpty()) {
            System.out.println("\u001B[32mAll closing tests passed!");
        }
        else {
            for (Failure failure : failures) {
                System.out.println(failure.toString());
            }
        }
        *
}


    @Test
    public boolean testBoard() {
        boolean assertBool = true;
        assertBool &= (board != null);
        // Should be true no matter what
        assertBool &= board.legitimateMove("BBAA")==false;
        return assertBool;
    }
    @Test
    public boolean testFinishedGame() {
        // Adjust for remaining pieces arrays
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
    public boolean testpiecePreparerSprite() {
        Boolean assert1 = (piecePreparerSprite != null);
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

     */

}
