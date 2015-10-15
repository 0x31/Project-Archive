package comp1140.ass2.Game;

import comp1140.ass2.Players.Human;
import comp1140.ass2.Scenes.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***, on 19/09/15
 * @author Holly, ***REMOVED***, some contributions
 * @author Tim, clicking
 *
 * Board implements the logisitics of the Blokus board, including
 *  1. Playing moves
 *  2. Scoring
 *  3. Displaying the pieces
 */
public class Board extends GridSprite {

    private final Colour[][] grid;
    private final boolean[] unplacedPiecesRed = new boolean['U'-'A'+ 1];
    private final boolean[] unplacedPiecesGreen = new boolean['U'-'A'+ 1];
    private final boolean[] unplacedPiecesBlue = new boolean['U'-'A' + 1];
    private final boolean[] unplacedPiecesYellow = new boolean['U' - 'A' + 1];
    private boolean active=false;
    private boolean displayable = true;
    private final boolean[][] unplacedPieces =
            {unplacedPiecesBlue
            ,unplacedPiecesYellow
            ,unplacedPiecesRed
            ,unplacedPiecesGreen};
    private final ArrayList<String> moves = new ArrayList<>();
    private int currentTurn;
    private final boolean[] lastMove = new boolean[]{false, false, false, false};
    private PieceSprite preview;
    private Coordinate previewCoord;

    /**
     * @return the 2D array of booleans representing whether a piece has been placed
     */
    public boolean[][] getUnplacedPieces() { return unplacedPieces; }

    /**
     * @return the current turn as an int (where 0=blue, ..., 4=green)
     */
    public int getCurrentTurn() { return currentTurn; }

    /**
     * @param active the state the board should be set to; inactive makes the board unhoverable
     */
    public void setActive(boolean active) { this.active = active; }

    /**
     * @return Returns an array of booleans denoting which player's last turn was placing a monomino
     */
    public boolean[] getLastMove() { return lastMove;}

    /**
     * @return the grid of the board as a 2D array of cells (Colours)
     */
    public Colour[][] getGrid() {
        return grid;
    }

    /**
     * Takes a Piece and transfers it to the board, after checking that the move is valid
     * @param piece the piece to place, representing shape, orientation and coordinate
     * @return the exiting status of the function
     */
    private boolean placePiece(Piece piece) {

        if(!legitimateMove(piece)) return false;

        /* Remove piece from unplacedPieces */
        int playerId = (parent!=null) ? parent.currentColourId : piece.colour.ordinal();
        int shapeId = piece.shape.ordinal();

        unplacedPieces[playerId][shapeId] = false;

        /* Setting the appropriate cells */
        Colour turnColour = Colour.values()[playerId];
        PieceSprite pieceSprite = new PieceSprite(piece, xsize, this);
        if(displayable) {
            this.addPieceSprite(pieceSprite);
        }
        /* Set Grid for legitimateMove */
        for(Coordinate coord : pieceSprite.coordinates) {
            grid[coord.getY()][coord.getX()] = turnColour;
        }

        /* Check for monomino */
        lastMove[playerId] = (shapeId == 0);

        moves.add(piece.toString());

        currentTurn=(currentTurn+1)%4;
        return true;
    }

    /**
     * Given a four character String, plays the encoded move, after checking it's validity.
     * Converts the string into a Piece and then calls placePiece(Piece piece)
     * @param move a 4 character string representing a shape, orientation and coordinate
     * @return the exiting status of the function
     */
    public boolean placePiece(String move) {
        if(move==".") { currentTurn=(currentTurn+1)%4; moves.add("."); return true; }
        Colour turnColour = Colour.values()[currentTurn];
        Piece piece = new Piece(move, turnColour);
        placePiece(piece);
        return true;
    }

    /**
     * @return A String encoding of the board, based on the board's past moves
     */
    public String toString() {
        String string = "";
        for (String move : moves)
            string += move + " ";
        return string;
    }

    /**
     * @return A String representation of the board's grid, used for testing and debugging
     */
    public String gridToString() {
        String string = "";
        /* substring() is used instead of charAt to able to subsequently use replace */
        for (Colour[] aGrid : grid) {
            for (Colour anAGrid : aGrid)
                string += anAGrid.name().substring(0, 1).replace("E", "•") + " ";
            string += "\n";
        }
        return string;
    }

    /**
     * Initialises a new Board to be displayed in a JavaFX app
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param size the size to be used when displaying the cells
     * @param color the background colour of the grid
     * @param parent the parent Game class
     */
    public Board(int col, int row, int size, Colour color, Game parent) {
        super(col, row, size, color, parent);

        /* Initialise the grid */
        grid = new Colour['T'-'A'+1]['T'-'A'+1];
        for(int i=0;i<grid.length;i++) for(int j=0;j<grid[0].length;j++) grid[i][j]=Colour.Empty;

        /* Fill up array of unused pieces */
        for(boolean[] unplacedPieceList : unplacedPieces)
            for(int i=0;i<unplacedPieceList.length;i++)
                unplacedPieceList[i]=true;

        /* When the mouse leaves the board, hide the Display piece */
        this.setOnMouseExited(event -> isUnhovered());

    }

    /**
     * Construct a new Board not for JavaFX displaying
     * @param game a string encoding of a game containing a previous set of moves
     */
    public Board(String game) {
        /* Won't be displayed as a GridPane */
        displayable = false;

        /* Fill up array of unused pieces */
        for(boolean[] unplacedPieceList : unplacedPieces)
            for(int i=0;i<unplacedPieceList.length;i++)
                unplacedPieceList[i]=true;

        /* Initialise the grid from the String */
        game = game.replace(" ", "");
        grid = new Colour['T'-'A'+1]['T'-'A'+1];
        for(int i=0;i<grid.length;i++) for(int j=0;j<grid[0].length;j++) grid[i][j]=Colour.Empty;
        String[] moves = splitMoves(game);
        for(String move : moves) placePiece(move);
    }

    /**
     * Split a string encoding into an array of strings, each representing a single move
     * @param game the string encoding of a game
     * @return moves an array of strings, each one representing a single move
     */
    public static String[] splitMoves (String game) {

        /* An array is used instead of an ArrayList for compatibility */
        game = game.replace(" ","");

        /* Work out how long the array should be */
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
     * Checks that a potential Piece move is legitimate
     * @param piece the move encoded as a piece to check
     * @return the legitimacy of the move
     */
    public boolean legitimateMove(Piece piece) {

        //int playerId = (parent!=null) ? parent.currentColourId : piece.colour.ordinal();
        int playerId = currentTurn;

        /* Check that piece hasn't been played yet */
        if(!unplacedPieces[playerId][piece.shape.ordinal()]) {
            return false;
        }

        Coordinate[] cells = piece.getOccupiedCells();
        Colour turnColour = piece.colour;

        boolean touchingSide = false;
        for(Coordinate cell : cells) {

            /* Check that the coordinate is not outside the grid */
            if(cell.getX()<0 || cell.getX()>19 || cell.getY()<0 || cell.getY()>19) return false;

            /* Check that coordinates are empty */
            if(grid[cell.getY()][cell.getX()]!=Colour.Empty) return false;

            /* Check that no sides are being touched */
            for(Coordinate sideCell : cell.getSideCells())
                if( cellAt(sideCell) == turnColour) return false;

            /* Check that there's at least one touching diagonal */
            for(Coordinate diagonalCell : cell.getDiagonalCells()) {
                if (cellAt(diagonalCell) == turnColour) touchingSide = true;
            }
        }

        return touchingSide;

    }

    /**
     * Parse a string representing a single move to check the move's legitimacy
     * Works by converting the string into a Piece and then calling legitimateMove(Piece piece)
     * @param move the string representing a single move to check
     * @return the legitimacy of the move
     */
    public boolean legitimateMove(String move) {

        if (move == ".") return true;
        Colour turnColour = Colour.values()[currentTurn];
        Piece piece = new Piece(move, turnColour);
        if (piece == null) return false;

        return legitimateMove(piece);
    }

    /**
     * Given a Coordinate, returns the cell on the board at the location, including corners for starting positions
     * @param c the coordinate to query
     * @return the cell (as a Colour) at the location
     */
    public Colour cellAt(Coordinate c) {

        /* Check for starting corners */
        if(c.getX()==-1 && c.getY()==-1) return Colour.Blue;
        if(c.getX()==-1 && c.getY()==20) return Colour.Green;
        if(c.getX()==20 && c.getY()==-1) return Colour.Yellow;
        if(c.getX()==20 && c.getY()==20) return Colour.Red;

        /* Not a corner */
        return grid[c.getY()][c.getX()];
    }

    /**
     * When the board is clicked, determine the coordinate of the cell clicked and pass it to the current player
     * @param cell the cell under the mouse for the click event
     */
    public void isClicked(CellSprite cell) {
        int x = getColumnIndex(cell);
        int y = getRowIndex(cell);
        if(parent.currentPlayer.isHuman()) {
            ((Human) parent.currentPlayer).handleClick(x,y);
        }
    }

    /**
     * Deals with the Preview piece getting in the way of clicks
     * @param sprite the PieceSprite on the board that was clicked
     */
    public void isClicked(PieceSprite sprite) {
        if(sprite == preview) {
            int x = sprite.coordinates[0].getX();
            int y = sprite.coordinates[0].getY();
            if(parent.currentPlayer.isHuman()) {
                ((Human) parent.currentPlayer).handleClick(x,y);
            }
        }
    }

    /**
     * Deals with the board being right-clicked
     * @param sprite the PieceSprite on the board that was right-clicked
     */
    public void isRightClicked(PieceSprite sprite) {
        parent.piecePreparer.rotatePiece();
        Piece piece = parent.piecePreparer.getPiece();
        if(piece==null) return;
        piece = new Piece(piece);
        piece.setXY(previewCoord);
        isUnhovered();
        previewPiece(piece);
    }

    /**
     * Previews a Preview piece (or shadow piece) under the mouse when a cell is hovered over
     * @param cell the CellSprite being hovered over by the mouse
     */
    public void isHovered(CellSprite cell) {
        if(!active || parent.currentPlayer==null || !parent.currentPlayer.isHuman())
            return;
        int x = getColumnIndex(cell);
        int y = getRowIndex(cell);
        Coordinate tempCoord = new Coordinate(x,y);
        if(previewCoord!=null && tempCoord.equals(previewCoord))
            return;

        /* Remove the previous shadow piece */
        isUnhovered();
        previewCoord = tempCoord;
        Piece piece = parent.piecePreparer.getPiece();
        if(piece==null) return;
        piece = new Piece(piece);
        piece.setXY(previewCoord);
        this.previewPiece(piece);
    }

    /**
     * Remove the current preview piece under the mouse
     */
    public void isUnhovered() {
        if(preview != null) {
            this.removePieceSprite(preview);
            preview = null;
        }
        previewCoord = null;
    }

    /**
     * Displays a transparent Piece on top of the board (above other pieces)
     * @param piece the Piece to place
     */
    private void previewPiece(Piece piece) {
        preview = new PieceSprite(piece, xsize, this);
        if(!this.legitimateMove(piece))
            preview.setOpacity(0.4);
        else
            preview.setOpacity(0.9);
        this.addPieceSprite(preview);
    }

    /**
     * Returns the score of a game
     * @return an array of ints representing the score of each player
     */
    public int[] currentScore() {
        int[] scores = new int[4];
        int[] pieceLenghts = new int[] {1,2,3,3,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5};

        for(int i=0;i<4;i++) {
            for(int j = 0; j < getUnplacedPieces()[i].length; j++)
                if(getUnplacedPieces()[i][j])
                    scores[i] -= pieceLenghts[j];
            if(scores[i]==0) {
                if(getLastMove()[i]) scores[i]+=20;
                else scores[i] += 15;
            }
        }
        return scores;
    }

    /***/
    public boolean isFinished() {
        int last = moves.size();
        if(last<3) return false;
        return (moves.get(last-1)=="." && moves.get(last-2)=="." && moves.get(last-3)=="." && moves.get(last-4)==".");
    }

}
