package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

import static main.model.Board.isValidSquare;

public class Knight extends Piece {

    /**
     * Constructor for Knight
     */
    public Knight(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "N";
        this.img = new ImageIcon(getClass().getResource("/resources/images/" + color + "-Horse.png"));

    }

    /**
     * Returns a list of all possible valid moves for Knight
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {

        int row = square.getRow();
        int col = square.getCol();

        ArrayList<Square> possibleMoves = new ArrayList<>();

        // Knight moves in L-shape
        if (isValidSquare(row + 2, col - 1, this.color)) {
            possibleMoves.add(Board.getSquare(row + 2, col - 1));
        }

        if (isValidSquare(row + 2, col + 1, this.color)) {
            possibleMoves.add(Board.getSquare(row + 2, col + 1));
        }

        if (isValidSquare(row - 2, col + 1, this.color)) {
            possibleMoves.add(Board.getSquare(row - 2, col + 1));
        }

        if (isValidSquare(row - 2, col - 1, this.color)) {
            possibleMoves.add(Board.getSquare(row - 2, col - 1));
        }

        if (isValidSquare(row + 1, col + 2, this.color)) {
            possibleMoves.add(Board.getSquare(row + 1, col + 2));
        }

        if (isValidSquare(row + 1, col - 2, this.color)) {
            possibleMoves.add(Board.getSquare(row + 1, col - 2));
        }

        if (isValidSquare(row - 1, col + 2, this.color)) {
            possibleMoves.add(Board.getSquare(row - 1, col + 2));
        }

        if (isValidSquare(row - 1, col - 2, this.color)) {
            possibleMoves.add(Board.getSquare(row - 1, col - 2));
        }

        return possibleMoves;
    }

}
