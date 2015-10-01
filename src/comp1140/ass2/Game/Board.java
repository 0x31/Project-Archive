package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by ***REMOVED*** on 19/08/15.
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***
 * Contributed to by Holly, ***REMOVED***
 */
public class Board extends GridSprite {

    private Colour[][] grid;

    private boolean[] unplacedPiecesRed = new boolean['U'-'A'+ 1];
    private boolean[] unplacedPiecesGreen = new boolean['U'-'A'+ 1];
    private boolean[] unplacedPiecesBlue = new boolean['U'-'A' + 1];
    private boolean[] unplacedPiecesYellow = new boolean['U' - 'A' + 1];

    private boolean active=false;
    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean[][] unplacedPieces =
            {unplacedPiecesBlue
            ,unplacedPiecesYellow
            ,unplacedPiecesRed
            ,unplacedPiecesGreen};

    /**
     * @return Returns this.unplacedPieces;
     */
    public boolean[][] getUnplacedPieces() {
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
     * @return Colour[][] this.grid
     */
    public Colour[][] getGrid() {
        return grid;
    }

    /**
     * Given a four character String and the identifying number of the current player,
     * sets the grid coordinates to that player's colour where the piece is played.
     *
     * @param piece the piece to place, representing shape, orientation and coordinate
     * @return void  while also changing this.grid
     */
    public boolean placePiece(Piece piece) {


        if(!legitimateMove(piece)) return false;

        /* Remove piece from unplacedPieces
         * Is this okay? I don't know if using ordinal is a good idea, as the order may change
         * If a tutor is reading this, then we obviously made the wise decision of keeping ordinal()
         */
        //int playerId = piece.colour.ordinal();
        int playerId = (parent!=null) ? parent.currentPlayerId : piece.colour.ordinal();
        int shapeId = piece.shape.ordinal();

        unplacedPieces[playerId][shapeId] = false;

        /* Setting the appropriate cells */
        Colour turnColour = Colour.values()[playerId];
        PieceSprite pieceSprite = new PieceSprite(piece, xsize, this);
        this.addPieceSprite(pieceSprite);
        // Set Grid for legitimateMove
        for(Coordinate coord : pieceSprite.coordinates) {
            grid[coord.getY()][coord.getX()] = turnColour;
        }

        /** Check for monomino */
        lastMove[playerId] = (shapeId == 0);

        currentTurn=(currentTurn+1)%4;
        return true;
    }


    /**
     * See above for placePiece
     * @param move a 4-char string representing a move
     * @return
     */
    public boolean placePiece(String move) {
        if(move==".") { currentTurn=(currentTurn+1)%4; return true; }
        Colour turnColour = Colour.values()[currentTurn];
        int x         = move.charAt(2)-'A';
        int y         = move.charAt(3)-'A';
        Piece piece = new Piece(move, turnColour);
        placePiece(piece);
        return true;
    }


    /**
     * Board's toString function, currently used for debugging.
     * @return String a string representation of the board, made up of the intials of each colour.
     */
    public String toString() {
        String string = "";
        for (Colour[] aGrid : grid) {
            for (Colour anAGrid : aGrid) {
                /* We use substring instead of charAt to able to subsequently use replace */
                string += anAGrid.name().substring(0, 1).replace("E", "•") + " ";
            }
            string += "\n";
        }
        return string;
    }

    /**
     * Creates a new Scene which represents a Blokus board state
     * @param col the number of columns
     * @param row the number of rows
     * @param size the size of the cells
     * @param color the default colour
     * @param parent the Game class
     */
    public Board(int col, int row, int size, Colour color, Game parent) {
        super(col, row, size, color, parent);
        grid = new Colour['T'-'A'+1]['T'-'A'+1];
        for(int i=0;i<grid.length;i++) for(int j=0;j<grid[0].length;j++) grid[i][j]=Colour.Empty;

        /**
         * Fill up array of unused pieces
         */
        for(boolean[] unplacedPieceList : unplacedPieces) {
            for(int i=0;i<unplacedPieceList.length;i++) {
                unplacedPieceList[i]=true;
            }
        }


        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isUnhovered();
            }
        });

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

    }

    /** Here for compatibility, please ignore
     * See above for description
     * @param game a string containing a previous set of moves
     */
    public Board(String game) {

        game = game.replace(" ","");
        grid = new Colour['T'-'A'+1]['T'-'A'+1];
        for(int i=0;i<grid.length;i++) for(int j=0;j<grid[0].length;j++) grid[i][j]=Colour.Empty;

        /**
         * Fill up array of unused pieces
         */

        for(boolean[] unplacedPieceList : unplacedPieces) {
            for(int i=0;i<unplacedPieceList.length;i++) {
                unplacedPieceList[i]=true;
            }
        }

        /* Loop through moves and play each one */
        String[] moves = splitMoves(game);
        for(String move : moves) placePiece(move);
    }

    /**
     * Split a string (game) into an array of strings, each representing one move
     * @param game
     * @return moves: an array of strings, each string representing one move
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
     * Same as below, but for piece
     * @param piece the piece to check
     * @return
     */
    public boolean legitimateMove(Piece piece) {

        int playerId = (parent!=null) ? parent.currentPlayerId : piece.colour.ordinal();

        if(!unplacedPieces[playerId][piece.shape.ordinal()]) {
            return false;
        }
        Coordinate[] cells = piece.getOccupiedCells();
        Colour turnColour = piece.colour;
        /** Check that coordinates are empty */
        boolean touchingSide = false;
        for(Coordinate cell : cells) {
            if(cell.getX()<0 || cell.getX()>19 || cell.getY()<0 || cell.getY()>19) return false;
            if(grid[cell.getY()][cell.getX()]!=Colour.Empty) return false;
            for(Coordinate sideCell : cell.getSideCells())
                if( cellAt(sideCell) == turnColour) return false;
            for(Coordinate diagonalCell : cell.getDiagonalCells()) {
                if (cellAt(diagonalCell) == turnColour) touchingSide = true;
            }
        }

        return touchingSide;

    }

    /**
     * Parse a string (move) to check if the move is legitimate.
     * @param move
     * @return the ligitness of the move
     * (if legit is replacing legitimate, then legitness can replace legitimacy)
     */
    public boolean legitimateMove(String move) {

        if (move == ".") return true;

        /* Split up the String move */
        char pieceChar = move.charAt(0);
        char rotation = move.charAt(1);
        char x = move.charAt(2);
        char y = move.charAt(3);

        int playerId = currentTurn % 4;

        Colour turnColour = Colour.values()[playerId];

        Coordinate coordinate = new Coordinate(x - 'A', y - 'A');
        Piece piece = new Piece(move, turnColour);

        if (piece == null) return false;

        /** Get array of coordinates */
        piece.initialisePiece(coordinate, rotation);
        return legitimateMove(piece);
    }

    /**
     * @param c the coordinate to check at
     * @return the Colour at a particular cell, including corners for starting positions
     */
    public Colour cellAt(Coordinate c) {

        // I don't know what this is doing - I should have commented it when I wrote it.
        Colour[] validCorners = {Colour.Empty, Colour.Yellow, Colour.Red, Colour.Green};

        /** Check for starting corner */
        if(c.getX()==-1 && c.getY()==-1) return Colour.Blue;
        if(c.getX()==-1 && c.getY()==20) return Colour.Green;
        if(c.getX()==20 && c.getY()==-1) return Colour.Yellow;
        if(c.getX()==20 && c.getY()==20) return Colour.Red;
        return grid[c.getY()][c.getX()];
    }

    /**
     * Grab coordinates of key cell of a piece when it is clicked.
     * @param sprite
     */
    public void isClicked(PieceSprite sprite) {
        if(sprite == preview) {
            int x = sprite.coordinates[0].getX();
            int y = sprite.coordinates[0].getY();
            parent.players[parent.currentPlayerId].handleClick(x, y);
        }
    }

    /** @TODO get shadow to update as piece is rotated */
    public void isRightClicked(PieceSprite sprite) {
        parent.piecePreparer.isClicked(sprite);
    }

    /**
     * Grad coordinates of the grid when a cell is clicked.
     * @param cell
     */

    public void isClicked(CellSprite cell) {
        int x = this.getColumnIndex(cell);
        int y = this.getRowIndex(cell);
        parent.players[parent.currentPlayerId].handleClick(x, y);
    }
    public void isHovered(CellSprite cell) {
        if(!active || !parent.players[parent.currentPlayerId].isHuman()) {
            return;
        }
        int x = this.getColumnIndex(cell);
        int y = this.getRowIndex(cell);
        Coordinate tempCoord = new Coordinate(x,y);
        if(previewCoord!=null && tempCoord.equals(previewCoord)) {
            return;
        }
        isUnhovered();
        previewCoord = tempCoord;
        Piece piece = parent.piecePreparer.getPiece();
        if(piece==null) return;
        piece = piece.clone();
        piece.setXY(previewCoord);
        /*for(Coordinate coord : piece.getOccupiedCells()) {
            if(coord.getY()>=20 || coord.getY()<0 || coord.getX()>=20 || coord.getX()<0)
                return;
        }*/
        this.previewPiece(piece);
    }

    /**
     * When a cell in the grid triggers a MouseExited event
     */
    public void isUnhovered() {
        if(preview != null) {
            this.removePieceSprite(preview);
            preview = null;
        }
        previewCoord = null;
    }
    public PieceSprite preview;
    public Coordinate previewCoord;

    /**
     * Shows a shadow of the piece under the cursor
     * @param piece the shape/orientation to render
     */
    public void previewPiece(Piece piece) {
        preview = new PieceSprite(piece, xsize, this);
        if(!this.legitimateMove(piece))
            preview.setOpacity(0.4);
        else
            preview.setOpacity(0.9);
        this.addPieceSprite(preview);
    }

    /**
     * Taken from BlokGame, returns the score
     * @return the game's current score
     */
    public int[] currentScore() {
        int[] scores = new int[4];

        int[] pieceLenghts = new int[] {1,2,3,3,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5};

        for(int i=0;i<4;i++) {
            for(int j = 0; j < getUnplacedPieces()[i].length; j++) {
                if(getUnplacedPieces()[i][j]!=false) {
                    scores[i] -= pieceLenghts[j];
                }
            }
            if(scores[i]==0) {
                if(getLastMove()[i]) scores[i]+=20;
                else scores[i] += 15;
            }
        }
        return scores;
    }

}
