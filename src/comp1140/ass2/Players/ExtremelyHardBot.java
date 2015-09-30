package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Panel;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class ExtremelyHardBot implements Player {

    Game parent;
    int playerId;
    Panel myPanel;

    public ExtremelyHardBot(int playerId, Game parent) {
        this.parent = parent;
        this.playerId = playerId;
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
