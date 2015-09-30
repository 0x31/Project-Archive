package comp1140.ass2;

import comp1140.ass2.Game.Board;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by ***REMOVED*** on 21/08/15.
 */
public class BoardTest {

    @Test
    public void checkToString() {
        Board board = new Board(
                "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . "
        );
        String is = board.toString();
        String should =
                "B • B B B B • Y Y Y • Y • Y Y Y Y Y • Y \n" +
                "B B • G G G B B Y • Y Y Y • B B B B Y Y \n" +
                "• B B G • B B B Y • • Y B B • • B Y Y • \n" +
                "G G G B B • G Y G G G G • B B Y Y R R • \n" +
                "• • G G B • G Y Y G B • G B Y Y R Y Y Y \n" +
                "• G • • B B G G G • B B B G G Y R R Y Y \n" +
                "G G • B • G B B • • B • • Y Y • R R • • \n" +
                "G • B B B G Y B B B • Y Y Y • Y • • R • \n" +
                "• G • B • G • Y Y Y B B B B B Y • R R R \n" +
                "• G G • B G B B B B Y Y Y Y • Y • • R • \n" +
                "• • G G B G • G • B • B R • Y Y • R Y Y \n" +
                "• G B B B • G G G • B B R R • • Y R • Y \n" +
                "• G G R R B • G R R R R B R • • Y R • Y \n" +
                "• G G R B B G R • R • B B R Y Y Y R Y R \n" +
                "R R R G • B G G G • R B Y Y R R • R Y R \n" +
                "G G R G G G B G B R R R B Y • R R Y R R \n" +
                "G • R G • • B B B R B B B Y Y • R Y Y • \n" +
                "• G G R R R R • R Y Y Y Y B B Y Y R Y • \n" +
                "• G • G • • R • R • Y • • B B • Y R R R \n" +
                "G G • G G G G R R R B B B • • Y Y • • R \n";

        assertTrue("Error: One of Board.toString or Board() has failed.", is.equals(should));
    }
}
