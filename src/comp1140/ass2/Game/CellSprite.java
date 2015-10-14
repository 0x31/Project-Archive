package comp1140.ass2.Game;


import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

/**
 * @author Tim ***REMOVED***, ***REMOVED***
 * @author ***REMOVED***, ***REMOVED***, updating:
 *  - images
 *  - OnMouseEntered/Exited
 *  - Changed to a Pane
 *
 *  CellSprite represents a Cell using a JavaFX Pane
 */
public class CellSprite extends Pane {

    private Object parent = null;

    /**
     * Creates a new CellSprite to represent a cell on the board
     * @param width  the width for displaying the cell
     * @param height the height for displaying the cell
     * @param color  the colour of the desired cell
     * @param parent the piece which the cell belongs to
     */
    public CellSprite (double width, double height, Colour color, Object parent) {
        super();
        super.setMinSize(width, height);
        this.parent = parent;
        String format = "-fx-background-image: url('comp1140/ass2/Assets/%s.png'); -fx-background-size: 100%%;";
        this.setStyle(String.format(format, color.name()));
        eventWatcher();
    }

    /**
     * Watch for mouse clicks and mouse hovers to pass back to the Parent class
     */
    private void eventWatcher() {

        CellSprite dummyCell = this;
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (parent instanceof PieceSprite)
                    ((PieceSprite) parent).isRightClicked(dummyCell);
                if(parent instanceof GridSprite)
                    ((GridSprite) parent).isRightClicked(dummyCell);
            } else {
                if (parent instanceof PieceSprite)
                    ((PieceSprite) parent).isClicked(dummyCell);
                if (parent instanceof GridSprite)
                    ((GridSprite) parent).isClicked(dummyCell);
            }
        });
        this.setOnMouseEntered(event -> {
            if (parent instanceof GridSprite)
                ((GridSprite) parent).isHovered(dummyCell);
            if (parent instanceof PieceSprite)
                ((PieceSprite) parent).isHovered(dummyCell);
        });
    }

}
