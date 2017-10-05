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

public class KnightTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validKnightSetup() throws Exception {

        int row = 0;
        int col = 1;
        String color = BLACK;
        String type = "N";
        Piece knight = Board.getPiece(row, col);

        assertEquals(color, knight.getColor());
        assertEquals(row, knight.getSquare().getRow());
        assertEquals(col, knight.getSquare().getCol());
        assertEquals(type, knight.getType());

    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece knight = new Knight(3, 0, BLACK);

        possibleMoves.add(new Square(2, 2));
        possibleMoves.add(new Square(5, 1));
        possibleMoves.add(new Square(4, 2));

        ArrayList<Square> moves = knight.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}
