package comp1140.ass2.Scenes;

import comp1140.ass2.*;
import comp1140.ass2.Game.*;
import comp1140.ass2.Players.EasyBot;
import comp1140.ass2.Players.Human;
import comp1140.ass2.Players.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by steveb on 12/08/2015.
 */
public class Game extends Scene {

    public int currentPlayer;
    public Player[] players;
    public PiecePreparerSprite piecePreparer;
    public Board board;
    public Panel[] panels;
    public boolean[] skip = {false, false, false, false};
    public Colour[] playerColours = {Colour.Blue, Colour.Yellow, Colour.Red, Colour.Green};

    // All players have to do everything through this interface
    public void makeMove(Player player, Piece piece) {
        board.placePiece(piece);
        //panels[currentPlayer].removePiece(piece);
        panels[currentPlayer].removePiece(piece.shape);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                ae -> transitionMove()));
        timeline.play();
    }

    public void transitionMove() {
        piecePreparer.setActive(false);
        panels[currentPlayer].setActive(false);
        panels[currentPlayer].temporary = null;
        piecePreparer.removePiece();
        if(skip[0]&&skip[1]&&skip[2]&&skip[3]) {
            endGame();
            return;
        }

        currentPlayer = (currentPlayer+1) % players.length;
        skip[currentPlayer] = false;
        System.out.println("Player " + (currentPlayer+1)+"'s go!");
        if(players[currentPlayer].isHuman()) {
            piecePreparer.setActive(true);
            panels[currentPlayer].setActive(true);
        }
        // Eventually, the bots should get passed a clone of board instead of board itself, so they can't do anything to board
        // directly.
        // Also, think about not giving them access to parent
        //players[currentPlayer].think(board.clone());
        players[currentPlayer].think(board);
    }

    public void endGame() {
    }

    public Game(Group root, double width, double height, Blokus parent) {
        super(root, width, height, Color.WHITE);
        getStylesheets().add("comp1140/ass2/Assets/main.css");


        /**primaryStage.setOnCloseRequest(event -> {
         closingTests(didFinish);
         }); */

        int boardSize = 520;
        int gameSize = 670;


        Panel bluePanel = new Panel(20, 10, gameSize - boardSize, boardSize / 2, Colour.Blue, this, true);
        Panel yellowPanel = new Panel(20, 10, gameSize - boardSize, boardSize / 2, Colour.Yellow, this, true);
        Panel redPanel = new Panel(10, 20, boardSize / 2, gameSize - boardSize, Colour.Red, this, false);
        Panel panelGreen = new Panel(10, 20, boardSize / 2, gameSize - boardSize, Colour.Green, this, false);
        panels = new Panel[]{bluePanel, yellowPanel, redPanel, panelGreen};

        board = new Board(20, 20, boardSize, boardSize, Colour.Empty, this);
        piecePreparer = new PiecePreparerSprite(5,5, gameSize - boardSize, gameSize - boardSize, Colour.Empty, this);

        // MENUBAR
        HBox menuBar = new HBox();
        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setFitHeight(70);
        imv.preserveRatioProperty().setValue(true);
        imv.setLayoutX(10);
        imv.setLayoutY(1);
        Button button0 = new Button("Pass");
        button0.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            }
        });
        button0.setMinSize(160, 40);
        button0.setLayoutX(640 - button0.getMinWidth() / 2);
        button0.setLayoutY(20);
        menuBar.setMinSize(700, 70);
        menuBar.getChildren().addAll(imv, button0);


        //Layout
        VBox vbox = new VBox();
        {
            HBox top = new HBox();
            {
                VBox topLeft = new VBox();
                {
                    topLeft.getChildren().addAll(bluePanel, yellowPanel);
                    //topLeft.setMargin(bluePanel, new Insets(5, 5, 5, 5));
                    //topLeft.setMargin(yellowPanel, new Insets(5, 5, 5, 5));
                }
                top.getChildren().addAll(topLeft, board);
                //top.setMargin(board, new Insets(5, 5, 5, 5));
                top.setMargin(board, new Insets(5, 50, 5, 5));
            }
            HBox bottom = new HBox();
            {
                bottom.getChildren().addAll(piecePreparer, redPanel, panelGreen);
                //bottom.setMargin(piecePreparer, new Insets(5, 5, 5, 5));
                //bottom.setMargin(redPanel, new Insets(5, 10, 5, 5));
                //bottom.setMargin(panelGreen, new Insets(5, 5, 5, 10));
            }
            vbox.getChildren().addAll(menuBar, top, bottom);
            //vbox.setMargin(top, new Insets(5, 5, 0, 5));
            //vbox.setMargin(menuBar, new Insets(1, 1, 1, 1));
            //vbox.setMargin(bottom, new Insets(0, 5, 5, 5));
        }
        root.getChildren().add(vbox);


    }

    public void start() {

        Player player0 = new EasyBot(0, this);
        Player player1 = new EasyBot(1, this);
        Player player2 = new EasyBot(2, this);
        Player player3 = new EasyBot(3, this);
        players = new Player[] {player0, player1, player2, player3};
        currentPlayer = players.length-1; // When we transition go, it will start with player 0

        board.setActive(true);

        // BEGIN!
        transitionMove();

    }




}
