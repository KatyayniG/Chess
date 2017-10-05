package main.model.pieces;

import main.model.Board;
import main.model.Square;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static main.model.Board.BLACK;
import static main.model.Board.WHITE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class QueenTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validQueenSetup() throws Exception {

        int row = 0;
        int col = 3;
        String color = BLACK;
        String type = "Q";
        Piece queen = Board.getPiece(row, col);

        assertEquals(color, queen.getColor());
        assertEquals(row, queen.getSquare().getRow());
        assertEquals(col, queen.getSquare().getCol());
        assertEquals(type, queen.getType());

    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece queen = new Queen(2, 0, BLACK);

        possibleMoves.add(new Square(3, 0));
        possibleMoves.add(new Square(4, 0));
        possibleMoves.add(new Square(5, 0));
        possibleMoves.add(new Square(6, 0));
        possibleMoves.add(new Square(2, 1));
        possibleMoves.add(new Square(2, 2));
        possibleMoves.add(new Square(2, 3));
        possibleMoves.add(new Square(2, 4));
        possibleMoves.add(new Square(2, 5));
        possibleMoves.add(new Square(2, 6));
        possibleMoves.add(new Square(2, 7));
        possibleMoves.add(new Square(3, 1));
        possibleMoves.add(new Square(4, 2));
        possibleMoves.add(new Square(5, 3));
        possibleMoves.add(new Square(6, 4));

        ArrayList<Square> moves = queen.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}
