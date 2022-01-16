package chess;

import java.io.IOException;

public class Pawn extends Piece {
    private int moved;

    public Pawn(Color color, int file, int rank) {
        super(color, file, rank);
        moved = -1;
        try {
            String path = color == Color.WHITE ? "src/images/72x72pawn.png" : "src/images/72x72blackpawn.png";
            setImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(Coordinate square, Board board) {
        super.move(square, board);
        moved = Board.getPly();
    }

    @Override
    public void calculatePossibleMoves(Board board, boolean isTurn) {
        int sign = 1;
        if (getColor() == Color.WHITE) {
            sign = -1;
        }
        calculateSingleMove(board, getFile(), getRank() + 1 * sign, isTurn);
        calculateDoubleMove(board, getFile(), getRank() + 2 * sign, sign, isTurn);
        calculateCaptures(board, getFile() + 1, getRank() + 1 * sign, isTurn);
        calculateCaptures(board, getFile() - 1, getRank() + 1 * sign, isTurn);
        calculateEnPassant(board, getFile() - 1, getRank(), sign, isTurn);
        calculateEnPassant(board, getFile() + 1, getRank(), sign, isTurn);
    }

    private void calculateSingleMove(Board board, int file, int rank, boolean isTurn) {
        if (isValidMove(rank, file) && board.getBoard()[file][rank] == null && 
            isNotCheck(board, file, rank, isTurn)) {
                getPossibleMoves().add(new Coordinate(file, rank));
        }
    }

    private void calculateDoubleMove(Board board, int file, int rank, int sign, boolean isTurn) {
        if (moved == -1 && isValidMove(file, rank) && board.getBoard()[file][rank] == null &&
            board.getBoard()[file][rank - sign] == null && isNotCheck(board, file, rank, isTurn)) {
                getPossibleMoves().add(new Coordinate(file, rank));
        }
    }

    private void calculateCaptures(Board board, int file, int rank, boolean isTurn) {
        if (isValidMove(file, rank) && board.getBoard()[file][rank] != null && 
            board.getBoard()[file][rank].getColor() != getColor() && 
            isNotCheck(board, file, rank, isTurn)) {
                getPossibleMoves().add(new Coordinate(file, rank));
        }
    }
    private void calculateEnPassant(Board board, int file, int rank, int sign, boolean isTurn) {
        if (isValidMove(file, rank) && board.getBoard()[file][rank] != null && 
            board.getBoard()[file][rank].getClass() == getClass() && 
            board.getBoard()[file][rank].getColor() != getColor() && 
            Board.getPly() - ((Pawn)(board.getBoard()[file][rank])).getMoved() == 1 &&
            isNotCheck(board, file, rank, isTurn)) {

            getPossibleMoves().add(new Coordinate(file, rank + sign));
        }
    }

    @Override
    public int getMoved() {
        return moved;
    }

    @Override
    public String toString() {
        return getColor() + " Pawn at (" + getFile() + ", " + getRank() + ")";
    }

}
