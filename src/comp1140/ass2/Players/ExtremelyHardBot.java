package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Panel;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 * There's nothing here so no point in marking yet
 */
public class ExtremelyHardBot implements Player {

    Game parent;

    /**
     * Creates a new ExtemelyHardBot
     * @param parent the Game class
     */
    public ExtremelyHardBot(Game parent) {
        this.parent = parent;
    }

    @Override
    public void handleClick(int x, int y) {
    }

    @Override
    public void think(Board board) {
        parent.transitionMove();
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public void confirmPass() {
        parent.transitionMove();
    }
}
