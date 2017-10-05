package main.controller;

import main.model.Board;
import main.model.Move;
import main.model.Square;
import main.model.pieces.Piece;
import main.view.GUI;

import java.util.Scanner;

import static java.lang.Thread.sleep;
import static main.model.Board.isInCheck;
import static main.model.Board.killPiece;
import static main.model.Board.*;

public class ChessGame {

    public static Scanner scan;
    public static int whiteScore = 0;
    public static int blackScore = 0;
    public static boolean donePlaying = false;
    public static boolean gameRunning = false;
    public static boolean whiteTurn = true;

    public static Square src = null;
    public static Square dest = null;
    public static String myColor, enemyColor;
    public static boolean inCheck = false;

    public static Move lastMove = null;

    private static GUI gui;

    public static void main(String[] args) throws Exception {

        scan = new Scanner(System.in);
        Board board = new Board(defaultSize, defaultSize, false);
        Board.printBoard();
        gui = new GUI(8, 8);

        while (!donePlaying) {

            while (gameRunning) {
                sleep(100);
            }
        }
    }

    /**
     * Extracted method to create custom pieces for custom board
     * @param board
     * @param pieceChoice
     * @param xCoord
     * @param yCoord
     * @param color
     */
    public static void createCustomPiece(Board board, String pieceChoice, int xCoord, int yCoord, String color) {
        switch(pieceChoice) {
            case ("pawn"):
                board.createPawn(xCoord, yCoord, color);
                break;

            case ("rook"):
                board.createRook(xCoord, yCoord, color);
                break;

            case ("knight"):
                board.createKnight(xCoord, yCoord, color);
                break;

            case ("bishop"):
                board.createBishop(xCoord, yCoord, color);
                break;

            case ("king"):
                board.createKing(xCoord, yCoord, color);
                break;

            case ("queen"):
                board.createQueen(xCoord, yCoord, color);
                break;

            case ("frog"):
                board.createFrog(xCoord, yCoord, color);
                break;

            case ("god"):
                board.createGod(xCoord, yCoord, color);
                break;
        }
    }

    /**
     * If a team wins, add a point to their score
     * @param color
     */
    public static void addScoreForEnemyColor(String color) {
        if (color == WHITE) {
            blackScore += 1;
        } else {
            whiteScore += 1;
        }

        System.out.println("Score \n" + "White: " + whiteScore + " Black: " + blackScore);
    }

    /**
     * Alerts the user of checkmate
     * @param color
     */
    private static void alertCheckmate(String color) {
        System.out.println("You're in Checkmate. The End LOL you suck. cya l8r.\n");
        addScoreForEnemyColor(color);

        if (gui != null) {
            gui.alertGameConditions("Checkmate");
        }
    }

    /**
     * Alerts the user of stalemate
     * @param color
     */
    private static void alertStalemate(String color){
        System.out.println("The Game is in Stalemate. It's a tie LOL you suck. cya l8r.\n");
        addScoreForEnemyColor(color);

        if (gui != null) { // For testing purposes
            gui.alertGameConditions("Stalemate");
        }
    }

    /**
     * Alerts the user of check
     * @param color
     */
    private static void alertCheck(String color) {
        System.out.println("BEWARE! Your King is in check!");

        if (gui != null) { // For testing purposes
            gui.alertGameConditions("Check");
        }
    }

    /**
     * Checks for game ending conditions in the game loop
     * @param inCheck
     * @param color
     * @return true if game over
     */

    public static boolean gameConditionsCheck(Boolean inCheck, String color) {
        if (inCheck) {

            // Checks to see if player is in Checkmate
            if (isInCheckmate(color)) {
                alertCheckmate(color);
                return true;
            }

            alertCheck(color);

        } else {

            // When not in Check, see if player is in Stalemate
            if (isInStalemate(color)) {
                alertStalemate(color);
                return true;
            }
        }

        return false;
    }

    /**
     * Set the turns for the color
     */
    private static void setTurns() {

        if (whiteTurn) {
            myColor = WHITE;
            enemyColor = BLACK;
        } else {
            myColor = BLACK;
            enemyColor = WHITE;
        }

        if (gui != null) // For testing purposes
            gui.setTurnLabel();
        System.out.println("It's " + myColor + "'s move.");
    }

    /**
     * Set the src piece to the designated location if valid
     * @param row
     * @param col
     */
    public static boolean setSrcPiece(int row, int col) {

        if (!isValidCoord(row, col)) {
            gui.alertMoveConditions("Coordinates out of bounds. Seriously...");
        } else if (getPiece(row, col) == null) {
            gui.alertMoveConditions("No piece at coordinates. You need glasses.");
        } else {
            Piece piece = getPiece(row, col);

            if (piece.getColor().equals(myColor)) {
                src = Board.getSquare(row, col);
                return true;
            } else {
                gui.alertMoveConditions("No No No! Not your piece. Dumbass.");
            }
        }
        return false;
    }

    /**
     * Set the dest piece to the designated location if valid
     * @param row
     * @param col
     */
    public static boolean setDestSquare(int row, int col) {

        boolean validMove = false;

        Piece srcPiece = src.getPiece();

        if (!Board.isValidCoord(row, col)) {
            gui.alertMoveConditions("Coordinates out of bounds. Are you serious??");
        }
        else if (!srcPiece.getAllPossibleMoves().contains(Board.getSquare(row, col))) {
            gui.alertMoveConditions("Invalid destination. Yo you stupid. Learn the rules.");
        }
        else {
            // Ensure player's move is not in Check
            Piece destPiece = Board.getPiece(row, col);

            if (Board.pseudoMove(destPiece, src.getRow(), src.getCol(), row, col, myColor)) {
                validMove = true;
            } else {
                gui.alertMoveConditions("Invalid move! You're in check! I D I O T.");
            }
        }

        if (validMove) {
            makeMove(row, col);
            return true;
        }

        src = null;
        return false;
    }

    /**
     * Moves piece src to the row and col designated, killing the piece there if it exists
     * @param row
     * @param col
     */
    public static void makeMove(int row, int col) {

        lastMove = new Move(src, row, col);

        // If a valid move is set, make the move and switch the turns
        if (Board.getPiece(row, col) != null) {
            killPiece(row, col);
        }

        Board.movePiece(src.getRow(), src.getCol(), row, col);

        inCheck = isInCheck(enemyColor);

        //Switch turns
        whiteTurn = !whiteTurn;
        setTurns();

        src = null;
        dest = null;

        if (gameConditionsCheck(inCheck, myColor)) {
            gameRunning = false;
            inCheck = false;
        }

        Board.printBoard();
    }

    /**
     * Allows player to undo move before opponent makes a move
     */
    public static boolean undoMove() {

        if (lastMove != null) {

            // Moves srcPiece from current dest to old location
            Board.movePiece(lastMove.destRow, lastMove.destCol, lastMove.srcRow, lastMove.srcCol);
            if (lastMove.destPiece != null) {
                Board.restorePiece(lastMove.destRow, lastMove.destCol, lastMove.destPiece);
            }
            lastMove = null;

            whiteTurn = !whiteTurn;
            setTurns();
            return true;
        }

        return false; // If last move is null, you don't do anything. Either first move or move was already undone.
    }

    /**
     * Initializes game starting values
     */

    public static void initGame() {

        whiteTurn = true;
        gameRunning = true;
        setTurns();
    }
}

