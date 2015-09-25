package comp1140.ass2;

import comp1140.ass2.Game.Board;
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

        assertTrue("ScoreGameTest string",
                Arrays.equals(Board.splitMoves("RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . BBBK . . . AACF"),
                        new String[]{"RCCC","RBTA","SARR","SBCR","SHDD","TBQD","RAOO","PBFP","LBJH","LHLH","LGNN","TAGN","JDKI","JBRA","OHIM","UAHK","KDGJ","KAPH","JARK","JAFG","UADG","UALA","UASH","QAGD","QDCL","PCIC","MEQE","MEBL","DDKL","MDRE","TGJQ","OHID","EBFA","QDON","PAIR","KBGT","IBMM","SHMO","KDDR","RCDK","GCFO","NAPR","QCCQ","IDAH","FHKQ","IHRP","FATN","LDAD","NBIP","OHJR","DBEM","FFFB","PBMF","BASN","AAHN","DBBP","THMC","FGTM","BBSD","AAME","OBRB","EBNJ",".","BBOF", "MHFC","CBJI",".",".","HANR","DAHD",".",".","CBMT","AAGH",".",".","BBBK",".",".",".","AACF"}));

    }

    @Test (expected = IllegalArgumentException.class)
    public void illegitimateMove() {
        Board.splitMoves(".AABB.AA");
    }


}
