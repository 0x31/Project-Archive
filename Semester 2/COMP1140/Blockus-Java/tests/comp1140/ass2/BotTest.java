package comp1140.ass2;


import comp1140.ass2.Game.Board;
import comp1140.ass2.Players.HardBot;
import comp1140.ass2.Players.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tim on 14/10/15.
 *
 * Tests that GreedyBot4 works
 */
public class BotTest {
    @Test
    public void extremelyHardBotTest() {


        String boardStr =
                "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . ";
        Board myBoard = new Board(boardStr);

        Player myBot = new HardBot();

        assertTrue("Something was returned", myBot.think(boardStr) instanceof String);
        assertTrue("Move was a legit move", myBoard.legitimateMove(myBot.think(boardStr)));
    }
}
