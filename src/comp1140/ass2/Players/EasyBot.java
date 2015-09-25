package comp1140.ass2.Players;

import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class EasyBot implements Player {

    Game parent;

    public EasyBot(Game parent) {
        this.parent = parent;
    }

    @Override
    public boolean makeMove(Piece piece) {
        return false;
    }

    @Override
    public void handleClick(int x, int y) {
        // Alert user it's not their turn?
        // Or do nothing
    }

    @Override
    public void think() {
        parent.transitionMove();
    }
}
