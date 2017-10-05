package main.model.pieces;

import main.model.Board;
import main.model.Square;

import javax.swing.*;
import java.util.ArrayList;

public class Bishop extends Piece {

    /**
     * Constructor for Bishop
     */
    public Bishop(int row, int col, String color) {

        this.square = Board.getSquare(row, col);
        this.color = color;
        this.type = "B";
        this.img = new ImageIcon(getClass().getResource("/resources/images/" + color + "-Bishop.png"));

    }

    /**
     * Returns a list of all possible valid moves for Bishop
     */
    @Override
    public ArrayList<Square> getAllPossibleMoves() {

        ArrayList<Square> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(allOneDirectionMoves(1, 1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(-1, 1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(1, -1, this.color));
        possibleMoves.addAll(allOneDirectionMoves(-1, -1, this.color));

        return possibleMoves;

    }

}
