package comp1140.ass2;

/**
 * Created by nosha on 19/08/15.
 */
public class Board {

    private CellState[][] grid;
    private String[] unplacedPiecesRed;
    private String[] unplacedPiecesGreen;
    private String[] unplacedPiecesBlue;
    private String[] unplacedPiecesYellow;

    private Pieces[] placedPieces;

    private int currentTurn;

    public void generateGrid(String game){
        grid = new CellState[0][0];
    }

    public void placePiece(String move, int turn) {
    }

    public String toString() {
        return "";
    }

    public Board() {
        this.grid = new CellState[0][0];
    }

}
