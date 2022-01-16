package chess;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Square {
    private StackPane stackPane;
    private GUIBoard guiBoard;
    private int file;
    private int rank;

    public Square(GUIBoard guiBoard, int file, int rank) {
        this.file = file;
        this.rank = rank;
        this.guiBoard = guiBoard;
        stackPane = new StackPane();
        stackPane.setPrefSize(GUIBoard.CELL_SIZE, GUIBoard.CELL_SIZE);
        setBackgroundColor(stackPane, file, rank);
        stackPane.setOnMouseClicked(e -> onClick());
    }

    private void setBackgroundColor(StackPane stack, int file, int rank) {
        if (isLightSquare(file, rank)) {
            stack.setStyle("-fx-background-color: #F0D9B5;");
        }
        else {
            stack.setStyle("-fx-background-color: #B58863;");
        }
    }

    private boolean isLightSquare(int file, int rank) {
        return (file + rank) % 2 == 0;
    }

    public void onClick() {
        System.out.println(1);
        if (GUIBoard.getSelectedPiece() != null) {
            resetOldSelectedSquare();
        }
        int last_index = stackPane.getChildren().size() - 1;
        if (last_index >= 0 && stackPane.getChildren().get(last_index).getClass() == Circle.class) {

            if (GUIBoard.getSelectedPiece().getClass() == King.class && 
                Math.abs(GUIBoard.getSelectedPiece().getFile() - file) == 2) {
                    handleCastle();
            }
            else if (GUIBoard.getSelectedPiece().getClass() == Pawn.class && 
                Math.abs(GUIBoard.getSelectedPiece().getFile() - file) == 1 && 
                stackPane.getChildren().size() == 1) {
                    handleEnPassant();
            }
            else if (GUIBoard.getSelectedPiece().getClass() == Pawn.class && 
                (rank == 7 || rank == 0)) {
                    handlePromotion();
                    return;
            }

            // then there is a circle on this stackpane and thus are moving a piece here
            Piece piece = GUIBoard.getSelectedPiece();
            removePieceFromOldSquare(piece);
            piece.move(new Coordinate(file, rank), guiBoard.getBoard()); // move piece on backend Board
            stackPane.getChildren().add(0, new ImageView(piece.getImage())); // add piece at new square

            guiBoard.getBoard().newPly(); // move to opponent's ply
            clearMoveHints();

            if (isCheckMate()) {
                handleCheckmate();
            }
            else if (isStaleMate()) {
                handleStalemate();
            }
            else if (Board.getFiftyMoveRule() == 100) {
                handle50MoveDraw();
            }
            if (!guiBoard.getBoard().addBoardPosition()) {
                handle3FoldRepetition();
            }
        }

        else if (guiBoard.getBoard().getBoard()[file][rank] != null) {
            clearMoveHints();

            // then we are clicking a piece to move it
            Piece piece = guiBoard.getBoard().getBoard()[file][rank];
            if ((piece.getColor() == Color.WHITE && Board.getPly() % 2 == 1) || 
                (piece.getColor() == Color.BLACK && Board.getPly() % 2 == 0)) {
                    return; // we are clicking an enemy piece
            }

            GUIBoard.setSelectedPiece(piece);
            stackPane.setStyle("-fx-background-color: #EE4B2B;");
            for (Coordinate move : piece.getPossibleMoves()) {
                int move_file = move.getFile();
                int move_rank = move.getRank();
                StackPane stack = (StackPane)guiBoard.getGrid().getChildren()
                    .get(move_rank * Board.WIDTH + move_file);
                stack.getChildren().add(moveHint());
            }
        }
        else {
            // clicked a random square
            clearMoveHints();
        }
    }

    private boolean isCheckMate() {
        King whiteKing = guiBoard.getBoard().getKing(Color.WHITE);
        King blackKing = guiBoard.getBoard().getKing(Color.BLACK);
        return (Board.getPly() % 2 == 0 && guiBoard.getBoard().getBlacksMoves().contains(
            new Coordinate(whiteKing.getFile(), whiteKing.getRank())) && 
            guiBoard.getBoard().getWhitesMoves().isEmpty()) || 
            (Board.getPly() % 2 == 1 && guiBoard.getBoard().getWhitesMoves().contains(
            new Coordinate(blackKing.getFile(), blackKing.getRank())) &&
            guiBoard.getBoard().getBlacksMoves().isEmpty());
    }

    private boolean isStaleMate() {
        return (Board.getPly() % 2 == 0 && guiBoard.getBoard().getWhitesMoves().isEmpty()) ||
        (Board.getPly() % 2 == 1 && guiBoard.getBoard().getBlacksMoves().isEmpty());
    }

    private void handleCastle() {
        if (file - GUIBoard.getSelectedPiece().getFile() == 2) {
            // short castle so move rook
            StackPane oldRookStack = (StackPane)guiBoard.getGrid().getChildren().get(file + 1 + rank * Board.WIDTH);
            StackPane newRookStack = (StackPane)guiBoard.getGrid().getChildren().get(file - 1 + rank * Board.WIDTH);
            newRookStack.getChildren().add(0, oldRookStack.getChildren().remove(0));
        }
        else {
            // long castle
            StackPane oldRookStack = (StackPane)guiBoard.getGrid().getChildren().get(file - 2 + rank * Board.WIDTH);
            StackPane newRookStack = (StackPane)guiBoard.getGrid().getChildren().get(file + 1 + rank * Board.WIDTH);
            newRookStack.getChildren().add(0, oldRookStack.getChildren().remove(0));
        }
    }

    private void handleEnPassant() {
        int sign = GUIBoard.getSelectedPiece().getColor() == Color.WHITE ? 1 : -1;
        StackPane enemyPawnStack = (StackPane)guiBoard.getGrid().getChildren().get(file + (rank + sign) * Board.WIDTH);
        enemyPawnStack.getChildren().remove(0);
    }

    private void handlePromotion() {
        Piece piece = GUIBoard.getSelectedPiece();
        addPromotionChoices(piece);
    }

    private void addPromotionChoices(Piece piece) {
        StackPane queenPane = new StackPane(new ImageView(new Queen(piece.getColor(), 0, 0).getImage()));
        StackPane knightPane = new StackPane(new ImageView(new Knight(piece.getColor(), 0, 0).getImage()));
        StackPane rookPane = new StackPane(new ImageView(new Rook(piece.getColor(), 0, 0).getImage()));
        StackPane bishopPane = new StackPane(new ImageView(new Bishop(piece.getColor(), 0, 0).getImage()));

        // set background colors of new panes to be white
        String white = "-fx-background-color: #FFFFFF;";
        queenPane.setStyle(white);
        knightPane.setStyle(white);
        rookPane.setStyle(white);
        bishopPane.setStyle(white);

        // add onclick functionality
        queenPane.setOnMouseClicked(e -> handlePromotionClick(0));
        knightPane.setOnMouseClicked(e -> handlePromotionClick(1));
        rookPane.setOnMouseClicked(e -> handlePromotionClick(2));
        bishopPane.setOnMouseClicked(e -> handlePromotionClick(3));

        // add these panes to the grid
        int sign = GUIBoard.getSelectedPiece().getColor() == Color.WHITE ? 1 : -1;
        stackPane.getChildren().add(queenPane);
        StackPane knightSquare = (StackPane)guiBoard.getGrid().getChildren().get(file + (rank + sign) * Board.WIDTH);
        knightSquare.getChildren().add(knightPane);
        StackPane rookSquare = (StackPane)guiBoard.getGrid().getChildren().get(file + (rank + 2 * sign) * Board.WIDTH);
        rookSquare.getChildren().add(rookPane);
        StackPane bishopSquare = (StackPane)guiBoard.getGrid().getChildren().get(file + (rank + 3 * sign) * Board.WIDTH);
        bishopSquare.getChildren().add(bishopPane);

    }

    private void removePieceFromOldSquare(Piece piece) {
        StackPane selectedPieceStackPane = (StackPane)guiBoard.getGrid().getChildren()
                .get(piece.getRank() * Board.WIDTH + piece.getFile());
        selectedPieceStackPane.getChildren().remove(0); //remove the piece at old square
        if (stackPane.getChildren().get(0).getClass() == ImageView.class) {
            // remove piece if it is captured
            stackPane.getChildren().remove(0);
        }
    }

    private void handlePromotionClick(int thisRank) {
        int endRank = 0;
        Piece piece = GUIBoard.getSelectedPiece();
        if (piece.getColor() == Color.BLACK) {
            endRank = 7;
        }
        removePieceFromOldSquare(piece);
        piece.move(new Coordinate(file, endRank), guiBoard.getBoard());

        guiBoard.getBoard().getPieces().remove(piece); // pawn becomes queen or other piece

        StackPane promotionSquare = (StackPane)guiBoard.getGrid().getChildren().get(file + endRank * Board.WIDTH);
        Piece promotedToPiece = pickPromotionPiece(piece, thisRank, endRank);
        clearMoveHints();
        promotionSquare.getChildren().add(new ImageView(promotedToPiece.getImage()));
        guiBoard.getBoard().getPieces().add(promotedToPiece);
        guiBoard.getBoard().getBoard()[file][endRank] = promotedToPiece;

        guiBoard.getBoard().newPly();
    }

    private Piece pickPromotionPiece(Piece piece, int relativeRank, int endRank) {
        switch (relativeRank) {
            case 1:
                return new Knight(piece.getColor(), file, endRank);
            case 2:
                return new Rook(piece.getColor(), file, endRank);
            case 3:
                return new Bishop(piece.getColor(), file, endRank);
            default:
                return new Queen(piece.getColor(), file, endRank);
        }
    }

    private void resetOldSelectedSquare() {
        Piece piece = GUIBoard.getSelectedPiece();
        StackPane selectedPieceStackPane = (StackPane)guiBoard.getGrid().getChildren()
            .get(piece.getRank() * Board.WIDTH + piece.getFile());
        setBackgroundColor(selectedPieceStackPane, piece.getFile(), piece.getRank());
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    private Circle moveHint() {
        Circle circle = new Circle(GUIBoard.CELL_SIZE / 4.0, javafx.scene.paint.Color.rgb(200, 200, 200, 0.85));
        circle.setStrokeWidth(0);
        return circle;
    }

    public void clearMoveHints() {
        for (Node node : guiBoard.getGrid().getChildren()) {
            StackPane stack = (StackPane)node;
            int last_index = stack.getChildren().size() - 1;
            if (last_index >= 0 && (stack.getChildren().get(last_index).getClass() == Circle.class ||
                stack.getChildren().get(last_index).getClass() == StackPane.class)) { 
                // if there is a move hint on top of piece or promotiotion button
                stack.getChildren().remove(last_index); // then remove the move hint
            }
            last_index--;
            // check again to see if we can remove a child in case where theres a move hint and a
            // promotion button
            if (last_index >= 0 && (stack.getChildren().get(last_index).getClass() == Circle.class ||
                stack.getChildren().get(last_index).getClass() == StackPane.class)) { 
                // if there is a move hint on top of piece or promotiotion button
                stack.getChildren().remove(last_index); // then remove the move hint
            }
        }
    }

    private void handleCheckmate() {
        clearMoves();
        String title = Board.getPly() % 2 == 1 ? "White Wins!" : "Black Wins!";
        EndScreen endScreen = new EndScreen(title, "Checkmate", guiBoard);
        guiBoard.getTotalPane().getChildren().addAll(endScreen.getBackground(), endScreen.getVBox());
    }

    private void handleStalemate() {
        clearMoves();
        EndScreen endScreen = new EndScreen("Draw", "Stalemate", guiBoard);
        guiBoard.getTotalPane().getChildren().addAll(endScreen.getBackground(), endScreen.getVBox());
    }

    private void handle50MoveDraw() {
        clearMoves();
        EndScreen endScreen = new EndScreen("Draw", "By 50 Move Rule", guiBoard);
        guiBoard.getTotalPane().getChildren().addAll(endScreen.getBackground(), endScreen.getVBox());
    }

    private void handle3FoldRepetition() {
        clearMoves();
        EndScreen endScreen = new EndScreen("Draw", "By 3-Fold Repetition", guiBoard);
        guiBoard.getTotalPane().getChildren().addAll(endScreen.getBackground(), endScreen.getVBox());
    }

    private void clearMoves() {
        for (Piece p: guiBoard.getBoard().getPieces()) {
            p.clearPossibleMoves();
        }
    }

}
