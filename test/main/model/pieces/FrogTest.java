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

public class FrogTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validFrogSetup() throws Exception {

        int row = 5;
        int col = 5;
        String color = BLACK;
        String type = "F";
        Board board = new Board(8,8, false);
        board.createFrog(row, col, color);
        Piece frog = Board.getPiece(row, col);

        assertEquals(color, frog.getColor());
        assertEquals(row, frog.getSquare().getRow());
        assertEquals(col, frog.getSquare().getCol());
        assertEquals(type, frog.getType());

    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece frog = new Frog(4, 4, BLACK);

        possibleMoves.add(new Square(4, 6));
        possibleMoves.add(new Square(4, 2));
        possibleMoves.add(new Square(2, 4));
        possibleMoves.add(new Square(6, 4));
        possibleMoves.add(new Square(2, 6));
        possibleMoves.add(new Square(2, 2));
        possibleMoves.add(new Square(6, 6));
        possibleMoves.add(new Square(6, 2));

        ArrayList<Square> moves = frog.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}
