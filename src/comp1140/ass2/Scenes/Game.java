package comp1140.ass2.Scenes;

import comp1140.ass2.*;
import comp1140.ass2.Game.Board;
import comp1140.ass2.Game.Panel;
import comp1140.ass2.Game.PiecePreparerSprite;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by steveb on 12/08/2015.
 */
public class Game extends Scene {

    public Game(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.WHITE);
        parent.setTitle("Blokus: Play!");

        /**primaryStage.setOnCloseRequest(event -> {
            closingTests(didFinish);
        }); */

        int boardSize = 550;
        int gameSize = 690;



        Node bluePanel = new Panel(20, 10, gameSize-boardSize,boardSize/2, new Color(0,0,1,0.1));
        Node yellowPanel = new Panel(20, 10, gameSize-boardSize,boardSize/2, new Color(1,1,0,0.1));
        Node redPanel = new Panel(10,20, boardSize/2,gameSize-boardSize, new Color(1,0,0,0.1));
        Node panelGreen = new Panel(10,20, boardSize/2,gameSize-boardSize, new Color(0,1,0,0.1));
        Node board = new Board(20, 20, boardSize,boardSize, Color.LIGHTGRAY);
        Node piecePreparer = new PiecePreparerSprite(10,10, gameSize-boardSize,gameSize-boardSize,Color.LIGHTGRAY);




        //Layout
        VBox vbox = new VBox(); {
            HBox top = new HBox(); {
                VBox topLeft = new VBox(); {
                    topLeft.getChildren().addAll(bluePanel, yellowPanel);
                    topLeft.setMargin(bluePanel, new Insets(5, 5, 10, 5));
                    topLeft.setMargin(yellowPanel, new Insets(10, 5, 5, 5));
                    top.getChildren().addAll(topLeft, board);
                }
                top.setMargin(board, new Insets(5, 5, 5, 5));
            }
            HBox bottom = new HBox();
            {
                bottom.getChildren().addAll(piecePreparer, redPanel, panelGreen);
                bottom.setMargin(piecePreparer, new Insets(5, 5, 5, 5));
                bottom.setMargin(redPanel, new Insets(5, 10, 5, 5));
                bottom.setMargin(panelGreen, new Insets(5, 5, 5, 10));
                vbox.getChildren().addAll(top, bottom);
                vbox.setMargin(top, new Insets(5, 5, 0, 5));
                vbox.setMargin(bottom, new Insets(0, 5, 5, 5));
            }
        } root.getChildren().add(vbox);

    }




}
