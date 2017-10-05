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

public class BishopTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validBishopSetup() throws Exception {

        int row = 0;
        int col = 5;
        String color = BLACK;
        String type = "B";
        Board board = new Board(8,8, false);
        Piece bishop = Board.getPiece(row, col);

        assertEquals(color, bishop.getColor());
        assertEquals(row, bishop.getSquare().getRow());
        assertEquals(col, bishop.getSquare().getCol());
        assertEquals(type, bishop.getType());

    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        Board board = new Board(8, 8, false);
        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece bishop = new Bishop(2, 5, BLACK);

        possibleMoves.add(new Square(3, 4));
        possibleMoves.add(new Square(3, 6));
        possibleMoves.add(new Square(4, 3));
        possibleMoves.add(new Square(4, 7));
        possibleMoves.add(new Square(5, 2));
        possibleMoves.add(new Square(6, 1));

        ArrayList<Square> moves = bishop.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}


