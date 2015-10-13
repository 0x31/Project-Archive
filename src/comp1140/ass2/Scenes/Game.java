package comp1140.ass2.Scenes;

import comp1140.ass2.*;
import comp1140.ass2.Game.*;
import comp1140.ass2.LiveTests.GameTests;
import comp1140.ass2.Players.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***, 25/10/2015
 * Code taken from Holly in an old class
 */
public class Game extends Scene {

    public int currentPlayerId;
    public Player[] players;
    public PiecePreparerSprite piecePreparer;
    public Board board;

    public Panel[] panels;
    public Pane[] panelHeads;
    public Pane[] panelBorders;

    public boolean[] skip = {false, false, false, false};
    public Colour[] playerColours = {Colour.Blue, Colour.Yellow, Colour.Red, Colour.Green};
    private Group root;
    private Group realRoot;
    private Blokus parent;

    public boolean NO_RIGHT_CLICK = false;

    /**
     * Creates a new Game, which is a Scene containing all the required graphics to play Blokus
     * @param realRoot the Group to add things too
     * @param width for super()
     * @param height for super()
     * @param parent to access functions from Blokus (class)
     */
    public Game(Group realRoot, double width, double height, Blokus parent) {
        super(realRoot, width, height, Color.WHITE);
        this.parent = parent;
        // This root is here so that when showing the score, I can blur what's underneath - 'root'.
        Group root = new Group();
        realRoot.getChildren().add(root);
        this.root = root;
        this.realRoot = realRoot;
        getStylesheets().add("comp1140/ass2/Assets/main.css");

        /* Set background image */
        final ImageView imv1 = new ImageView();
        final Image image3 = new Image(Blokus.class.getResourceAsStream("Assets/blokusbg.png"));
        imv1.setImage(image3);
        imv1.setLayoutX(0); imv1.setLayoutY(0);
        imv1.setFitWidth(700);
        imv1.setPreserveRatio(true);
        root.getChildren().add(imv1);



        // MENUBAR
        Pane menubar = new Pane();
        menubar.setMinSize(700,100);
        menubar.setLayoutY(-100); menubar.setLayoutX(0);

        Button button0 = new Button("Pass");
        button0.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            }
        });
        button0.getStyleClass().add("pass");
        button0.setMinSize(80, 20);
        button0.setLayoutX(580);
        button0.setLayoutY(4);
        button0.getStyleClass().add("button1");
        menubar.getChildren().add(button0);

        Button button1 = new Button("Menu");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            }
        });
        button1.getStyleClass().add("pass");
        button1.setMinSize(80, 20);
        button1.setLayoutX(470);
        button1.setLayoutY(4);
        button1.getStyleClass().add("button1");
        menubar.getChildren().add(button1);


        int panelCell = 11;
        int prepCell = 20;

        Pane blueHead = new Pane();
        blueHead.setMinSize(panelCell * 10 + 20, 30);
        blueHead.setLayoutX(10);
        blueHead.setLayoutY(10);
        blueHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        root.getChildren().add(blueHead);

        Panel bluePanel = new Panel(20, 10, panelCell, Colour.Blue, this, true);
        Pane bluePane = new Pane();
        bluePane.setLayoutX(10); bluePane.setLayoutY(30 + 10);
        bluePane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        bluePane.setMinSize(panelCell * 10 + 20, panelCell * 20 + 20);
        bluePanel.setLayoutX(10); bluePanel.setLayoutY(10);
        bluePane.getChildren().addAll(bluePanel);


        Pane yellowHead = new Pane();
        yellowHead.setMinSize(panelCell * 10 + 20, 30);
        yellowHead.setLayoutX(10);
        yellowHead.setLayoutY(30 + 10 + 10 + panelCell * 20 + 20);
        yellowHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        root.getChildren().add(yellowHead);

        Panel yellowPanel = new Panel(20, 10, panelCell, Colour.Yellow, this, true);
        Pane yellowPane = new Pane();
        yellowPane.setLayoutX(10); yellowPane.setLayoutY(30+10+30+10+panelCell*20+20);
        yellowPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        yellowPane.setMinSize(panelCell*10+20, panelCell*20+20);
        yellowPanel.setLayoutX(10); yellowPanel.setLayoutY(10);
        yellowPane.getChildren().add(yellowPanel);


        Pane redHead = new Pane();
        redHead.setMinSize(30, panelCell * 10 + 20);
        redHead.setLayoutX(10+prepCell*5 + 20 + 10);
        redHead.setLayoutY(30 + 10 + 10 + panelCell * 40 + 30+40);
        redHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        root.getChildren().add(redHead);

        Panel redPanel = new Panel(10, 20, panelCell, Colour.Red, this, false);
        Pane redPane = new Pane();
        redPane.setLayoutX(10+prepCell * 5 + 20+10+30); redPane.setLayoutY(700-30-panelCell*10);
        redPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        redPane.setMinSize(panelCell*20+20, panelCell*10+20);
        redPanel.setLayoutX(10); redPanel.setLayoutY(10);
        redPane.getChildren().add(redPanel);


        Pane greenHead = new Pane();
        greenHead.setMinSize(30, panelCell * 10 + 20);
        greenHead.setLayoutX(700-30-panelCell*20-30);
        greenHead.setLayoutY(30 + 10 + 10 + panelCell * 40 + 30+40);
        greenHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        root.getChildren().add(greenHead);

        Panel greenPanel = new Panel(10, 20, panelCell, Colour.Green, this, false);
        Pane greenPane = new Pane();
        greenPane.setLayoutX(700 - 30 - panelCell * 20); greenPane.setLayoutY(700 - 30 - panelCell * 10);
        greenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        greenPane.setMinSize(panelCell * 20 + 20, panelCell * 10 + 20);
        greenPanel.setLayoutX(10); greenPanel.setLayoutY(10);
        greenPane.getChildren().add(greenPanel);

        panels = new Panel[]{bluePanel, yellowPanel, redPanel, greenPanel};
        panelHeads = new Pane[]{blueHead, yellowHead, redHead, greenHead};
        panelBorders = new Pane[]{bluePane,yellowPane,redPane,greenPane};

        int boardCell = 26;
        board = new Board(20, 20, boardCell, Colour.Empty, this);
        Pane boardPane = new Pane();
        boardPane.setLayoutX(panelCell * 10 + 20+10+10); boardPane.setLayoutY(10);
        boardPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        boardPane.setMinSize(boardCell * 20 + 20, boardCell * 20 + 20);
        board.setLayoutX(10); board.setLayoutY(10);
        boardPane.getChildren().add(board);

        piecePreparer = new PiecePreparerSprite(5,5, prepCell, Colour.Empty, this);
        Pane prepPane = new Pane();
        prepPane.setLayoutX(10); prepPane.setLayoutY(700 - 30 - panelCell * 10 + 10);
        prepPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        prepPane.setMinSize(prepCell * 5 + 20, prepCell * 5 + 20);
        piecePreparer.setLayoutX(10); piecePreparer.setLayoutY(10);
        prepPane.getChildren().add(piecePreparer);

        root.getChildren().addAll(bluePane, yellowPane, redPane, greenPane, boardPane, prepPane);


        //Layout
        //VBox vbox = new VBox();

        /*
        final ImageView imv = new ImageView();
        final Image image2 = new Image(Blokus.class.getResourceAsStream("Assets/Blokus.png"));
        imv.setImage(image2);
        imv.setFitHeight(60);
        imv.preserveRatioProperty().setValue(true);
        imv.setLayoutX(700 - 50 * panelCell - 80);
        imv.setLayoutY(1);
        root.getChildren().add(imv);
        */

    }


    /**
     * Starts a new game
     * @param playerCodes represent whether each player is a Human, and Easy Bot, Hard or not playing (for now, only
     *                    Human and Easy are supported)
     */
    public void start(int[] playerCodes) {
        players = new Player[] {null,null,null,null};
        boolean human = false;

        for(int i=0;i<4;i++) {
            if(playerCodes[i]==0) {
                players[i] = new EasyBot(this);
            }
            if(playerCodes[i]==1) {
                players[i] = new Human(this);
                human = true;
            }
            if(playerCodes[i]==2) {
                players[i] = new GreedyBot3(this);
            }
            if(playerCodes[i]==3) {
                players[i] = new GreedyBot4(this);
            }
        }

        if (human == false) {parent.GAME_SPEED = 0;}
        else {parent.GAME_SPEED = 3}

        /*
        Player player0 = new Human(0, this);
        Player player1 = new EasyBot(1, this);
        Player player2 = new EasyBot(2, this);
        Player player3 = new EasyBot(3, this);
        players = new Player[] {player0, player1, player2, player3};
        */
        currentPlayerId = players.length-1; // When we transition go, it will start with player 0

        board.setActive(true);

        // BEGIN!
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                ae -> transitionMove()));
        timeline.play();

    }


    /**
     * The interface for players to make moves
     * @param player a reference to the player calling the method
     * @param piece the desired piece to play
     */

    /**
     * Used to handle passes - and in the future for handling bots that return strings instead of pieces, if that's
     * how the competition will work
     * @param string
     */
    public void makeMove(String string) {
        if(string.equals("")) {
            // DO nothing
            return;
        }
        else if(string==".") {
            board.placePiece(".");

            panels[currentPlayerId].lock(currentPlayer.isHuman());
            skip[currentPlayerId] = true;

            transitionMove();
        }
        else {
            board.placePiece(string);

            int pieceChar = string.charAt(0)-'A';
            Shape shape = Shape.values()[pieceChar];
            panels[currentPlayerId].removePiece(shape);

            transitionMove();
        }
    }

    /**
     * Checks for unplayable Shapes
     * @param currentPlayerId which player to check for
     */
    public void hideBadPieces(int currentPlayerId) {
        Panel panel = panels[currentPlayerId];
        Colour colour = playerColours[currentPlayerId];
        for(Shape shape : panel.shapes) {
            boolean playable = false;
            for(char orientation : new char[] {'A','B','C','D','E','F','G','H'}) {
                for(int x = 0; x<20; x++) {
                    for(int y = 0; y<20; y++) {
                        //Piece testPiece = new Piece(piece.shape, piece.colour);
                        Piece testPiece = new Piece(shape, colour);
                        testPiece.initialisePiece(new Coordinate(x,y), orientation);
                        if(board.legitimateMove(testPiece)) {
                            playable = true;
                        }
                    }
                }
            }
            if(!playable) {
                panel.lockShape(shape);
            } else {
               panel.unlockShape(shape);
            }
        }
    }

    public void updatePanels(int currentPlayerId) {
        int nextPlayerId = (currentPlayerId+1) % players.length;
        for(int i=0; i<panels.length; i++) {
            panelBorders[i].setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
            panelHeads[i].setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        }
        String[] borders = new String[] {"rgba(0,0,0,0.5)","rgba(0,0,0,0.5)","rgba(0,0,0,0.5)","rgba(0,0,0,0.5)"};
        panelBorders[nextPlayerId].setStyle("-fx-background-color: "+borders[nextPlayerId]+", #ffffff;  -fx-background-insets: 0,10;");
        panelHeads[nextPlayerId].setStyle("-fx-background-color: "+borders[nextPlayerId]);
    }

    public Panel currentPanel;
    public Player currentPlayer;

    /**
     * Run between two goes - to set and unset panels and the piecepreparer,
     * to check for game ends, etc...
     */
    public void transitionMove() {
        updatePanels(currentPlayerId);
        piecePreparer.setActive(false);
        panels[currentPlayerId].setActive(false);
        panels[currentPlayerId].temporary = null;
        piecePreparer.removePiece();

        // Check for the end of the game
        if(skip[0]&&skip[1]&&skip[2]&&skip[3]) {
            currentPlayerId = (currentPlayerId+1) % players.length;
            endGame();
            return;
        }

        currentPlayerId = (currentPlayerId+1) % players.length;
        currentPlayer = players[currentPlayerId];
        currentPanel = panels[currentPlayerId];
        if(parent.DEBUG) System.out.print("\rPlayer " + (currentPlayerId + 1) + "'s go!");

        hideBadPieces(currentPlayerId);
        if(panels[currentPlayerId].activeShapes.isEmpty()){
            currentPlayer.pass();
        } else {
            // Gives the player 10 seconds to complete their turn (unless it's a human)
            final CountDownLatch latch = new CountDownLatch(1);
            final String[] move = new String[] {"."};
            Thread t = new Thread(new Runnable() {
                public void run() {
                    move[0] = currentPlayer.think(board.toString());
                    latch.countDown(); // Release await() in the test thread.
                }}); t.start();
            try {
                if(parent.BOT_TIME==0)
                    latch.await();
                else
                    latch.await(parent.BOT_TIME, TimeUnit.SECONDS);
            } catch (InterruptedException e) { e.printStackTrace();}

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(Math.pow(10,parent.GAME_SPEED)),
                    ae -> makeMove(move[0])));
            timeline.play();
        }
    }

    /**
     * Shows the game outcome. Is called only when the game ends.
     */
    public void endGame() {
        if(parent.DEBUG) System.out.println("\rGame finished!");

        closingTests();

        root.setEffect(new GaussianBlur(3));

        End endPane = new End(board.currentScore(), playerColours, parent);
        realRoot.getChildren().add(endPane);
    }






    public void closingTests() {

        boolean allPassed = true;

        GameTests testClass = new GameTests(this);
        ArrayList<String> tests = new ArrayList<>(Arrays.asList(
                new String[] {
                        "testBoard",
                        "testFinishedGame",
                        "testPiecePreparer",
                        "testIntentionalFail",
                        "testIntentionalPass"
        }));

        Class yourClass = GameTests.class;
        for (Method method : yourClass.getMethods()){
            if(tests.contains(method.getName())) {
                try {
                    System.out.print("\u001B[34m" + "Running '" + method.getName() + "()'" + "\u001B[0m");
                    Boolean value = (Boolean) method.invoke(testClass);
                    boolean returnValue = value.booleanValue();
                    if(!(boolean) returnValue) {
                        System.out.println("\r\u001B[31m" + "Running '"+method.getName()+"()' ✖"); allPassed = false;
                    }
                    else System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");
                } catch (InvocationTargetException e) {}
                  catch (IllegalAccessException e) {}
                  catch (SecurityException e) {}
            }
        }

        if(allPassed)
            System.out.println("\n\u001B[34mAll tests passed!" + "\u001B[0m");
        else
            System.out.println("\n\u001B[31mSome tests failed!" + "\u001B[0m");

    }



}
