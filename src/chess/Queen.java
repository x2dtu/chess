package chess;

import java.io.IOException;

public class Queen extends Piece {

    public Queen(Color color, int file, int rank) {
        super(color, file, rank);
        try {
            String path = color == Color.WHITE ? "src/images/72x72queen.png" : "src/images/72x72blackqueen.png";
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
        calculateUpRightMoves(board, isTurn);
        calculateUpLeftMoves(board, isTurn);
        calculateDownRightMoves(board, isTurn);
        calculateDownLeftMoves(board, isTurn);
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

    private void calculateUpRightMoves(Board board, boolean isTurn) {
        int file = getFile() + 1;
        int rank = getRank() + 1;
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            file++;
            rank++;
        }
    }

    private void calculateUpLeftMoves(Board board, boolean isTurn) {
        int file = getFile() - 1;
        int rank = getRank() + 1;
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            file--;
            rank++;
        }
    }

    private void calculateDownRightMoves(Board board, boolean isTurn) {
        int file = getFile() + 1;
        int rank = getRank() - 1;
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            file++;
            rank--;
        }
    }

    private void calculateDownLeftMoves(Board board, boolean isTurn) {
        int file = getFile() - 1;
        int rank = getRank() - 1;
        while (isValidMove(file, rank) && addIfMove(board, file, rank, isTurn)) {
            file--;
            rank--;
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
    public String toString() {
        return getColor() + " Queen at (" + getFile() + ", " + getRank() + ")";
    }

}