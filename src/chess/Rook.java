package chess;

import java.io.IOException;

public class Rook extends Piece {
    
    private boolean hasMoved;

    public Rook(Color color, int file, int rank) {
        super(color, file, rank);
        hasMoved = false;
        try {
            String path = color == Color.WHITE ? "src/images/72x72rook.png" : "src/images/72x72blackrook.png";
            setImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calculatePossibleMoves(Board board, boolean isTurn) {
        calculateRightMoves(board, isTurn);
        calculateLeftMoves(board, isTurn);
        calculateUpMoves(board, isTurn);
        calculateDownMoves(board, isTurn);
    }

    private void calculateRightMoves(Board board, boolean isTurn) {
        int file = getFile() + 1;
        int rank = getRank();
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            file++;
        }
    }

    private void calculateLeftMoves(Board board, boolean isTurn) {
        int file = getFile() - 1;
        int rank = getRank();
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            file--;
        }
    }

    private void calculateUpMoves(Board board, boolean isTurn) {
        int file = getFile();
        int rank = getRank() - 1;
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            rank--;
        }
    }

    private void calculateDownMoves(Board board, boolean isTurn) {
        int file = getFile();
        int rank = getRank() + 1;
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            rank++;
        }
    }

    private boolean addIfMove(Board board, int file, int rank, boolean isTurn) {
        if (board.getBoard()[file][rank] == null && isNotCheck(board, file, rank, isTurn)) {
            getPossibleMoves().add(new Coordinate(file, rank));
        }
        else if (board.getBoard()[file][rank] != null) {
            if (board.getBoard()[file][rank].getColor() != getColor() && 
                isNotCheck(board, file, rank, isTurn)) {
                    getPossibleMoves().add(new Coordinate(file, rank));
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean getHasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        return getColor() + " Rook at (" + getFile() + ", " + getRank() + ")";
    }

}
