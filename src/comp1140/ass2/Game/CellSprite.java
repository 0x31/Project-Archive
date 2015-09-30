package comp1140.ass2.Game;


import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;import java.lang.Override;import java.lang.System;

/**
 * @author Tim ***REMOVED***, ***REMOVED***
 * Later edited by ***REMOVED***:
 *  - images
 *  - OnMouseEntered/Exited
 *  - Changed to a Pane
 */
public class CellSprite extends Pane {

    Color fill;
    Object parent = null;

    /**
     *
     * @param width  cell dimension
     * @param height cell dimension
     * @param color  the colour of the desired cell image
     * @param parent the piece which the cell will belong to
     */
    public CellSprite (double width, double height, Colour color, Object parent) {
        super();
        super.setMinSize(width, height);
        this.parent = parent;

        if (color == Colour.Empty) {
            this.setStyle("-fx-background-image: url('comp1140/ass2/Assets/empty.png'); -fx-background-size: 100%;");
        }
        //if(color==Colour.Empty) {this.setStyle("-fx-background-color: lightgray");}
        if (color == Colour.Red) {
            this.setStyle("-fx-background-image: url('comp1140/ass2/Assets/red.png'); -fx-background-size: 100%;");
        }
        if (color == Colour.Green) {
            this.setStyle("-fx-background-image: url('comp1140/ass2/Assets/green.png'); -fx-background-size: 100%;");
        }
        if (color == Colour.Blue) {
            this.setStyle("-fx-background-image: url('comp1140/ass2/Assets/blue.png'); -fx-background-size: 100%;");
        }
        if (color == Colour.Yellow) {
            this.setStyle("-fx-background-image: url('comp1140/ass2/Assets/yellow.png'); -fx-background-size: 100%;");
        }

        //this.getStyleClass().add(color.name());
        eventWatcher();

    }

    /**
     * Collection of event handlers
     */
    private void eventWatcher() {

        CellSprite dummyCell = this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Cell was clicked");
                if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("-- click was rightClick");
                    if (parent instanceof PieceSprite) {
                        //System.out.println("Cell rightClicked");
                        ((PieceSprite) parent).isRightClicked(dummyCell);
                    }
                    if(parent instanceof GridSprite) {
                        //System.out.println("Cell rightClicked");
                        ((GridSprite) parent).isRightClicked(dummyCell);
                    }
                } else {
                    System.out.println("-- click was not rightClick");
                    if (parent instanceof PieceSprite) {
                        ((PieceSprite) parent).isClicked(dummyCell);
                    }
                    if (parent instanceof GridSprite) {
                        ((GridSprite) parent).isClicked(dummyCell);
                    }
                }
            }
        });
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (parent instanceof GridSprite) {
                    ((GridSprite) parent).isHovered(dummyCell);
                }
                if (parent instanceof PieceSprite) {
                    ((PieceSprite) parent).isHovered(dummyCell);
                }
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Check for Board's preview
            }
        });
    }

    /**
     * Used to translate ass2.Game.Colour into JavaFX.Color
     * @param color
     * @return a color from the JavaFX module
     */
    private Color getFillFromPlayer(Colour color) {
        if      (color == Colour.Blue)      return Color.BLUE;
        else if (color == Colour.Red)       return Color.RED;
        else if (color == Colour.Green)     return Color.GREEN;
        else if (color == Colour.Yellow)    return Color.GOLD;
        return Color.LIGHTGRAY;
    }

}
