package chess;

import java.io.IOException;

public class King extends Piece {

    private boolean hasMoved;

    public King(Color color, int file, int rank) {
        super(color, file, rank);
        hasMoved = false;
        try {
            String path = color == Color.WHITE ? "src/images/72x72king.png" : "src/images/72x72blackking.png";
            setImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(Coordinate square, Board board) {
        int oldFile = getFile();
        if (square.getFile() - oldFile == 2) {
            // then we short castled and need to move the rook
            Rook rook = (Rook)board.getBoard()[getFile() + 3][getRank()];
            rook.move(new Coordinate(getFile() + 1, getRank()), board);
        }
        else if (oldFile - square.getFile() == 2) {
            // then we long castled and need to move the rook
            Rook rook = (Rook)board.getBoard()[getFile() - 4][getRank()];
            rook.move(new Coordinate(getFile() - 1, getRank()), board);
        }
        // move regardless
        super.move(square, board);
        hasMoved = true;
    }

    @Override
    public void calculatePossibleMoves(Board board, boolean isTurn) {
        int file = getFile();
        int rank = getRank();
        calculateSingleMove(board, file, rank + 1);
        calculateSingleMove(board, file + 1, rank + 1);
        calculateSingleMove(board, file + 1, rank);
        calculateSingleMove(board, file + 1, rank - 1);
        calculateSingleMove(board, file, rank - 1);
        calculateSingleMove(board, file - 1, rank - 1);
        calculateSingleMove(board, file - 1, rank);
        calculateSingleMove(board, file - 1, rank + 1);
        calculateCastleLong(board);
        calculateCastleShort(board);
    }

    private void calculateSingleMove(Board board, int file, int rank) {
        if (isValidMove(file, rank) && moveNotInCheck(board, file, rank)) {
                getPossibleMoves().add(new Coordinate(file, rank));
        }
    }



    private void calculateCastleShort(Board board) {
        if (hasMoved || board.getBoard()[getFile() + 3][getRank()] == null || 
            board.getBoard()[getFile() + 3][getRank()].getClass() != Rook.class) {
                return;
        }
        Rook rook = (Rook)board.getBoard()[getFile() + 3][getRank()];
        if (!rook.getHasMoved() && castleNotInCheck(board, getFile(), getFile() + 2, getRank()) &&
            castleNoPieceInWay(board, getFile() + 1, getFile() + 2, getRank())) {
                getPossibleMoves().add(new Coordinate(getFile() + 2, getRank()));
                rook.getPossibleMoves().add(new Coordinate(getFile() + 1, getRank()));
        }
    }

    private void calculateCastleLong(Board board) {
        if (hasMoved || board.getBoard()[getFile() - 4][getRank()] == null || 
            board.getBoard()[getFile() - 4][getRank()].getClass() != Rook.class) {
                return;
        }
        Rook rook = (Rook)board.getBoard()[getFile() - 4][getRank()];
        if (!rook.getHasMoved() && castleNotInCheck(board, getFile() - 2, getFile(), getRank()) && 
            castleNoPieceInWay(board, getFile() - 3, getFile() - 1, getRank())) {
                getPossibleMoves().add(new Coordinate(getFile() - 2, getRank()));
                rook.getPossibleMoves().add(new Coordinate(getFile() - 1, getRank()));
        }
    }

    private boolean castleNotInCheck(Board board, int fileStart, int fileEnd, int rank) {
        for (int i = fileStart; i <= fileEnd; i++) {
            if (squareAttacked(board, i, rank)) {
                return false;
            }
        }
        return true;
    }

    private boolean castleNoPieceInWay(Board board, int fileStart, int fileEnd, int rank) {
        for (int i = fileStart; i <= fileEnd; i++) {
            if (board.getBoard()[i][rank] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean squareAttacked(Board board, int file, int rank) {
        if (getColor() == Color.WHITE) {
            return board.getBlacksMoves().contains(new Coordinate(file, rank)) || 
                squareAttackedByPawn(board, rank);
        }
        return board.getWhitesMoves().contains(new Coordinate(file, rank)) || 
            squareAttackedByPawn(board, rank);
    }

    private boolean moveNotInCheck(Board board, int file, int rank) {
        if (board.getBoard()[file][rank] != null) {
            return false;
        }
        board.getBoard()[getFile()][getRank()] = null;
        if (getColor() == Color.WHITE) {
            board.setBlacksMoves(false);
            board.getBoard()[getFile()][getRank()] = this;
            return !board.getBlacksMoves().contains(new Coordinate(file, rank));
        }
        else {
            board.setWhitesMoves(false);
            board.getBoard()[getFile()][getRank()] = this;
            return !board.getWhitesMoves().contains(new Coordinate(file, rank));
        }
    }

    private boolean squareAttackedByPawn(Board board, int rank) {
        int sign = getColor() == Color.WHITE ? 1 : -1;
        for (Piece p : board.getPieces()) {
            if (p.getColor() != getColor() && p.getClass() == Pawn.class && p.getRank() + sign == rank) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getColor() + " King at (" + getFile() + ", " + getRank() + ")";
    }
}
