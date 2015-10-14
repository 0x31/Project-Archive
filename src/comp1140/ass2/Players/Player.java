package comp1140.ass2.Players;

import comp1140.ass2.Scenes.Game;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED*** on 25/09/15.
 */
public interface Player {

    void handleClick(int x, int y);
    String think(String string);
    boolean isHuman();
    void pass(Game parent);

}
