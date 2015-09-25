package comp1140.ass2.Game;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Created by ***REMOVED*** on 19/08/15.
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***
 */
public class Board extends GridSprite {

    private CellState[][] grid;

    private Piece[] unplacedPiecesRed;
    private Piece[] unplacedPiecesGreen;
    private Piece[] unplacedPiecesBlue;
    private Piece[] unplacedPiecesYellow;

    private Piece[][] unplacedPieces =
            {unplacedPiecesBlue
            ,unplacedPiecesYellow
            ,unplacedPiecesRed
            ,unplacedPiecesGreen};

    /**
     * @return Returns this.unplacedPieces;
     */
    public Piece[][] getUnplacedPieces() {
        return unplacedPieces;
    }

    private int currentTurn;

    private boolean[] lastMove = new boolean[]{false, false, false, false};

    /**
     *
     * @return Returns an array of booleans denoting which player's last turn was placing a monimo
     */
    public boolean[] getLastMove() {
        return lastMove;
    }

    /**
     *
     * @return CellState[][] this.grid
     */
    public CellState[][] getGrid() {
        return grid;
    }

    /**
     * Given a four character String and the identifying number of the current player,
     * sets the grid coordinates to that player's colour where the piece is played.
     *
     * @param move   a four character string representing a single move
     * @return void  while also changing this.grid
     */
    public void placePiece(String move) {

        if(move==".") {
            currentTurn++;
            return;
        }

        /* Split up the String move */
        char pieceChar = move.charAt(0);
        char rotation  = move.charAt(1);
        char x         = move.charAt(2);
        char y         = move.charAt(3);

        int playerId = currentTurn % 4;

        Coordinate coordinate = new Coordinate(x-'A',y-'A');
        Piece piece = unplacedPieces[playerId][pieceChar-'A'];

        /* Remove piece from unplacedPieces */
        unplacedPieces[playerId][pieceChar-'A'] = null;

        /* Setting the appropriate cells */
        piece.shape.initialisePiece(coordinate,rotation);
        Coordinate[] cells = piece.shape.getOccupiedCells();
        CellState turnColour = CellState.values()[playerId];
        for(Coordinate cell : cells) {
            if(cell!=null) {
                this.setCell(cell, turnColour);
                grid[cell.getY()][cell.getX()] = turnColour;
            }
        }

        /** Check for monomino */
        lastMove[playerId] = (pieceChar == 'A');

        currentTurn++;
    }

    /**
     * Board's toString function, currently used for debugging.
     * @return String a string representation of the board, made up of the intials of each colour.
     */
    public String toString() {
        String string = "";
        for (CellState[] aGrid : grid) {
            for (CellState anAGrid : aGrid) {
                /* We use substring instead of charAt to able to subsequently use replace */
                string += anAGrid.name().substring(0, 1).replace("E", "•") + " ";
            }
            string += "\n";
        }
        return string;
    }

    /**
     * Initialises a Board object from a string.
     * @param game A string representing the set of moves so far
     */

    public Board(int col, int row, int width, int height, Color color) {
        super(col, row, width, height, color);
        grid = new CellState['T'-'A'+1]['T'-'A'+1];
        for(int i=0;i<grid.length;i++) for(int j=0;j<grid[0].length;j++) grid[i][j]=CellState.Empty;

        /**
         * Fill up array of unused pieces
         */
        for(int colourIndex = 0; colourIndex<4; colourIndex++) {
            Colour colour = Colour.values()[colourIndex];
            unplacedPieces[colourIndex] = new Piece['U'-'A'+1];
            int i=0;
            for(Shape shape : Shape.values()) unplacedPieces[colourIndex][i++] = new Piece(shape, colour);
        }

        /* Loop through moves and play each one */


        String game = "RCCC RBTA SARR SBCR SHDD TBQD RAOO PBFP LBJH LHLH LGNN TAGN JDKI JBRA OHIM UAHK KDGJ KAPH JARK JAFG UADG UALA UASH QAGD QDCL PCIC MEQE MEBL DDKL MDRE TGJQ OHID EBFA QDON PAIR KBGT IBMM SHMO KDDR RCDK GCFO NAPR QCCQ IDAH FHKQ IHRP FATN LDAD NBIP OHJR DBEM FFFB PBMF BASN AAHN DBBP THMC FGTM BBSD AAME OBRB EBNJ . BBOF MHFC CBJI . . HANR DAHD . . CBMT AAGH . . BBBK . . . AACF . . . .";
        String[] moves = Board.splitMoves(game);
        class Index {
            int index = 0;
            public Index() {
            }
            public void add(int i) {
                index+=i;
            }
            public int value() {
                return index;
            }
        }
        Index index = new Index();

        Board board = this;

        board.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (index.value() < moves.length) {
                    if(index.value() > 3) {
                        if(moves[index.value()]=="." && moves[index.value()-1]=="." && moves[index.value()-2]=="." && moves[index.value()-3]==".") {

                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Winner!");
                            a.setHeaderText("Game ended!");
                            a.setResizable(true);
                            String content = "You may have won! Congratulations!";
                            a.setContentText(content);
                            a.showAndWait();

                        }
                    }
                    board.placePiece(moves[index.value()]);
                    index.add(1);
                }
            }
        });


    }


    public Board(String game) {

        game = game.replace(" ","");
        grid = new CellState['T'-'A'+1]['T'-'A'+1];
        for(int i=0;i<grid.length;i++) for(int j=0;j<grid[0].length;j++) grid[i][j]=CellState.Empty;

        /**
         * Fill up array of unused pieces
         */
        for(int colourIndex = 0; colourIndex<4; colourIndex++) {
            Colour colour = Colour.values()[colourIndex];
            unplacedPieces[colourIndex] = new Piece['U'-'A'+1];
            int i=0;
            for(Shape shape : Shape.values()) unplacedPieces[colourIndex][i++] = new Piece(shape, colour);
        }

        /* Loop through moves and play each one */
        String[] moves = splitMoves(game);
        for(String move : moves) placePiece(move);
    }

    /**
     *sou
     * @param game
     * @return moves  an array of strings, each string representing one move
     */
    public static String[] splitMoves (String game) {
        game = game.replace(" ","");
        int passCount = game.length() - game.replace(".","").length();
        int moveCount = (game.length() - passCount) / 4;
        int totalCount = passCount + moveCount;
        if( (passCount+4*moveCount) != game.length())
            throw new IllegalArgumentException("Invalid game");
        String[] moves = new String[totalCount];

        int index=0;
        for(int i=0;i<totalCount;i++){
            if(game.charAt(index)=='.') {
                moves[i]=".";
                index++;
                continue;
            }
            moves[i]=game.substring(index,index+4);
            index+=4;
        }

        return moves;
    }

    /**
     *
     * @param move
     * @return
     */
    public boolean legitimateMove(String move) {

        if(move==".") return true;

        /* Split up the String move */
        char pieceChar = move.charAt(0);
        char rotation  = move.charAt(1);
        char x         = move.charAt(2);
        char y         = move.charAt(3);

        int playerId = currentTurn % 4;

        CellState turnColour = CellState.values()[playerId];

        Coordinate coordinate = new Coordinate(x-'A',y-'A');
        Piece piece = unplacedPieces[playerId][pieceChar-'A'];

        if(piece==null) return false;

        /** Get array of coordinates */
        piece.shape.initialisePiece(coordinate,rotation);
        Coordinate[] cells = piece.shape.getOccupiedCells();                    //Tim's edit: Inserted 'getOccupiedCells'

        /** Check that coordinates are empty */
        boolean touchingSide = false;
        for(Coordinate cell : cells) {
            if(cell.getX()<0 || cell.getX()>19 || cell.getY()<0 || cell.getY()>19) return false;
            if(grid[cell.getY()][cell.getX()]!=CellState.Empty) return false;
            for(Coordinate sideCell : cell.getSideCells())
                if( cellAt(sideCell) == turnColour) return false;
            for(Coordinate diagonalCell : cell.getDiagonalCells()) {
                if (cellAt(diagonalCell) == turnColour) touchingSide = true;
            }
        }

        return touchingSide;
    }

    public CellState cellAt(Coordinate c) {

        CellState[] validCorners = {CellState.Empty, CellState.Yellow, CellState.Red, CellState.Green};

        /** Check for starting corner */
        if(c.getX()==-1 && c.getY()==-1) return CellState.Blue;
        if(c.getX()==-1 && c.getY()==20) return validCorners[currentTurn%4];
        if(c.getX()==20 && c.getY()==-1) return validCorners[currentTurn%4];
        if(c.getX()==20 && c.getY()==20) return validCorners[currentTurn%4];
        return grid[c.getY()][c.getX()];
    }
}
