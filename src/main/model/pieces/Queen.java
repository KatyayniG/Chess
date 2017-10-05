package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

public class Queen extends Piece {

    /**
     * Constructor for Queen
     */
    public Queen(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "Q";
        this.img = new ImageIcon(getClass().getResource("/resources/images/" + color + "-Queen.png"));

    }

    /**
     * Returns a list of all possible valid moves for Queen
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {

        ArrayList<Square> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(allOneDirectionMoves(1, 1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(-1, 1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(1, -1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(-1, -1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(1, 0, this.color));
        possibleMoves.addAll(allOneDirectionMoves(0, 1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(-1, 0, this.color));
        possibleMoves.addAll(allOneDirectionMoves(0, -1, this.color));

        return possibleMoves;
    }

}
