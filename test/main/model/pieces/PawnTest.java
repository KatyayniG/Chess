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

public class PawnTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validPawnSetup() throws Exception {

        int row = 1;
        int col = 0;
        String color = BLACK;
        String type = "P";
        Piece pawn = Board.getPiece(row, col);

        assertEquals(color, pawn.getColor());
        assertEquals(row, pawn.getSquare().getRow());
        assertEquals(col, pawn.getSquare().getCol());
        assertEquals(type, pawn.getType());


    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece pawn = Board.getPiece(1, 0);
        possibleMoves.add(new Square(2, 0));
        possibleMoves.add(new Square(3, 0));

        ArrayList<Square> moves = pawn.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}
