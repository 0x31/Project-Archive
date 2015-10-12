package comp1140.ass2.Players;

import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Scenes.Game;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 */
public interface Player {

    public void handleClick(int x, int y);
    public String think(String string);
    public boolean isHuman();
    public void pass();

}
