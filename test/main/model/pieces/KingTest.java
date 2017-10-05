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

public class KingTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validKingSetup() throws Exception {

        int row = 0;
        int col = 4;
        String color = BLACK;
        String type = "K";
        Piece king = Board.getPiece(row, col);

        assertEquals(color, king.getColor());
        assertEquals(row, king.getSquare().getRow());
        assertEquals(col, king.getSquare().getCol());
        assertEquals(type, king.getType());

    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece king = new King(3, 1, BLACK);

        possibleMoves.add(new Square(3, 0));
        possibleMoves.add(new Square(3, 2));
        possibleMoves.add(new Square(2, 0));
        possibleMoves.add(new Square(2, 1));
        possibleMoves.add(new Square(2, 2));
        possibleMoves.add(new Square(4, 0));
        possibleMoves.add(new Square(4, 1));
        possibleMoves.add(new Square(4, 2));

        ArrayList<Square> moves = king.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}
