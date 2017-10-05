package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

import static main.model.Board.isValidSquare;

public class Frog extends Piece {

    /**
     * Constructor for Frog
     */
    public Frog(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "F";
        this.img = new ImageIcon(getClass().getResource("/resources/images/Frog.png"));

    }

    /**
     * Returns a list of all possible valid moves for Frog
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {
        int row = square.getRow();
        int col = square.getCol();

        ArrayList<Square> possibleMoves = new ArrayList<>();

        if (isValidSquare(row + 2, col, this.color)) {
            possibleMoves.add(Board.getSquare(row + 2, col));
        }

        if (isValidSquare(row - 2, col, this.color)) {
            possibleMoves.add(Board.getSquare(row - 2, col));
        }

        if (isValidSquare(row, col + 2, this.color)) {
            possibleMoves.add(Board.getSquare(row, col + 2));
        }

        if (isValidSquare(row, col - 2, this.color)) {
            possibleMoves.add(Board.getSquare(row, col - 2));
        }

        if (isValidSquare(row + 2, col  + 2, this.color)) {
            possibleMoves.add(Board.getSquare(row + 2, col + 2));
        }

        if (isValidSquare(row + 2, col - 2, this.color)) {
            possibleMoves.add(Board.getSquare(row + 2, col - 2));
        }

        if (isValidSquare(row - 2, col + 2, this.color)) {
            possibleMoves.add(Board.getSquare(row - 2, col + 2));
        }

        if (isValidSquare(row - 2, col - 2, this.color)) {
            possibleMoves.add(Board.getSquare(row - 2, col - 2));
        }

        return possibleMoves;
    }
}
