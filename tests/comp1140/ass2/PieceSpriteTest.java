package comp1140.ass2;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class PieceSpriteTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        int cellDim = 10;

        primaryStage.setTitle("Board");
        Group root = new Group();
        Scene  scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);

        //CellSprite cell = new CellSprite(5,Colour.Blue, 100, 100);
        //root.getChildren().add(cell.getShape());

        Piece myPiece = new Piece(Shape.G, Colour.Red);
        myPiece.shape.initialisePiece(new Coordinate(100, 100), 'C');
        PieceSprite mySprite = new PieceSprite(20, myPiece);
        mySprite.AddShape(root);

        Piece hisPiece = new Piece(Shape.G, Colour.Blue);
        hisPiece.shape.initialisePiece(new Coordinate(0, 0), 'A');
        PieceSprite hisSprite = new PieceSprite(20, hisPiece);
        hisSprite.AddShape(root);

        //@ToDo sort out why they are one pixel apart
        Piece yourPiece = new Piece(Shape.S, Colour.Green);
        yourPiece.shape.initialisePiece(new Coordinate(120,100), 'A');
        PieceSprite yourSprite = new PieceSprite(20, yourPiece);
        yourSprite.AddShape(root);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
