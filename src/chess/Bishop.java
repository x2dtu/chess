package chess;

import java.io.IOException;

public class Bishop extends Piece {

    public Bishop(Color color, int file, int rank) {
        super(color, file, rank);
        try {
            String path = color == Color.WHITE ? "src/images/72x72bishop.png" : "src/images/72x72blackbishop.png";
            setImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void calculatePossibleMoves(Board board, boolean isTurn) {
        calculateUpRightMoves(board, isTurn);
        calculateUpLeftMoves(board, isTurn);
        calculateDownRightMoves(board, isTurn);
        calculateDownLeftMoves(board, isTurn);
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
        return getColor() + " Bishop at (" + getFile() + ", " + getRank() + ")";
    }

}
