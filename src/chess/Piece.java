package chess;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.image.Image;

public class Piece {
    private Color color;
    private int currRank;
    private int currFile;
    private Set<Coordinate> possibleMoves;
    private Image image;


    public Piece(Color color, int file, int rank) {
        this.color = color;
        currFile = file;
        currRank = rank;
        image = null;
        clearPossibleMoves();
    }

    public Coordinate getPos() {
        return new Coordinate(currFile, currRank);
    }

    public Color getColor() {
        return color;
    }

    public void setFile(int file) {
        currFile = file;
    }

    public int getFile() {
        return currFile;
    }

    public void setRank(int rank) {
        currRank = rank;
    }

    public int getRank() {
        return currRank;
    }

    public void clearPossibleMoves() {
        this.possibleMoves = new HashSet<>();
    }

    public Set<Coordinate> getPossibleMoves() {
        return possibleMoves;
    }

    public boolean isValidMove(int file, int rank) {
        return rank >= 0 && rank < Board.WIDTH && file >= 0 && file < Board.WIDTH;
    }

    private boolean kingInDanger(Board board, King king) {
        if (color == Color.WHITE) {
            board.setBlacksMoves(false);
            return board.getBlacksMoves().contains(new Coordinate(king.getFile(), king.getRank()));
        }
        board.setWhitesMoves(false);
        return board.getWhitesMoves().contains(new Coordinate(king.getFile(), king.getRank()));
    }

    private boolean stopCheckWithCapture(Board board, Piece originalPiece, King king) {
        board.getBoard()[getFile()][getRank()] = null;
        if (color == Color.WHITE) {
            board.setBlacksMoves(false);
        }
        else {
            board.setWhitesMoves(false);
        }
        if (doubleCheck(board, king)) return false;
        board.getBoard()[getFile()][getRank()] = this;
        return originalPiece.getPossibleMoves().contains(
            new Coordinate(king.getFile(), king.getRank()));

    }

    private boolean doubleCheck(Board board, King king) {
        int checkCount = 0;
        for (Piece p : board.getPieces()) {
            if (p.color != color && p.getPossibleMoves().contains(
                new Coordinate(king.getFile(), king.getRank()))) {
                    checkCount++;
            }
        }
        return checkCount == 2;
    }

    public boolean isNotCheck(Board board, int file, int rank, boolean isTurn) {
        if (!isTurn) {
            return true;
        }
        int oldFile = this.getFile();
        int oldRank = this.getRank();

        Piece originalPiece = board.getBoard()[file][rank];
        King king = board.getKing(color);

        if (originalPiece != null && stopCheckWithCapture(board, originalPiece, king) && 
            !doubleCheck(board, king)) {
            return true;
        }

        board.getBoard()[oldFile][oldRank] = null;
        board.getBoard()[file][rank] = this;
        boolean result = !kingInDanger(board, king);

        // revert move
        board.getBoard()[file][rank] = originalPiece;
        board.getBoard()[oldFile][oldRank] = this;
        return result;
    }

    public void move(Coordinate square, Board board) {
        if (getPossibleMoves().contains(square)) {
            int newFile = square.getFile();
            int newRank = square.getRank();
            Piece capturedPiece = board.getBoard()[newFile][newRank];
            board.getPieces().remove(capturedPiece);
            board.getBoard()[newFile][newRank] = this;
            board.getBoard()[this.currFile][this.currRank] = null;
            this.currFile = newFile;
            this.currRank = newRank;
            if (getClass() == Pawn.class || capturedPiece != null) {
                Board.resetFiftyMoveRule();
            }
            else {
                Board.incrementFiftyMoveRule();
            }
        }
    }

    /**
     * Calculates possible moves for this Piece
     * @param board The board needed for calculating the possible moves
     */
    public void calculatePossibleMoves(Board board, boolean isTurn) {
        // this method is empty because Pieces can not calculate moves on their own,
        // but their subclasses who will override this method can
    }

    /**
     * Used and overriden by Rook subclass
     * @return Whether this piece has moved or not
     */
    public boolean getHasMoved() {
        return false;
    }

    /**
     * Used and overriden by Pawn subclass
     * @return The ply this piece has moved
     */
    public int getMoved() {
        return -1;
    }
    
    public Image getImage() {
        return image;
    }

    public void setImage(String path) throws IOException {
        try (FileInputStream input = new FileInputStream(path)) {
            image = new Image(input);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return color + " Piece at (" + currFile + ", " + currRank + ")";
    }
}
