package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.paint.Color;

/**
 * Created by nosha on 25/09/15.
 */
public final class Panel extends GridSprite {
    public Panel(int col, int row, int width, int height, Colour color, Game parent) {
        super(col, row, width, height, color, parent);
    }
}
