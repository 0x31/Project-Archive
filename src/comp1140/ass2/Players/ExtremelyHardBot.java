package comp1140.ass2.Players;

import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class ExtremelyHardBot implements Player {

    Game parent;

    public ExtremelyHardBot(Game parent) {
        this.parent = parent;
    }

    @Override
    public boolean makeMove(Piece piece) {
        return false;
    }

    @Override
    public void handleClick(int x, int y) {

    }

    @Override
    public void think() {
        parent.transitionMove();
    }
}
