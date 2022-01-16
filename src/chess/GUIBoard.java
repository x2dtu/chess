package chess;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GUIBoard {
    public static final int CELL_SIZE = 80;
    private Scene scene;
    private GridPane grid;
    private Board board;
    private static Piece selectedPiece = null;
    private StackPane totalPane;

    public GUIBoard(Board board) {
        init(board);
    }

    private void init(Board board) {
        this.board = board;
        totalPane = new StackPane();
        grid = new GridPane();
        totalPane.getChildren().add(grid);
        initGrid();
        scene = new Scene(totalPane, CELL_SIZE * Board.WIDTH, CELL_SIZE * Board.WIDTH);
    }

    public Scene getScene() {
        return scene;
    }

    private void initGrid() {
        for (int rank = 0; rank < Board.WIDTH; rank++) {
            for (int file = 0; file < Board.WIDTH; file++) {
                StackPane square = new Square(this, file, rank).getStackPane();
                GridPane.setConstraints(square, file, rank);
                grid.getChildren().add(square);
            }
        }
        initPieces();
    }

    public void initPieces() {
        for (Piece p : board.getPieces()) {
            int index = p.getRank() * Board.WIDTH + p.getFile();
            StackPane currPane = (StackPane)grid.getChildren().get(index);
            currPane.getChildren().add(new ImageView(p.getImage()));
        }
    }

    public static Piece getSelectedPiece() {
        return selectedPiece;
    }

    public static void setSelectedPiece(Piece piece) {
        selectedPiece = piece;
    } 

    public GridPane getGrid() {
        return grid;
    }

    public Board getBoard() {
        return board;
    }

    public StackPane getTotalPane() {
        return totalPane;
    }

    public void resetBoard() {
        board = new Board();
        totalPane.getChildren().clear();
        grid = new GridPane();
        totalPane.getChildren().add(grid);
        initGrid();
    }
    
}
