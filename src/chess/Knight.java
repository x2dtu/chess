package chess;

import java.io.IOException;

public class Knight extends Piece {
    
    public Knight(Color color, int file, int rank) {
        super(color, file, rank);
        try {
            String path = color == Color.WHITE ? "src/images/72x72knight.png" : "src/images/72x72blackknight.png";
            setImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calculatePossibleMoves(Board board, boolean isTurn) {
        int file = getFile();
        int rank = getRank();
        calculateSingleMove(board, file - 1, rank + 2, isTurn);
        calculateSingleMove(board, file - 2, rank + 1, isTurn);
        calculateSingleMove(board, file - 2, rank - 1, isTurn);
        calculateSingleMove(board, file - 1, rank - 2, isTurn);
        calculateSingleMove(board, file + 1, rank - 2, isTurn);
        calculateSingleMove(board, file + 2, rank - 1, isTurn);
        calculateSingleMove(board, file + 2, rank + 1, isTurn);
        calculateSingleMove(board, file + 1, rank + 2, isTurn);
    }

    private void calculateSingleMove(Board board, int file, int rank, boolean isTurn) {
        if (isValidMove(file, rank) && (board.getBoard()[file][rank] == null || 
            board.getBoard()[file][rank].getColor() != getColor()) && 
            isNotCheck(board, file, rank, isTurn)) {
                getPossibleMoves().add(new Coordinate(file, rank));
        }
    }

    @Override
    public String toString() {
        return getColor() + " Knight at (" + getFile() + ", " + getRank() + ")";
    }
}
