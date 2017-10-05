package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static main.model.Board.*;

public abstract class Piece {

    protected Square square;
    protected String color;
    protected String type;
    protected ImageIcon img;

    /**
     * An abstract method to return list of all possible moves for a piece
     */
    abstract public ArrayList<Square> getAllPossibleMoves();

    /**
     * Getter for piece's color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Getter for piece's image
     */
    public ImageIcon getImage() {
        return this.img;
    }

    /**
     * Returns a list of all moves a piece can take in a single direction
     * Used for Bishop, Queen, & Rook
     */
    public List<Square> allOneDirectionMoves(int deltaX, int deltaY, String color) {
        int row = square.getRow();
        int col = square.getCol();

        Square[][] grid = Board.getGrid();

        List<Square> possibleMoves = new ArrayList<>();

        row += deltaX;
        col += deltaY;

        if (isValidCoord(row, col) && Board.hasEnemy(row, col, color)) {
            possibleMoves.add(grid[row][col]);
        }
        while (isValidCoord(row, col) && grid[row][col].isFree()) {
            possibleMoves.add(grid[row][col]);
            row += deltaX;
            col += deltaY;
            if (isValidCoord(row, col) && Board.hasEnemy(row, col, color)) {
                possibleMoves.add(grid[row][col]);
                break;
            }
        }

        return possibleMoves;
    }

    /**
     * Bidirectional
     * Returns a square that the piece is on
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * Updates the location of the piece
     */
    public void setSquare(Square square) {

        this.square = square;

    }

    /**
     * Returns piece type
     */
    public String getType() {
        return this.type;
    }


}

