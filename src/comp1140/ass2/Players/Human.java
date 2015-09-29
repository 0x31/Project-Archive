package comp1140.ass2.Players;

import comp1140.ass2.Game.*;
import comp1140.ass2.Scenes.Game;

/**
 * Created by nosha on 25/09/15.
 */
public class Human implements Player {

    Game parent;
    int playerId;
    Panel myPanel;
    Board board;

    public Human(int playerId, Game parent) {
        this.parent = parent;
        this.playerId = playerId;
    }

    @Override
    public void handleClick(int x, int y) {
        if(parent.piecePreparer.getPiece()!=null) {
            Piece piece = parent.piecePreparer.getPiece().clone();
            piece.setXY(new Coordinate(x, y));
            if(board.legitimateMove(piece)) {
                parent.makeMove(this, piece);
            }
            else {
                System.out.println("Invalid move!");
            }
        }
    }

    @Override
    public void think(Board board) {
        this.board = board;
        if(stuck())
            skip();
        // HAHAHA! Humans? Thinking?!
        return; // Nope! Do nothing.
        // i.e. Wait for click();
    }

    @Override
    public void skip() {
        myPanel.lock();
        parent.skip[parent.currentPlayer] = true;
        parent.transitionMove();
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    public boolean stuck() {
        myPanel = parent.panels[parent.currentPlayer];
        Colour colour = parent.playerColours[parent.currentPlayer];
        for(Shape shape : myPanel.shapes) {
            for(char orientation : new char[] {'A','B','C','D','E','F','G','H'}) {
                parent.piecePreparer.addShape(shape, colour, orientation);
                for(int x = 0; x<20; x++) {
                    for(int y = 0; y<20; y++) {
                        //Piece testPiece = new Piece(piece.shape, piece.colour);
                        Piece testPiece = new Piece(shape, colour);
                        testPiece.initialisePiece(new Coordinate(x,y), orientation);
                        if(parent.board.legitimateMove(testPiece)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
