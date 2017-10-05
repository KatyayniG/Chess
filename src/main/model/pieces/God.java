package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

import static main.model.Board.*;

public class God extends Piece {

    /**
     * Constructor for God
     */
    public God(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "G";
        this.img = new ImageIcon(getClass().getResource("/resources/images/God.png"));

    }

    /**
     * Returns a list of all possible valid moves for God
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {

        ArrayList<Square> possibleMoves = new ArrayList<>();

        for (int i = 0; i < Board.getRows(); i++) {
            for (int j = 0; j < Board.getCols(); j++) {
                if (isValidSquare(i, j, this.color) && !(this.square.getRow() == i && this.square.getCol() == j)) {
                    possibleMoves.add(Board.getSquare(i, j));
                }
            }
        }

        return possibleMoves;
    }
}
