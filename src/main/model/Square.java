package main.model;

import main.model.pieces.Piece;

public class Square implements Comparable<Square> {
    private int row;
    private int col;
    private Piece piece;

    /**
     * Constructor to set up coordinates of each square
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.piece = null; // No piece on square initially
    }

    /**
     * Checks if a square is empty
     */
    public boolean isFree() {
        return (piece == null);
    }

    /**
     * Getter for piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets piece on square
     */
    public void setPiece(Piece piece) {

        this.piece = piece;
        if (piece != null)
            piece.setSquare(this);

    }

    /**
     * Removes piece from square
     */
    public Piece removePiece() {

        Piece piece = this.piece;
        this.piece = null;
        piece.setSquare(null);
        return piece;

    }

    /**
     * String representation of coordinates
     */
    public String toString() {

        return "(" + row + ", " + col + ")";

    }

    /**
     * Returns row of a square
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns column of a square
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Equality operator for squares
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        } else if (obj.getClass() != getClass()) {
            return false;
        }
        Square sq = (Square)obj;

        return this.row == sq.row && this.col == sq.col;
    }

    /**
     * Comparative operator to sort array of Squares
     */
    @Override
    public int compareTo(Square sq) {
        if (this.row < sq.row) {
            return -1;
        } else if (this.row == sq.row) {
            if (this.col < sq.col) {
                return -1;
            } else if (this.col == sq.col) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
