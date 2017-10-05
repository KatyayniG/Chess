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

public class GodTest {

    /**
     * Ensures the piece is created and its properties are set correctly
     * @throws Exception
     */
    @Test
    public void validGodSetup() throws Exception {

        int row = 5;
        int col = 5;
        String color = BLACK;
        String type = "G";
        Board board = new Board(8,8, false);
        Piece god = new God(row, col, color);
        board.createGod(row, col, color);
        Board.setPiece(row, col, god);

        assertEquals(color, god.getColor());
        assertEquals(row, god.getSquare().getRow());
        assertEquals(col, god.getSquare().getCol());
        assertEquals(type, god.getType());

    }

    /**
     * Ensures that the piece's getAllPossibleMoves returns the correct list of squares
     * @throws Exception
     */
    @Test
    public void getAllPossibleMoves() throws Exception {

        Board board = new Board(8,8, false);
        ArrayList<Square> possibleMoves = new ArrayList<Square>();

        Piece god = new God(2, 0, BLACK);

        for (int i = 2; i < Board.getRows(); i++) {
            for (int j = 0; j < Board.getCols(); j++) {
                if (!(i == 2 && j == 0)) {
                    possibleMoves.add(Board.getSquare(i, j));
                }
            }
        }

        ArrayList<Square> moves = god.getAllPossibleMoves();

        Collections.sort(possibleMoves);
        Collections.sort(moves);

        assertArrayEquals(possibleMoves.toArray(), moves.toArray());
    }

}
