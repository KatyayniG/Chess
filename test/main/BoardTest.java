package main;

import main.controller.ChessGame;
import main.model.Board;
import main.model.Square;
import main.model.pieces.King;
import main.model.pieces.Piece;
import main.view.GUI;
import org.junit.Test;

import static main.model.Board.*;
import static org.junit.Assert.*;

public class BoardTest {

    /**
     * Ensure the default board is initialized correctly with pieces at the correct locations
     * @throws Exception
     */
    @Test
    public void validBoardSetup() throws Exception {
        Board board = new Board(8, 8, false);

        for (int i = 0; i < 8; i++) {
            Piece rowBlackPawn = Board.getPiece(1, i);
            Piece rowWhitePawn = Board.getPiece(6, i);
            assertEquals(rowBlackPawn.getType(), "P");
            assertEquals(rowWhitePawn.getType(), "P");
        }

        for (int i = 0; i < 8; i++) {
            Piece blackPiece = Board.getPiece(0, i);
            Piece whitePiece = Board.getPiece(7, i);

            if (i == 0 || i == 7) {
                assertEquals(blackPiece.getType(), "R");
                assertEquals(whitePiece.getType(), "R");
            } else if (i == 1 || i == 6) {
                assertEquals(blackPiece.getType(), "N");
                assertEquals(whitePiece.getType(), "N");
            } else if (i == 2 || i == 5) {
                assertEquals(blackPiece.getType(), "B");
                assertEquals(whitePiece.getType(), "B");
            } else if (i == 3) {
                assertEquals(blackPiece.getType(), "Q");
                assertEquals(whitePiece.getType(), "Q");
            } else {
                assertEquals(blackPiece.getType(), "K");
                assertEquals(whitePiece.getType(), "K");
            }
        }
    }

    /**
     * Ensures each of the squares in the grid are initialized correctly
     * @throws Exception
     */
    @Test
    public void validGridSetup() throws Exception {
        Board board = new Board(8, 8, false);

        Square[][] grid = Board.getGrid();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = grid[i][j];
                assertNotNull(square);
                assertEquals(square.getRow(), i);
                assertEquals(square.getCol(), j);
            }
        }
    }

    /**
     * Ensures we can get a piece through a square correctlys
     * @throws Exception
     */
    @Test
    public void validGetPiece() throws Exception {
        Board board = new Board(8, 8, false);
        Piece piece = Board.getSquare(0, 0).getPiece();
        assertEquals(piece.getType(), "R");
    }

    /**
     * Ensures we can set a piece correctly on the board
     * @throws Exception
     */
    @Test
    public void validSetPiece() throws Exception {
        Board board = new Board(8, 8, false);
        Piece piece = new King(5, 5, BLACK);
        Board.setPiece(5, 5, piece);

        assertEquals(piece.getType(), "K");
        assertNotNull(piece.getSquare());
    }

    /**
     * Ensures we can remove a piece correctly on the board
     * @throws Exception
     */
    @Test
    public void validRemovePiece() throws Exception {
        Board board = new Board(8, 8, false);
        Piece piece = new King(5, 5, BLACK);
        Board.setPiece(5, 5, piece);

        Board.removePiece(5, 5);
        assertNull(piece.getSquare());
    }

    /**
     * Ensures we can move a piece and it's updated on the board correctly
     * @throws Exception
     */
    @Test
    public void validMovePiece() throws Exception {
        Board board = new Board(8, 8, false);
        Piece piece = new King(5, 5, BLACK);
        Board.setPiece(5, 5, piece);

        Board.movePiece(5, 5, 5, 6);

        Square srcSquare = Board.getSquare(5, 5);
        Square destSquare = Board.getSquare(5, 6);

        assertNull(srcSquare.getPiece());
        assertNotNull(destSquare.getPiece());
        assertEquals(destSquare.getPiece(), piece);

    }

    /**
     * Ensures hasEnemy returns True and False correctly
     * @throws Exception
     */
    @Test
    public void validHasEnemy() throws Exception {
        Board board = new Board(8, 8, false);

        int row = 5;
        int col = 5;

        Piece piece = new King(row, col, WHITE);
        Board.setPiece(row, col, piece);

        assertTrue(Board.hasEnemy(row, col, BLACK));
    }

    /**
     * Kills and restores a white piece, making sure the board and list of white pieces are updated
     * @throws Exception
     */
    @Test
    public void validKillAndRestoreWhitePiece() throws Exception {
        Board board = new Board(8, 8, false);
        Piece whitePiece = Board.getPiece(0, 1);

        String color = whitePiece.getColor();

        int numPiecesBefore = Board.getPlayerPieces(color).size();

        Board.killPiece(0, 1);
        int numPiecesAfter  = Board.getPlayerPieces(color).size();

        assertEquals(numPiecesBefore, numPiecesAfter + 1);

        Board.restorePiece(0, 1, whitePiece);
        numPiecesAfter = Board.getPlayerPieces(color).size();

        assertEquals(numPiecesBefore, numPiecesAfter);
    }

    /**
     * Kills and restores a black piece, making sure the board and list of black pieces are updated
     * @throws Exception
     */
    @Test
    public void validKillAndRestoreBlackPiece() throws Exception {
        Board board = new Board(8, 8, false);
        Piece blackPiece = Board.getPiece(7, 1);

        String color = blackPiece.getColor();

        int numPiecesBefore = Board.getPlayerPieces(color).size();

        Board.killPiece(7, 1);
        int numPiecesAfter  = Board.getPlayerPieces(color).size();

        assertEquals(numPiecesBefore, numPiecesAfter + 1);

        Board.restorePiece(7, 1, blackPiece);
        numPiecesAfter = Board.getPlayerPieces(color).size();

        assertEquals(numPiecesBefore, numPiecesAfter);
    }

    /**
     * Ensures the cleanBoard method cleans the entire board and all squares correctly
     * @throws Exception
     */
    @Test
    public void validCleanBoard() throws Exception {
        Board board = new Board(8, 8, false);

        int row = 5;
        int col = 5;

        Piece piece = new King(row, col, BLACK);
        Board.setPiece(row, col, piece);
        Square square = Board.getSquare(row, col);

        Board.cleanBoard();
        assertNull(square.getPiece());


    }

    /**
     * Ensures that isInCheck returns false when not in check and true when in a check scenario
     * @throws Exception
     */
    @Test
    public void validCheck() throws Exception {
        Board board = new Board(8, 8, false);

        Piece whiteKing = Board.getPiece(0, 4);
        Board.movePiece(0, 4, 3, 3);

        assertFalse(Board.isInCheck(BLACK));

        Piece blackRook = Board.getPiece(7, 0);
        Board.movePiece(7, 0, 3, 6);

        assertTrue(Board.isInCheck(BLACK));

//        Board.printBoard();
    }

    // This test also tests pseudoMove as it's called within Checkmate

    /**
     * Ensures that isInCheckmate returns false when not in a checkmate scenario and true when in a
     * checkmate scenario. Also tests pseudoMove which is called by isInCheckmate.
     * @throws Exception
     */
    @Test
    public void validCheckmate() throws Exception {
        Board board = new Board(8, 8, false);

        Board.printBoard();

        Piece whitePawnA = Board.getPiece(1, 5);
        Board.movePiece(1, 5, 2, 5);

        Piece whitePawnB = Board.getPiece(1, 6);
        Board.movePiece(1, 6, 3, 6);

        Piece blackPawn  = Board.getPiece(6, 4);
        Board.movePiece(6, 4, 4, 4);

        assertFalse(Board.isInCheckmate(BLACK));
        assertFalse(ChessGame.gameConditionsCheck(false, BLACK));
        assertFalse(ChessGame.gameConditionsCheck(true, BLACK));

        Piece blackQueen = Board.getPiece(7, 3);
        Board.movePiece(7, 3, 3, 7);

        assertTrue(Board.isInCheckmate(BLACK));
        assertTrue(ChessGame.gameConditionsCheck(true, BLACK));

        Board.printBoard();
    }

    /**
     * Ensures that isInStalemate returns false when not in a stalemate scenario and true when in
     * a stalemate scenario.
     * @throws Exception
     */
    @Test
    public void validStalemate() throws Exception {
        Board board = new Board(8, 8, false);
        Board.cleanBoard();

        board.createKing(0, 7, WHITE);
        board.createKing(1, 5, BLACK);

        assertFalse(Board.isInStalemate(WHITE));

        board.createQueen(2, 6, BLACK);

//        Board.printBoard();

        assertTrue(Board.isInStalemate(WHITE));
        assertTrue(ChessGame.gameConditionsCheck(false, WHITE));
    }

    /**
     * Ensures the toString method returns the correct values
     * @throws Exception
     */
    @Test
    public void validToString() throws Exception {
        int row = 5;
        int col = 5;
        String string = "(5, 5)";
        Square square = new Square(row,col);

        assertEquals(string, square.toString());

    }
}
