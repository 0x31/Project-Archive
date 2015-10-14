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
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    public int currentColourId;
    public int currentPlayerId;
    //public Player[] players;
    private final ArrayList<Player> players = new ArrayList<>();
    public final PiecePreparerSprite piecePreparer;
    public final Board board;

    public final Panel[] panels;
    private final Pane[] panelHeads;
    private final Pane[] panelBorders;
    private final Pane[] panelAll;
    private final Label[] labelScore;
    private final Label[] labelPlayer;
    private final Label[] labelName;

    private int turn = 0;

    private final boolean[] skip = {false, false, false, false};
    public final Colour[] playerColours = {Colour.Blue, Colour.Yellow, Colour.Red, Colour.Green};
    private final Group root;
    private final Group realRoot;
    private final Blokus parent;

    public final boolean NO_RIGHT_CLICK = false;

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

        final Font fontSmall = Font.loadFont(Blokus.class.getResourceAsStream("Assets/PressStart2P.ttf"),8);
        final Font fontLarge = Font.loadFont(Blokus.class.getResourceAsStream("Assets/PressStart2P.ttf"),16);

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
        menubar.setMinSize(700, 100);
        menubar.setLayoutY(00); menubar.setLayoutX(0);

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
        root.getChildren().add(menubar);


        int panelCell = 11;
        int prepCell = 20;

        Pane blue = new Pane();
        blue.setLayoutX(10);
        blue.setLayoutY(10);
        Pane blueHead = new Pane();
        blueHead.setMinSize(panelCell * 10 + 20, 30);
        blueHead.setMaxSize(panelCell * 10 + 20, 30);
        blueHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label blueLabel = new Label(" 00");
        blueLabel.setFont(fontLarge);
        blueLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        blueLabel.setLayoutX(75);
        blueLabel.setLayoutY(12);
        blueHead.getChildren().add(blueLabel);
        Label blueName = new Label("Player 1");
        blueName.setFont(fontSmall);
        blueName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        blueName.setLayoutX(10);
        blueName.setLayoutY(12);
        blueHead.getChildren().add(blueName);
        Label bluePlayer = new Label("Human");
        bluePlayer.setFont(fontSmall);
        bluePlayer.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        bluePlayer.setLayoutX(10);
        bluePlayer.setLayoutY(20);
        blueHead.getChildren().add(bluePlayer);
        Panel bluePanel = new Panel(20, 10, panelCell, Colour.Blue, this, true);
        Pane bluePane = new Pane();
        bluePane.setLayoutX(0);
        bluePane.setLayoutY(30);
        bluePane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        bluePane.setMinSize(panelCell * 10 + 20, panelCell * 20 + 20);
        bluePanel.setLayoutX(10); bluePanel.setLayoutY(10);
        bluePane.getChildren().addAll(bluePanel);
        blue.getChildren().add(blueHead);
        blue.getChildren().add(bluePane);
        root.getChildren().add(blue);


        Pane yellow = new Pane();
        yellow.setLayoutX(10);
        yellow.setLayoutY(30 + 10 + 10 + panelCell * 20 + 20);
        Pane yellowHead = new Pane();
        yellowHead.setMinSize(panelCell * 10 + 20, 30);
        yellowHead.setMaxSize(panelCell * 10 + 20, 30);
        yellowHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label yellowLabel = new Label(" 00");
        yellowLabel.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 16));
        yellowLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        yellowLabel.setLayoutX(75);
        yellowLabel.setLayoutY(12);
        yellowHead.getChildren().add(yellowLabel);
        Label yellowName = new Label("Player 2");
        yellowName.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 8));
        yellowName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        yellowName.setLayoutX(10);
        yellowName.setLayoutY(12);
        yellowHead.getChildren().add(yellowName);
        Label yellowPlayer = new Label("Human");
        yellowPlayer.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 8));
        yellowPlayer.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        yellowPlayer.setLayoutX(10);
        yellowPlayer.setLayoutY(20);
        yellowHead.getChildren().add(yellowPlayer);
        Panel yellowPanel = new Panel(20, 10, panelCell, Colour.Yellow, this, true);
        Pane yellowPane = new Pane();
        yellowPane.setLayoutX(0); yellowPane.setLayoutY(30);
        yellowPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        yellowPane.setMinSize(panelCell * 10 + 20, panelCell * 20 + 20);
        yellowPanel.setLayoutX(10); yellowPanel.setLayoutY(10);
        yellowPane.getChildren().add(yellowPanel);
        yellow.getChildren().add(yellowHead);
        yellow.getChildren().add(yellowPane);
        root.getChildren().add(yellow);


        Pane red = new Pane();
        red.setLayoutX(10+prepCell*5 + 20 + 10);
        red.setLayoutY(30 + 10 + 10 + panelCell * 40 + 30 + 40);
        Pane redHead = new Pane();
        redHead.setMinSize(30, panelCell * 10 + 20);
        redHead.setMaxSize(30, panelCell * 10 + 20);
        redHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label redLabel = new Label(" \n0\n0");
        redLabel.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 16));
        redLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        redLabel.setLayoutX(12);
        redLabel.setLayoutY(75);
        redHead.getChildren().add(redLabel);
        Label redName = new Label("P\nl\na\ny\ne\nr\n \n3");
        redName.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 8));
        redName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        redName.setLayoutX(10);
        redName.setLayoutY(10);
        redHead.getChildren().add(redName);
        Label redPlayer = new Label("H\nu\nm\na\nn");
        redPlayer.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 8));
        redPlayer.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        redPlayer.setLayoutX(22);
        redPlayer.setLayoutY(10);
        redHead.getChildren().add(redPlayer);
        red.getChildren().add(redHead);
        Panel redPanel = new Panel(10, 20, panelCell, Colour.Red, this, false);
        Pane redPane = new Pane();
        redPane.setLayoutX(30); redPane.setLayoutY(0);
        redPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        redPane.setMinSize(panelCell * 20 + 20, panelCell * 10 + 20);
        redPanel.setLayoutX(10); redPanel.setLayoutY(10);
        redPane.getChildren().add(redPanel);
        red.getChildren().add(redPane);
        root.getChildren().add(red);


        Pane green = new Pane();
        green.setLayoutX(700-30-panelCell*20-30);
        green.setLayoutY(30 + 10 + 10 + panelCell * 40 + 30+40);
        Pane greenHead = new Pane();
        greenHead.setMinSize(30, panelCell * 10 + 20);
        greenHead.setLayoutX(0);
        greenHead.setLayoutY(0);
        greenHead.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
        Label greenLabel = new Label(" \n0\n0");
        greenLabel.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 16));
        greenLabel.setTextFill(Color.valueOf("rgba(255,255,255,0.8)"));
        greenLabel.setLayoutX(12);
        greenLabel.setLayoutY(75);
        greenHead.getChildren().add(greenLabel);
        Label greenName = new Label("P\nl\na\ny\ne\nr\n \n4");
        greenName.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 8));
        greenName.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        greenName.setLayoutX(10);
        greenName.setLayoutY(10);
        greenHead.getChildren().add(greenName);
        Label greenPlayer = new Label("H\nu\nm\na\nn");
        greenPlayer.setFont(Font.font("Press Start 2P", FontWeight.NORMAL, 8));
        greenPlayer.setTextFill(Color.valueOf("rgba(255,255,255,0.4)"));
        greenPlayer.setLayoutX(22);
        greenPlayer.setLayoutY(10);
        greenHead.getChildren().add(greenPlayer);
        green.getChildren().add(greenHead);
        Panel greenPanel = new Panel(10, 20, panelCell, Colour.Green, this, false);
        Pane greenPane = new Pane();
        greenPane.setLayoutX(30); greenPane.setLayoutY(0);
        greenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
        greenPane.setMinSize(panelCell * 20 + 20, panelCell * 10 + 20);
        greenPanel.setLayoutX(10); greenPanel.setLayoutY(10);
        greenPane.getChildren().add(greenPanel);
        green.getChildren().add(greenPane);
        root.getChildren().add(green);


        panels = new Panel[]{bluePanel, yellowPanel, redPanel, greenPanel};
        panelHeads = new Pane[]{blueHead, yellowHead, redHead, greenHead};
        panelBorders = new Pane[]{bluePane,yellowPane,redPane,greenPane};
        panelAll = new Pane[]{blue, yellow, red, green};
        labelScore = new Label[]{blueLabel,yellowLabel,redLabel,greenLabel};
        labelPlayer = new Label[]{bluePlayer,yellowPlayer,redPlayer,greenPlayer};
        labelName = new Label[]{blueName,yellowName,redName,greenName};

        for(int i=0;i<4;i++){
            labelScore[i].setVisible(false);
            labelName[i].setVisible(false);
            labelPlayer[i].setVisible(false);
        }

        int boardCell = 26;
        board = new Board(20, 20, boardCell, Colour.Empty, this);
        Pane boardPane = new Pane();
        boardPane.setLayoutX(panelCell * 10 + 20 + 10 + 10); boardPane.setLayoutY(10);
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

        root.getChildren().addAll(boardPane, prepPane);


    }


    /**
     * Starts a new game
     * @param playerCodes represent whether each player is a Human, and Easy Bot, Hard or not playing (for now, only
     *                    Human and Easy are supported)
     */
    public void start(int[] playerCodes) {
        //players = new Player[] {null,null,null,null};

        boolean humans = false;
        for(int i=0;i<4;i++) {
            if(playerCodes[i]==0) {
                //players[i] = new EasyBot(this);
            }
            if(playerCodes[i]==1) {
                humans = true;
                //players[i] = new Human(this);
                players.add(new Human(this));
                if(i<2)
                    labelPlayer[i].setText("Human");
                else
                    labelPlayer[i].setText("Human".replace("","\n").trim());
            }
            if(playerCodes[i]==2) {
                //players[i] = new EasyBot(this);
                players.add(new EasyBot(this));
                if(i<2)
                    labelPlayer[i].setText("Easy");
                else
                    labelPlayer[i].setText("Eeasy".replace("","\n").trim());
            }
            if(playerCodes[i]==3) {
                //players[i] = new HardBot(this);
                players.add(new HardBot());
                if(i<2)
                    labelPlayer[i].setText("Hard");
                else
                    labelPlayer[i].setText("Hard".replace("","\n").trim());
            }
        }
        if(!humans)
            parent.GAME_SPEED=0;
        else
            parent.GAME_SPEED=3;

        for(int i=0;i<4;i++){
            labelScore[i].setVisible(true);
            labelName[i].setVisible(true);
            labelPlayer[i].setVisible(true);
        }

        if(players.size()==3) {
            labelPlayer[3].setText("S\nh\na\nr\ne\nd");
            labelName[3].setText("");
            labelPlayer[3].setLayoutX(15);
            labelScore[3].setVisible(false);
        }
        if(players.size()==2) {
            labelPlayer[2].setText("");
            labelName[2].setText("P\nl\na\ny\ne\nr\n \n1");
            labelName[2].setLayoutX(15);
            labelPlayer[3].setText("");
            labelName[3].setText("P\nl\na\ny\ne\nr\n \n2");
            labelName[3].setLayoutX(15);
            labelScore[2].setVisible(false);
            labelScore[3].setVisible(false);
        }
        if(players.size()==1) {
            labelScore[0].setVisible(false);
            labelPlayer[1].setText("");
            labelName[1].setText("Player 1");
            labelName[1].setLayoutY(15);
            labelScore[1].setVisible(false);
            labelPlayer[2].setText("");
            labelName[2].setText("P\nl\na\ny\ne\nr\n \n1");
            labelName[2].setLayoutX(15);
            labelScore[2].setVisible(false);
            labelPlayer[3].setText("");
            labelName[3].setText("P\nl\na\ny\ne\nr\n \n1");
            labelName[3].setLayoutX(15);
            labelScore[3].setVisible(false);
        }

        /*
        Player player0 = new Human(0, this);
        Player player1 = new EasyBot(1, this);
        Player player2 = new EasyBot(2, this);
        Player player3 = new EasyBot(3, this);
        players = new Player[] {player0, player1, player2, player3};
        */
        currentColourId = 3; // When we transition go, it will start with player 0

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
        }
        else if(string==".") {
            board.placePiece(".");

            panels[currentColourId].lock(currentPlayer.isHuman());
            skip[currentColourId] = true;

            transitionMove();
        }
        else {
            board.placePiece(string);

            int pieceChar = string.charAt(0)-'A';
            Shape shape = Shape.values()[pieceChar];
            panels[currentColourId].removePiece(shape);

            transitionMove();
        }
    }

    /**
     * Checks for unplayable Shapes
     * @param currentPlayerId which player to check for
     */
    private void hideBadPieces(int currentPlayerId) {
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

    private void updatePanels(int currentColourId) {
        String[] borders = new String[] {"rgba(24,88,196,0.6)","rgba(237,197,0,0.6)","rgba(175,2,16,0.6)","rgba(39,136,37,0.6)"};
        for(int i=0; i<panels.length; i++) {
            panelBorders[i].setStyle("-fx-background-color: rgba(0, 0, 0, 0.30), #ffffff; -fx-background-insets: 0,10;");
            panelHeads[i].setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");
            panelAll[i].setStyle("-fx-background-color: rgba(0,0,0,0);-fx-background-insets: 0;");
            labelName[i].setTextFill(Paint.valueOf("rgba(255,255,255,0.4)"));
            labelPlayer[i].setTextFill(Paint.valueOf("rgba(255,255,255,0.4)"));
        }
        panelBorders[currentColourId].setStyle("-fx-background-color: "+borders[currentPlayerId]+", #ffffff;  -fx-background-insets: 0,8;");
        panelHeads[currentColourId].setStyle("-fx-background-color: "+borders[currentPlayerId]);
        panelAll[currentColourId].setStyle("-fx-background-color: rgba(0,0,0,0.6), rgba(255,255,255,0.2);-fx-background-insets: 0, 2;");
        labelName[currentColourId].setTextFill(Paint.valueOf("#ffffff"));
        labelPlayer[currentColourId].setTextFill(Paint.valueOf("#ffffff"));

        int[] score = board.currentScore();
        if(players.size()==2) {
            for(int i=0;i<2;i++) {
                String iscore = (score[i] + score[i+2] + 178) + "";
                if (iscore.length() < 2) iscore = "0" + iscore;
                if (iscore.length() < 3) iscore = " " + iscore;
                labelScore[i].setText(iscore);
            }
        } else
            for(int i=0;i<players.size();i++) {
                String iscore = (score[i] + 89) + "";
                if(iscore.length()<2) iscore="0"+iscore;
                if(iscore.length()<3) iscore=" "+iscore;
                if(i>1) {
                    iscore = iscore.replace("", "\n");
                    iscore = iscore.substring(1,iscore.length()-1);
                }
                labelScore[i].setText(iscore);
            }
    }

    public Panel currentPanel;
    public Player currentPlayer;


    private Player nextPlayer() {
        if(players.size() != 3) {
            currentPlayerId = currentColourId % players.size();
            return players.get(currentColourId % players.size());
        }
        if(turn%4==3) {
            currentPlayerId = turn % 3;
            return players.get(turn % 3);
        }
        else {
            currentPlayerId = currentColourId % players.size();
            return players.get(currentColourId % players.size());
        }
    }

    /**
     * Run between two goes - to set and unset panels and the piece-preparer,
     * to check for game ends, etc...
     */
    private void transitionMove() {
        piecePreparer.setActive(false);
        panels[currentColourId].setActive(false);
        panels[currentColourId].temporary = null;
        piecePreparer.removePiece();

        currentColourId = (currentColourId +1) % 4;
        assert(currentColourId==board.getCurrentTurn());
        // Check for the end of the game
        if(skip[0]&&skip[1]&&skip[2]&&skip[3]) {
            endGame();
            return;
        }

        currentPlayer = nextPlayer();
        turn++;

        updatePanels(currentColourId);

        currentPanel = panels[currentColourId];
        if(parent.DEBUG) System.out.print("\rPlayer " + (currentColourId + 1) + "'s go!");

        hideBadPieces(currentColourId);
        if(panels[currentColourId].activeShapes.isEmpty()){
            currentPlayer.pass(this);
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

            t.interrupt();

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(Math.pow(10,parent.GAME_SPEED)),
                    ae -> makeMove(move[0])));
            timeline.play();
        }
    }

    /**
     * Shows the game outcome. Is called only when the game ends.
     */
    private void endGame() {
        if(parent.DEBUG) {
            System.out.println("\rGame finished!");
            closingTests();
        }

        root.setEffect(new GaussianBlur(3));

        int[] score = board.currentScore();
        int playerN = players.size();
        switch (playerN) {
            case 3: score = new int[] {score[0],score[1],score[2]}; break;
            case 2: score = new int[] {score[0]+score[2],score[1]+score[3]}; break;
            case 1: score = new int[] {score[0]+score[1]+score[2]+score[3]}; break;
        }

        End endPane = new End(score, playerColours, playerN, parent);
        realRoot.getChildren().add(endPane);
    }

    /**
     * Runs tests after a game has finished, checking for things li ke:
     *      1. The game did indeed finish (there were no possible moves)
     *      2. All the componenets behaved properly
     */
    private void closingTests() {

        boolean allPassed = true;

        GameTests testClass = new GameTests(this);
        ArrayList<String> tests = new ArrayList<>(Arrays.asList(
                new String[] {
                        "testBoard",
                        "testFinishedGame",
                        "testPiecePreparer",
                        //"testIntentionalFail",
                        "testIntentionalPass"
        }));

        Class yourClass = GameTests.class;
        for (Method method : yourClass.getMethods()){
            if(tests.contains(method.getName())) {
                try {
                    System.out.print("\u001B[34m" + "Running '" + method.getName() + "()'" + "\u001B[0m");
                    Boolean value = (Boolean) method.invoke(testClass);
                    boolean returnValue = value.booleanValue();
                    if(!returnValue) {
                        System.out.println("\r\u001B[31m" + "Running '"+method.getName()+"()' ✖"); allPassed = false;
                    }
                    else if(parent.DEBUG) System.out.println("\u001B[34m" + " ✔"  + "\u001B[0m");
                } catch (InvocationTargetException e) {return;}
                  catch (IllegalAccessException e) {return;}
                  catch (SecurityException e) {return;}
            }
        }

        if(allPassed)
            System.out.println("\n\u001B[34mAll tests passed!" + "\u001B[0m");
        else
            System.out.println("\n\u001B[31mSome tests failed!" + "\u001B[0m");

    }



}
