package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 19/08/15.
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***
 */
public class Board {

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
     * @return
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

        piece.shape.initialisePiece(coordinate,rotation);                       // @TODO check Tim's edit is consistent
        Coordinate[] cells = piece.shape.getOccupiedCells();                    //Tim's edit: Inserted 'getOccupiedCells'


        /* @TODO find better way to get CellState */
        CellState[] cellstates = {CellState.Blue, CellState.Yellow, CellState.Red, CellState.Green};
        CellState turnColour = cellstates[playerId];



        for(Coordinate cell : cells) {
            if(cell!=null) grid[cell.getY()][cell.getX()] = turnColour;
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
        /* @TODO replace with foreach / for(:)? */
        for(int i=0;i<grid.length;i++) {
            for(int j = 0;j<grid[i].length;j++) {
                /* We use substring instead of charAt to able to subsequently use replace */
                string += grid[i][j].name().substring(0,1).replace("E","•")+" ";
            }
            string += "\n";
        }
        return string;
    }

    /**
     * Initialises a Board object from a string.
     * @param game A string representing the set of moves so far
     */
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
     *
     * @param game
     * @return
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

        /* @TODO find better way to get CellState */
        CellState[] cellstates = {CellState.Blue, CellState.Yellow, CellState.Red, CellState.Green};
        CellState turnColour = cellstates[playerId];

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
