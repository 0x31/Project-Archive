package comp1140.ass2;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Scenes.Game;
import javafx.application.Application;
import javafx.scene.Group;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ***REMOVED*** on 21/08/15.
 */
public class GameFXTest {

    /**
    Blokus blokus;

    @Test
    public void checkToString() throws InterruptedException {
        // Initialise Java FX

        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                blokus = new Blokus();
                blokus.launch(new String[0]);
                //Application.launch(Blokus.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(500);

        Group root = new Group();
        Game game = new Game(root, 700, 700, blokus);
        game.start(new int[] {2,2,2,2});
    }
    */
}
