package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

import static main.model.Board.isValidSquare;

public class King extends Piece {

    /**
     * Constructor for King
     */
    public King(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "K";
        this.img = new ImageIcon(getClass().getResource("/resources/images/" + color + "-King.png"));

    }

    /**
     * Returns a list of all possible valid moves for King
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {
        int row = square.getRow();
        int col = square.getCol();

        ArrayList<Square> possibleMoves = new ArrayList<>();

        // Check square in front of king
        if (isValidSquare(row + 1, col, this.color)) {
            possibleMoves.add(Board.getSquare(row + 1, col));
        }

        if (isValidSquare(row - 1, col, this.color)) {
            possibleMoves.add(Board.getSquare(row - 1, col));
        }

        if (isValidSquare(row, col + 1, this.color)) {
            possibleMoves.add(Board.getSquare(row, col + 1));
        }

        if (isValidSquare(row, col - 1, this.color)) {
            possibleMoves.add(Board.getSquare(row, col - 1));
        }

        if (isValidSquare(row + 1, col  + 1, this.color)) {
            possibleMoves.add(Board.getSquare(row + 1, col + 1));
        }

        if (isValidSquare(row + 1, col - 1, this.color)) {
            possibleMoves.add(Board.getSquare(row + 1, col - 1));
        }

        if (isValidSquare(row - 1, col + 1, this.color)) {
            possibleMoves.add(Board.getSquare(row - 1, col + 1));
        }

        if (isValidSquare(row - 1, col - 1, this.color)) {
            possibleMoves.add(Board.getSquare(row - 1, col - 1));
        }

        return possibleMoves;
    }
}
