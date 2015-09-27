package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public interface Player {

    public void handleClick(int x, int y);
    public void think(Board board);
    public void skip();
    public boolean isHuman();

}
