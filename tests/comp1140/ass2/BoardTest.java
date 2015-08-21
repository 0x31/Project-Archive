package comp1140.ass2;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by ***REMOVED*** on 21/08/15.
 */
public class BoardTest {

    @Test
    public void checkToString() {
        Board board = new Board("AAAA.AAAABBBBCCCC");
        String is = board.toString();
        String should = "B • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • R • • • • • • • • • • • • • • • • \n"+
                "• • • G • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n"+
                "• • • • • • • • • • • • • • • • • • • \n";
        assertTrue("Error: One of Board.toString or Board() has failed.", is.equals(should));
    }
}
