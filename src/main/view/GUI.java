package main.view;

import main.model.Board;
import main.controller.ChessGame;
import main.model.Square;
import main.model.pieces.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

import static main.controller.ChessGame.*;


public class GUI {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel board;
    public JButton[][] grid;
    public int length;
    public int width;
    public boolean gameHasStarted = false;
    public String player1;
    public String player2;

    private JLabel whiteLabel;
    private JLabel blackLabel;
    private JLabel turnLabel = new JLabel("", SwingConstants.CENTER);

    private final String LABELS = "01234567";

    /**
     * Constructor for GUI
     * @param length of board
     * @param width of board
     */
    public GUI(int length, int width){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }

        this.length = length;
        this.width = width;

        JFrame window = new JFrame("Katyayni's Chess Game");
        window.setSize(1000, 1000);

        board = new JPanel(new GridLayout(0, 9));
        board.setBorder(new LineBorder(Color.BLACK));
        board.setBackground(Color.LIGHT_GRAY);
        gui.add(board);

        // Initialize items that will go on board
        initializeGrid();
        addLabelsAndPieces();
        getPlayerNames();
        promptDefaultBoard();
        startGameButton();
        restartGameButton();
        forfeitGameButton();
        undoGameButton();
        board.add(turnLabel);
        addScoreLabels();

        window.add(gui);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
        window.setMinimumSize(window.getSize());
        window.setVisible(true);
    }

    /**
     * Getting the player names
     */
    private void getPlayerNames() {
        player1 = JOptionPane.showInputDialog(null, "What is Player 1 name?", "Player 1", JOptionPane.INFORMATION_MESSAGE);
        player2 = JOptionPane.showInputDialog(null, "What is Player 2 name?", "Player 2", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompt the user about using a default board or not
     */

    private void promptDefaultBoard() {
        int reply = JOptionPane.showConfirmDialog(null, "Start with default board?", "Board Type", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION) {

            Object frame = null;
            int rowNum = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of rows", "Rows", JOptionPane.INFORMATION_MESSAGE, null, null, "8").toString());
            int colNum = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of cols", "Cols", JOptionPane.INFORMATION_MESSAGE, null, null, "8").toString());
            Board board = new Board(rowNum, colNum, true);
            String piece = JOptionPane.showInputDialog(null, "Enter which piece you would like to set or enter done when finished:", "Custom Board", JOptionPane.INFORMATION_MESSAGE);
            piece = piece.toLowerCase();

            while (!piece.equals("done")) {

                addCustomPiece(board, piece);
                piece = JOptionPane.showInputDialog(null, "Enter which piece you would like to set or enter done when finished:", "Custom Board", JOptionPane.INFORMATION_MESSAGE);
                piece = piece.toLowerCase();
            }

            updateGUI();

        }
    }

    /**
     * Gets input from the user and creates the piece they enter
     * @param board
     * @param piece
     */
    private void addCustomPiece(Board board, String piece) {

        int pieceRow = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter which row you want to put the chosen piece at:", "Custom Board", JOptionPane.INFORMATION_MESSAGE));
        int pieceCol = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter which col you want to put the chosen piece at:", "Custom Board", JOptionPane.INFORMATION_MESSAGE));
        String color = JOptionPane.showInputDialog(null, "Enter which color you would like your piece to be:", "Custom Board", JOptionPane.INFORMATION_MESSAGE);
        color = color.toLowerCase();
        ChessGame.createCustomPiece(board, piece, pieceRow, pieceCol, color);
    }

    /**
     * Initializes grid of buttons for pieces on the board
     */
    private void initializeGrid() {

        grid = new JButton[length][width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = new JButton();
                button.setOpaque(true);

                if ((i + j) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.PINK);
                }
                button.setBorderPainted(false);

                addActionListener(button, i, j);

                addPiece(button, i, j);
                grid[i][j] = button;
            }
        }
    }

    /**
     * Actions for when a button on the board is clicked
     * @param button
     * @param row
     * @param col
     */
    private void addActionListener(JButton button, int row, int col) {
        button.addActionListener(e -> {

            if (ChessGame.gameRunning) {
                if (ChessGame.src == null) {
                    if (ChessGame.setSrcPiece(row, col)) {
                        highlightPossibleMoves();
                    }
                } else {
                    ChessGame.setDestSquare(row, col);
                    updateGUI();
                }
            }
        });
    }

    /**
     * Highlights the selected piece and all of its possible moves
     */
    private void highlightPossibleMoves() {

        int srcRow = ChessGame.src.getRow();
        int srcCol = ChessGame.src.getCol();
        grid[srcRow][srcCol].setBackground(Color.ORANGE);

        Piece srcPiece = ChessGame.src.getPiece();
        ArrayList<Square> possibleMoves = srcPiece.getAllPossibleMoves();

        for (Square square : possibleMoves) {
            int row = square.getRow();
            int col = square.getCol();

            grid[row][col].setBackground(Color.cyan);
        }
    }

    /**
     * Updates the GUI to reflect board actions
     */
    private void updateGUI() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = grid[i][j];

                addPiece(button, i, j);

                // Resets highlighted square colors
                if ((i + j) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.PINK);
                }

                grid[i][j] = button;
            }
        }

        if (whiteLabel != null) {
            updateScoreLabels();
        }
    }

    /**
     * Method to create button
     * @return JButton
     */
    private JButton createButton(String buttonText) {
        JButton button = new JButton();
        button.setBorder(new LineBorder(Color.BLACK));
        button.setBackground(Color.LIGHT_GRAY);
        button.setText(buttonText);
        board.add(button);
        return button;
    }

    /**
     * Method that sets up the start game button
     */
    private void startGameButton() {
        JButton button = createButton("Start Game");
        startGameLogic(button);
    }

    /**
     * Method for actual start game logic
     * @param button
     */
    public void startGameLogic(JButton button) {
        button.addActionListener(e -> {
            if (!gameHasStarted) {
                gameHasStarted = true;
                ChessGame.initGame();
            }
        });
    }

    /**
     * Gets the choice of each player when asked to restart
     * @param player
     * @return answer
     */
    private int playerRestartChoice(String player) {
        int replyPlayer = JOptionPane.showConfirmDialog(null, "Does " + player + " agree to restart?", "Restart", JOptionPane.YES_NO_OPTION);
        return replyPlayer;
    }

    /**
     * Method for actual restart game logic
     */
    private void restartGameLogic() {

        Board.cleanBoard();
        Board.populateBoard();

        ChessGame.gameRunning = false;
        gameHasStarted = false;
        updateGUI();

        gameHasStarted = true;
        ChessGame.initGame();
    }

    /**
     * Method to restart the game if both players agree to do so
     */
    private void restartGameButton() {
        JButton button = createButton("Restart Game");
        button.addActionListener(e -> {
            int replyPlayer1 = playerRestartChoice(player1);
            int replyPlayer2 = playerRestartChoice(player2);
            if ((replyPlayer1 == JOptionPane.YES_OPTION) && (replyPlayer2 == JOptionPane.YES_OPTION)) {
                restartGameLogic();
            } else {
                JOptionPane.showConfirmDialog(null, "Both players don't agree. No restart.", "Restart Alert", JOptionPane.OK_CANCEL_OPTION);
            }
        });
    }

    /**
     * Method to forfeit the game at any time
     */
    private void forfeitGameButton() {
        JButton button = createButton("Forfeit Game");
        button.addActionListener(e -> {
            Board.cleanBoard();
            Board.populateBoard();
            ChessGame.addScoreForEnemyColor(myColor);
            ChessGame.gameRunning = false;
            gameHasStarted = false;
            updateGUI();
        });
    }

    /**
     * Method to create the button for undoing a move
     */
    private void undoGameButton() {
        JButton button = createButton("Undo Move");
        button.addActionListener(e -> {
            if(ChessGame.undoMove()){
                updateGUI();
            } else {
                JOptionPane.showConfirmDialog(null, "Cannot undo move", "Undo Alert", JOptionPane.OK_CANCEL_OPTION);
            }
        });
    }

    /**
     * Add the labels for the scores
     */
    private void addScoreLabels() {
        whiteLabel = new JLabel(player1 + ": " + ChessGame.whiteScore);
        blackLabel = new JLabel(player2 + ": " + ChessGame.blackScore);

        board.add(whiteLabel);
        board.add(blackLabel);
    }

    /**
     * Update the labels for the scores
     */
    private void updateScoreLabels() {
        whiteLabel.setText(player1 + ": " + ChessGame.whiteScore);
        blackLabel.setText(player2 + ": " + ChessGame.blackScore);
    }

    /**
     * Method to alert user about a game condition (Check, Checkmate, Stalemate)
     * @param string
     */
    public void alertGameConditions(String string) {
        JOptionPane.showConfirmDialog(null, "You are in " + string, "Game Condition Alert", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Method to alert user about invalid moves
     * @param string
     */
    public void alertMoveConditions(String string) {
        JOptionPane.showConfirmDialog(null, string, "Move Alert", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Add the turn label to the chess GUI
     */
    public void setTurnLabel() {

        turnLabel.setText("It's " + myColor + "'s turn.");

    }

    /**
     * Method to add a button for a piece on the board
     * @param button
     * @param i = row
     * @param j = column
     */
    private void addPiece(JButton button, int i, int j) {

        Piece piece = Board.getPiece(i, j);
        if (piece != null) {
            ImageIcon img = Board.getPiece(i, j).getImage();
            button.setIcon(img);
        } else {
            button.setIcon(null);
        }
    }

    /**
     * Adds vertical and horizontal labelling of board
     */
    private void addLabelsAndPieces() {
        // Add horizontal labels
        // i = row
        // j = col
        board.add(new JLabel(""));
        for (int i = 0; i < length; i++) {
            board.add(new JLabel(LABELS.substring(i, i + 1), SwingConstants.CENTER));
        }

        // Add vertical labels and pieces
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0) {
                    board.add(new JLabel(LABELS.substring(i, i + 1), SwingConstants.CENTER));
                }
                board.add(grid[i][j]);
            }
        }
    }

}