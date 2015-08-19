package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 19/08/15.
 */
public class Board {

    private CellState[][] grid;
    private Piece[] unplacedPiecesRed;
    private Piece[] unplacedPiecesGreen;
    private Piece[] unplacedPiecesBlue;
    private Piece[] unplacedPiecesYellow;

    private int currentTurn;

    public CellState[][] getGrid() {
        return grid;
    }

    public void generateGrid(String game){
        while(game != "") {
            if(game.charAt(0)=='.') {
                game = game.substring(1);
                currentTurn=(currentTurn+1)%4;
                continue;
            }
            String turn = game.substring(0,4);
            placePiece(turn,currentTurn);
            game = game.substring(4);
            currentTurn=(currentTurn+1)%4;
        }
        grid = new CellState[0][0];
    }

    public void placePiece(String move, int turn) {
    }

    public String toString() {
        String string = "";
        for(CellState[] cellRow : grid) {
            for(CellState cell : cellRow) {
                string += cell.name().charAt(0);
            }
        }
        return "";
    }

    public Board(int players) {
        this.grid = new CellState['T'-'A']['T'-'A'];
        for(int colourIndex = 0; colourIndex<players; colourIndex++) {
            Colour colour = Colour.values()[colourIndex];
            this.unplacedPiecesBlue = new Piece[]{
                    new PieceA(colour),
                    new PieceB(colour),
                    new PieceU(colour)
            };
        }
    }

}
