package main;


import main.controller.ChessGame;
import main.model.Board;
import main.model.Square;
import main.model.pieces.Piece;
import org.junit.Test;

import java.util.Scanner;

import static main.model.Board.BLACK;
import static main.model.Board.WHITE;
import static org.junit.Assert.*;

public class ChessGameTest {

    /**
     * Ensures the createCustomPiece method actually creates the piece on the board
     * @throws Exception
     */
    @Test
    public void validCreateCustomPiece() throws Exception {

        Board board = new Board(8, 8, true);

        String pieceChoice = "pawn";
        int xCoord = 5;
        int yCoord = 5;
        String color = BLACK;
        ChessGame.createCustomPiece(board, pieceChoice, xCoord, yCoord, color);
        assertNotNull(Board.getPiece(xCoord, yCoord));
    }

    /**
     * Ensures the setSrcPiece method correctly sets the src variable to the correct value
     * @throws Exception
     */
    @Test
    public void validSetSrc() throws Exception {

        Board board = new Board(8, 8, false);
        ChessGame.whiteTurn = false;
        ChessGame.myColor = BLACK;

        Square square = Board.getSquare(0, 0);
        ChessGame.setSrcPiece(0, 0);

        assertNotNull(ChessGame.src);
        assertEquals(square, ChessGame.src);
    }

    /**
     * Ensures the setDestSquare method correctly sets the dest variable to the correct value and makes
     * the move if it is a valid move.
     * @throws Exception
     */
    @Test
    public void validDestSquare() throws Exception {

        Board board = new Board(8, 8, false);
        ChessGame.whiteTurn = false;
        ChessGame.myColor = BLACK;

        ChessGame.setSrcPiece(1, 0);
        ChessGame.setDestSquare(2, 0);

        assertNull(Board.getPiece(1, 0));
        assertNotNull(Board.getPiece(2, 0));

        assertNull(ChessGame.src);
        assertNull(ChessGame.dest);

        assertTrue(ChessGame.whiteTurn);
        assertEquals(ChessGame.myColor, WHITE);

        ChessGame.setSrcPiece(6, 0);
        ChessGame.setDestSquare(4, 0);

        assertNull(Board.getPiece(6, 0));
        assertNotNull(Board.getPiece(4, 0));

        assertFalse(ChessGame.whiteTurn);
        assertEquals(ChessGame.myColor, BLACK);
    }

    /**
     * Ensure undoMove actually undos whatever the last move was, restoring any piece that
     * may have been killed.
     * @throws Exception
     */
    @Test
    public void validUndoMove() throws Exception {

        Board board = new Board(8, 8, false);
        ChessGame.whiteTurn = false;
        ChessGame.myColor = BLACK;

        assertNull(ChessGame.lastMove);
        assertFalse(ChessGame.undoMove());

        ChessGame.setSrcPiece(1, 0);
        ChessGame.setDestSquare(2, 0);

        assertNotNull(ChessGame.lastMove);

        assertTrue(ChessGame.undoMove());
        assertNull(Board.getPiece(2, 0));
        assertNotNull(Board.getPiece(1, 0));

    }

    /**
     * Ensures that initGame correctly sets the global variables for the game
     * @throws Exception
     */
    @Test
    public void validInitGame() throws Exception {

        ChessGame.initGame();

        assertTrue(ChessGame.whiteTurn);
        assertEquals(ChessGame.myColor, WHITE);

        assertTrue(ChessGame.gameRunning);

    }

}