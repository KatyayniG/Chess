package main.model;

import main.model.pieces.*;

import java.util.ArrayList;
import java.util.Objects;

public class Board {

    private static int rows;
    private static int cols;
    private static Square[][] grid;
    private static ArrayList<Piece> blackPieces;
    private static ArrayList<Piece> whitePieces;
    public static String WHITE = "white";
    public static String BLACK = "black";
    public static int defaultSize = 8;

    /**
     * Constructor for a board with dimensions. Initializes list of team pieces.
     */
    public Board(int rows, int cols, Boolean customBoard) {
        this.rows = rows;
        this.cols = cols;
        grid = new Square[rows][cols];

        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();

        createGrid();

        if (!customBoard) {
            populateBoard();
        }
    }

    /**
     * Returns 2D array of squares
     */
    public static Square[][] getGrid() {

        return grid;
    }


    /**
     * Returns number of rows on board
     */
    public static int getRows() {

        return rows;
    }

    /**
     * Returns number of columns on board
     */
    public static int getCols() {

        return cols;
    }

    /**
     * Gets piece from a place on the grid
     */
    public static Piece getPiece(int row, int col) {

        return grid[row][col].getPiece();

    }

    /**
     * Sets a piece onto a place on the grid
     */
    public static void setPiece(int row, int col, Piece piece) {

        grid[row][col].setPiece(piece);

    }

    /**
     * Removes a piece from a place on the grid
     */
    public static Piece removePiece(int row, int col) {

        return grid[row][col].removePiece();

    }

    /**
     * Moves a piece from a source to destination
     */
    public static void movePiece(int srcRow, int srcCol, int destRow, int destCol) {

        Piece srcPiece = removePiece(srcRow, srcCol);
        setPiece(destRow, destCol, srcPiece);

    }

    /**
     * Checks to ensure coordinate is valid on grid
     */
    public static boolean isValidCoord(int row, int col) {

        if ((row < rows && row >= 0) && (col < cols && col >= 0)) {
            return true;
        }

        return false;
    }

    /**
     * Returns a square on the grid
     */
    public static Square getSquare(int row, int col) {

        return grid[row][col];
    }

    /**
     * Checks for if a square on the grid has a piece
     */
    public static boolean isSquareFree(int row, int col) {

        return grid[row][col].isFree();
    }

    /**
     * Checks for a square is occupied by an enemy piece
     */
    public static boolean hasEnemy(int row, int col, String color) {

        if (!grid[row][col].isFree() && !Objects.equals(grid[row][col].getPiece().getColor(), color)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kills the piece by removing it from the board
     * and deletes the piece from its corresponding list
     */
    public static void killPiece(int row, int col) {
        Piece piece = removePiece(row, col);
        if (piece.getColor() == WHITE) {
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
    }

    /**
     * Method to restore piece when conducting 'pseudo moves'
     * for verifying Checkmate moves
     */
    public static void restorePiece(int row, int col, Piece piece) {
        setPiece(row, col, piece);
        if (piece.getColor() == WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    /**
     * Method to check if a square is valid for a piece to move to,
     * given color of the player
     */
    public static boolean isValidSquare(int row, int col, String color) {
        if (isValidCoord(row, col) && (isSquareFree(row, col) || hasEnemy(row, col, color))) {
            return true;
        }

        return false;
    }

    /**
     * Returns list of all pieces corresponding to player color
     */
    public static ArrayList<Piece> getPlayerPieces(String color) {

        if (color == WHITE) {
            return whitePieces;
        } else {
            return blackPieces;
        }
    }

    /**
     * Returns list of all pieces corresponding to enemy color
     */
    public static ArrayList<Piece> getEnemyPieces(String color) {

        if (color == WHITE) {
            return blackPieces;
        } else {
            return whitePieces;
        }
    }

    /**
     * Checks the board for check. Gets your team's King and checks
     * if any enemy pieces can touch my King
     */
    public static boolean isInCheck(String color) {

        ArrayList<Piece> myList = getPlayerPieces(color);
        ArrayList<Piece> enemyList = getEnemyPieces(color);
        Square kingPosition = null;

        // Get the king's position
        for (Piece piece : myList) {
            if (piece.getType() == "K") {
                kingPosition = piece.getSquare();
                break;
            }
        }

        // Check if any enemy piece can target enemy king's position
        for (Piece piece: enemyList) {
            if (piece.getAllPossibleMoves().contains(kingPosition)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for Checkmate to see if there exists any
     * move that gets the player out of Check
     */
    public static boolean isInCheckmate(String color) {

        ArrayList<Piece> myList = getPlayerPieces(color);
        int destRow, destCol, srcRow, srcCol;

        for (Piece piece : myList) {

            Square pieceCoord = piece.getSquare();
            srcRow = pieceCoord.getRow();
            srcCol = pieceCoord.getCol();
            ArrayList<Square> possibleMoves = piece.getAllPossibleMoves();

            for (Square move : possibleMoves) {
                destRow = move.getRow();
                destCol = move.getCol();

                Piece destPiece = getPiece(destRow, destCol);

                if (pseudoMove(destPiece, srcRow, srcCol, destRow, destCol, color)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Method to check if player is in Stalemate.
     * If the player is not in Check but every move
     * would put them in Check
     */
    public static boolean isInStalemate(String color) {
        return isInCheckmate(color);
    }

    /**
     * Removes all pieces from the Board
     */
    public static void cleanBoard() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Square square = grid[i][j];
                if (!square.isFree())
                    killPiece(i, j);
            }
        }
    }

    /**
     * Method to set up grid of squares
     */
    private void createGrid() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Square(i, j);
            }
        }
    }

    /**
     * Populates board with all pieces for standard board
     */
    public static void populateBoard() {

        createPawns();

        createRooks();

        createKnights();

        createBishops();

        createQueens();

        createKings();

    }

    /**
     * Initializes all Pawns on the board
     */
    private static void createPawns() {
        for (int i = 0; i < cols; i++) {
            createPawn(1, i, BLACK);
            createPawn(6, i, WHITE);
        }
    }

    /**
     * Creates one pawn and sets its coordinates & color
     */
    public static void createPawn(int xCoord, int yCoord, String colorChoice) {
        Pawn pawn = new Pawn(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(pawn);
        addPieceToList(pawn);
    }

    /**
     * Initializes all Rooks on the board
     */
    private static void createRooks() {
        createRook(0, 0, BLACK);
        createRook(0, 7, BLACK);

        createRook(7, 0, WHITE);
        createRook(7, 7, WHITE);
    }

    /**
     * Creates one rook and sets its coordinates & color
     */
    public static void createRook(int xCoord, int yCoord, String colorChoice) {
        Rook rook = new Rook(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(rook);
        addPieceToList(rook);
    }

    /**
     * Initializes all Knights on the board
     */
    private static void createKnights() {
        createKnight(0, 1, BLACK);
        createKnight(0, 6, BLACK);

        createKnight(7, 1, WHITE);
        createKnight(7, 6, WHITE);
    }

    /**
     * Creates one knight and sets its coordinates & color
     */
    public static void createKnight(int xCoord, int yCoord, String colorChoice) {
        Knight knight = new Knight(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(knight);
        addPieceToList(knight);
    }

    /**
     * Initializes all Bishops on the board
     */
    private static void createBishops() {
        createBishop(0, 2, BLACK);
        createBishop(0, 5, BLACK);

        createBishop(7, 2, WHITE);
        createBishop(7, 5, WHITE);
    }

    /**
     * Creates one bishop and sets its coordinates & color
     */
    public static void createBishop(int xCoord, int yCoord, String colorChoice) {
        Bishop bishop = new Bishop(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(bishop);
        addPieceToList(bishop);
    }

    /**
     * Initializes all Kings on the board
     */
    private static void createKings() {
        createKing(0, 4, BLACK);
        createKing(7, 4, WHITE);
    }

    /**
     * Creates one king and sets its coordinates & color
     */
    public static void createKing(int xCoord, int yCoord, String colorChoice) {
        King king = new King(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(king);
        addPieceToList(king);
    }

    /**
     * Initializes all Queens on the board
     */
    private static void createQueens() {
        createQueen(0, 3, BLACK);
        createQueen(7, 3, WHITE);
    }

    /**
     * Creates one queen and sets its coordinates & color
     */
    public static void createQueen(int xCoord, int yCoord, String colorChoice) {
        Queen queen = new Queen(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(queen);
        addPieceToList(queen);
    }

    /**
     * Creates one frog and sets its coordinates & color
     */
    public void createFrog(int xCoord, int yCoord, String colorChoice) {
        Frog frog = new Frog(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(frog);
        addPieceToList(frog);
    }

    /**
     * Creates one god and sets its coordinates & color
     */
    public void createGod(int xCoord, int yCoord, String colorChoice) {
        God god = new God(xCoord, yCoord, colorChoice);
        grid[xCoord][yCoord].setPiece(god);
        addPieceToList(god);
    }

    /**
     * Adds each initialized piece to its corresponding list
     */
    public static void addPieceToList(Piece piece) {

        if (piece.getColor() == WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    /**
     * A pseudo-move method to use during checkmate & chessgame
     * Returns true if you can get out of check, false otherwise
     */

    public static boolean pseudoMove(Piece destPiece, int srcRow, int srcCol, int destRow, int destCol, String color) {

        boolean outOfCheck = false;

        if (destPiece != null) {

            killPiece(destRow, destCol);
        }

        movePiece(srcRow, srcCol, destRow, destCol);

        if (!isInCheck(color)) {
            outOfCheck = true;
        }

        movePiece(destRow, destCol, srcRow, srcCol);

        if (destPiece != null) {

            restorePiece(destRow, destCol, destPiece);
        }

        return outOfCheck;
    }

    /**
     * Print out the board in this text format
     *   -----------------
       0 |R|N|B|Q|K|B|N|R|
       1 |P|P|P|P|P|P|P|P|
       2 | | | | | | | | |
       3 | | | | | | | | |
       4 | | | | | | | | |
       5 | | | | | | | | |
       6 |P|P|P|P|P|P|P|P|
       7 |R|N|B|K|Q|B|N|R|
         -----------------
          0 1 2 3 4 5 6 7
     */
    public static void printBoard() {

        String result = "       WHITE        \n";
        result += "   0 1 2 3 4 5 6 7\n";
        result += "   -----------------\n";
        for (int i = 0; i < rows; i++) {
            result += i + " |";
            for (int j = 0; j < cols; j++) {
                Square square = grid[i][j];
                if (!square.isFree()) {
                    Piece piece = square.getPiece();
                    if (piece.getColor() == WHITE) {
                        result += piece.getType();
                    } else {
                        result += piece.getType().toLowerCase();
                    }
                } else {
                    result += " ";
                }

                result += "|";
            }
            result += " " + i;

            result += "\n";
        }
        result += "   -----------------\n";
        result += "   0 1 2 3 4 5 6 7  \n";
        result += "       BLACK        \n";

        System.out.println(result);
    }
}
