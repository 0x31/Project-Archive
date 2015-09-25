package comp1140.ass2.Game;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;import java.lang.Override;import java.lang.System;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class CellSprite extends Rectangle{
    //private int cellDim;
    //private Colour colour;
 //   int xPos;
//    int yPos;

    Colour color;
    Color fill;
    PieceSprite pieceSprite = null; //is part of this pieceSprite


    //Necessary constructors, won't be used
    public CellSprite() {
        super();
    }
    public CellSprite (double width, double height) {super (width, height); }
    public CellSprite (double width, double height,Paint fill) {super (width, height, fill);}
    public CellSprite (double x, double y, double width, double height) {super (x, y, width, height);}

    public CellSprite (double width, double height, Colour color, PieceSprite pieceSprite) {
        super(width, height);
        this.color = color;
        this.setFill(getFillFromPlayer(color));
        this.pieceSprite = pieceSprite;

        CellSprite dummyCell = this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("-- Cell was clicked");
                pieceSprite.isClicked(dummyCell);
            }
        });
    }

    private Color getFillFromPlayer(Colour color) {
        if      (color == Colour.Blue)      return Color.BLUE;
        else if (color == Colour.Red)       return Color.RED;
        else if (color == Colour.Green)     return Color.GREEN;
        else if (color == Colour.Yellow)    return Color.YELLOW;
        return Color.GREY;
    }
}
