package comp1140.ass2;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ***REMOVED*** on 21/08/15.
 */
public class SplitMovesTest {

    @Test
    public void leigitimateMove() {
        assertTrue("Error for: Empty",
                Arrays.equals(Board.splitMoves(""),
                        new String[]{}));

        assertTrue("Error for: '.'",
                Arrays.equals(Board.splitMoves("."),
                        new String[]{"."}));

        assertTrue("Error for: 'AABBCCDD'",
                Arrays.equals(Board.splitMoves("AABBCCDD"),
                        new String[]{"AABB", "CCDD"}));

        assertTrue("Error for: Long Game",
                Arrays.equals(Board.splitMoves("AABB.CCDD...EEFFGGHH.IIJJ..KKLLMMNNOOPP.....QQRRSSTT"),
                        new String[]{"AABB", ".", "CCDD", ".", ".", ".", "EEFF", "GGHH", ".", "IIJJ", ".", ".", "KKLL", "MMNN", "OOPP", ".", ".", ".", ".", ".", "QQRR", "SSTT"}));

    }

    @Test (expected = IllegalArgumentException.class)
    public void illegitimateMove() {
        Board.splitMoves(".AABB.AA");
    }


}
