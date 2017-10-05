package main.model;

import main.model.pieces.Piece;

public class Move {

    public Piece srcPiece;
    public Piece destPiece;
    public int srcRow;
    public int srcCol;
    public int destRow;
    public int destCol;

    public Move(Square src, int row, int col) {
        srcPiece = src.getPiece();
        destPiece = Board.getPiece(row, col);
        destRow = row;
        destCol = col;
        srcRow = srcPiece.getSquare().getRow();
        srcCol = srcPiece.getSquare().getCol();
    }
}
