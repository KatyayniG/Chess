package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

import static main.model.Board.*;

public class Pawn extends Piece {

    /**
     * Constructor for Pawn
     */
    public Pawn(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "P";
        this.img = new ImageIcon(getClass().getResource("/resources/images/" + color + "-Pawn.png"));

    }

    /**
     * Returns a list of all possible valid moves for Pawn
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {

        int row = square.getRow();
        int col = square.getCol();

        ArrayList<Square> possibleMoves = new ArrayList<>();

        int newRow, twoStepRow;
        if (this.color == BLACK) {
            newRow = row + 1;
            twoStepRow = row + 2;
        } else {
            newRow = row - 1;
            twoStepRow = row - 2;
        }

        // Check square in front of pawn
        if (isValidCoord(newRow, col) && isSquareFree(newRow, col)) {
            possibleMoves.add(Board.getSquare(newRow, col));

            // Checks for double step for pawns first movement if square in front of pawn is free
            if (((row == 1 && this.color == BLACK) || (row == 6 && this.color != BLACK)) && isSquareFree(twoStepRow, col)) {
                possibleMoves.add(Board.getSquare(twoStepRow, col));
            }
        }
        // Check diagonals in front of pawn for enemies
        if (isValidCoord(newRow, col + 1) && hasEnemy(newRow, col + 1, color)) {
            possibleMoves.add(Board.getSquare(newRow, col + 1));
        }
        if (isValidCoord(newRow, col - 1) && hasEnemy(newRow, col - 1, color)) {
            possibleMoves.add(Board.getSquare(newRow, col - 1));
        }

        return possibleMoves;
    }
}
