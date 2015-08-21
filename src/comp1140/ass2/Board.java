package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 19/08/15.
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***
 */
public class Board {

    private CellState[][] grid;
    private Piece[] unplacedPiecesRed = new Piece['U'-'A'];
    private Piece[] unplacedPiecesGreen;
    private Piece[] unplacedPiecesBlue=new Piece['U'-'A'];
    private Piece[] unplacedPiecesYellow;
    private Piece[][] unplacedPieces =
            {unplacedPiecesRed
            ,unplacedPiecesGreen
            ,unplacedPiecesBlue
            ,unplacedPiecesYellow};

    private int currentTurn;

    /**
     *
     * @return CellState[][] this.grid
     */
    public CellState[][] getGrid() {
        return grid;
    }

    /**
     *
     * Given a four character String and the identifying number of the current player,
     * sets the grid coordinates to that player's colour where the piece is played.
     *
     * @param move   a four character string representing a single move
     * @param playerId   an integer identifying the current player
     * @return void  while also changing this.grid
     */
    public void placePiece(String move, int playerId) {

        /* Split up the String move */
        char pieceChar = move.charAt(0);
        char rotation  = move.charAt(1);
        char x         = move.charAt(2);
        char y         = move.charAt(3);

        Coordinate coordinate = new Coordinate(x,y);
        Piece piece = unplacedPieces[playerId][pieceChar-'A'];

        /* Remove piece from unplacedPieces */
        unplacedPieces[playerId][pieceChar-'A'] = null;

        /* @TODO once Piece.getOccupiedCells is finished, uncomment line */
        //Coordinate[] cells = piece.getOccupiedCells(coordinate,rotation);
        Coordinate[] cells = {new Coordinate(playerId,playerId)};


        /* @TODO find better way to get CellState */
        CellState[] cellstates = {CellState.Blue, CellState.Yellow, CellState.Red, CellState.Green};
        CellState turnColour = cellstates[playerId];

        for(Coordinate cell : cells) {
            grid[cell.getY()][cell.getX()] = turnColour;
        }
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
        grid = new CellState['T'-'A']['T'-'A'];
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++)
                grid[i][j]=CellState.Empty;
        int players = 4;

        /**
         * Fill up array of unused pieces
         */
        for(int colourIndex = 0; colourIndex<players; colourIndex++) {
            Colour colour = Colour.values()[colourIndex];
            unplacedPieces[colourIndex] = new Piece[]{
                    new PieceA(colour),
                    new PieceB(colour),
                    new PieceU(colour)
            };
        }

        /* Remove any spaces */
        game = game.replace(" ","");
        /**
         * Parse string game and turn it into a board
         */
        while(game.length()>0) {

            /**
             * Check for a pass, represented by the character '.'.
             */
            if(game.charAt(0)=='.') {
                game = game.substring(1);
                currentTurn++;
                continue;
            }
            /**
             * Get the next four chars and parse the individual move.
             */
            String turn = game.substring(0,4);
            placePiece(turn,currentTurn%4);
            game = game.substring(4);
            currentTurn++;
        }
    }
}
