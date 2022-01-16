package chess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    public static final int WIDTH = 8;
    private static int ply = 0;
    private static int fiftyMoveRule = 0;
    private Piece[][] gameBoard;
    private Set<Piece> pieces;
    private King whiteKing;
    private King blackKing;
    private Set<Coordinate> blacksMoves;
    private Set<Coordinate> whitesMoves;
    private Map<String, Integer> pastPositions;

    public Board() {
        Board.resetPly();
        gameBoard = new Piece[WIDTH][WIDTH];
        pieces = new HashSet<>();
        whiteKing = new King(Color.WHITE, 4, 0);
        blackKing = new King(Color.BLACK, 4, 7);
        readFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", true);
        blacksMoves = new HashSet<>();
        whitesMoves = new HashSet<>();
        setBlacksMoves(false);
        setWhitesMoves(false);
        pastPositions = new HashMap<>();
        pastPositions.put("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 1);
    }

    public Piece[][] getBoard() {
        return this.gameBoard;
    }

    public void setPiece(Piece piece) {
        this.gameBoard[piece.getFile()][piece.getRank()] = piece;
    }

    public void removePiece(Piece piece) {
        this.gameBoard[piece.getFile()][piece.getRank()] = null;
    }

    /**
     * @Precondition Param fen is a valid fen string
     * @param fen The fen String to write the board with
     */
    private void readFen(String fen, boolean addToSet) {
        String piece_positions = fen.split(" ")[0];
        int rank = 0;
        int file = 0;
        for (char c : piece_positions.toCharArray()) {
            if (c == '/') {
                rank++;
                file = 0;
            }
            else if (c == 'k') {
                blackKing = new King(Color.BLACK, file, rank);
                gameBoard[file][rank] = blackKing;
                if (addToSet) pieces.add(blackKing);
                file++;
            }
            else if (c == 'K') {
                whiteKing = new King(Color.WHITE, file, rank);
                gameBoard[file][rank] = whiteKing;
                if (addToSet) pieces.add(whiteKing);
                file++;
            }
            else if (Character.toLowerCase(c) == 'q') {
                Queen queen = new Queen(Character.isUpperCase(c) ? 
                Color.WHITE : Color.BLACK, file, rank);
                gameBoard[file][rank] = queen;
                if (addToSet) pieces.add(queen);
                file++;
            }
            else if (Character.toLowerCase(c) == 'r') {
                Rook rook = new Rook(Character.isUpperCase(c) ? 
                Color.WHITE : Color.BLACK, file, rank);
                gameBoard[file][rank] = rook;
                if (addToSet) pieces.add(rook);
                file++;
            }
            else if (Character.toLowerCase(c) == 'n') {
                Knight knight = new Knight(Character.isUpperCase(c) 
                ? Color.WHITE : Color.BLACK, file, rank);
                gameBoard[file][rank] = knight;
                if (addToSet) pieces.add(knight);
                file++;
            }
            else if (Character.toLowerCase(c) == 'b') {
                Bishop bishop = new Bishop(Character.isUpperCase(c) 
                ? Color.WHITE : Color.BLACK, file, rank);
                gameBoard[file][rank] = bishop;
                if (addToSet) pieces.add(bishop);
                file++;
            }
            else if (Character.toLowerCase(c) == 'p') {
                Pawn pawn = new Pawn(Character.isUpperCase(c) 
                ? Color.WHITE : Color.BLACK, file, rank);
                gameBoard[file][rank] = pawn;
                if (addToSet) pieces.add(pawn);
                file++;
            }
            // else the character is a digit
            else {
                file += Character.getNumericValue(c);
            }
        }
    }

    public void readFen(String fen) {
        readFen(fen, false);
    }

    public String makeFen() {
        StringBuilder result = new StringBuilder();
        for (int rank = 0; rank < WIDTH; rank++) {
            int blankSquares = 0;
            for (int file = 0; file < WIDTH; file++) {
                if (gameBoard[file][rank] != null) {
                    Piece piece = gameBoard[file][rank];
                    if (blankSquares != 0) {
                        result.append(blankSquares);
                        blankSquares = 0;
                    }
                    if (piece.getClass() == King.class) {
                        if (piece.getColor() == Color.WHITE) {
                            result.append('K');
                        }
                        else {
                            result.append('k');
                        }
                    }
                    else if (piece.getClass() == Queen.class) {
                        if (piece.getColor() == Color.WHITE) {
                            result.append('Q');
                        }
                        else {
                            result.append('q');
                        }
                    }
                    else if (piece.getClass() == Rook.class) {
                        if (piece.getColor() == Color.WHITE) {
                            result.append('R');
                        }
                        else {
                            result.append('r');
                        }
                    }
                    else if (piece.getClass() == Bishop.class) {
                        if (piece.getColor() == Color.WHITE) {
                            result.append('B');
                        }
                        else {
                            result.append('b');
                        }
                    }
                    else if (piece.getClass() == Knight.class) {
                        if (piece.getColor() == Color.WHITE) {
                            result.append('N');
                        }
                        else {
                            result.append('n');
                        }
                    }
                    else {
                        if (piece.getColor() == Color.WHITE) {
                            result.append('P');
                        }
                        else {
                            result.append('p');
                        }
                    }
                }
                else {
                    blankSquares++;
                }
            }
            if (blankSquares != 0) {
                result.append(blankSquares);
            }
            result.append('/');
        }
        result.setLength(result.length() - 1); // shave off last '/'
        return result.toString();
    }

    public static void incrementPly() {
        ply++;
    }

    public void newPly() {
        incrementPly();
        if (ply % 2 == 0) {
            setWhitesMoves(true);
            setKingMoves(Color.WHITE);
        }
        else {
            setBlacksMoves(true);
            setKingMoves(Color.BLACK);
        }
    }

    public static int getPly() {
        return ply;
    }

    public static void resetPly() {
        ply = 0;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    private Set<Coordinate> setMoves(Color color, boolean isTurn) {
        Set<Coordinate> set = new HashSet<>();
        for (Piece p : pieces) {
            if (p.getColor() == color && p.getClass() != King.class) {
                resetMoves(p, isTurn);
                for (Coordinate move : p.getPossibleMoves()) {
                    set.add(move);
                }
            }
        }
        return set;
    }

    private void setKingMoves(Color color) {
        if (color == Color.WHITE) {
            whiteKing.clearPossibleMoves();
            whiteKing.calculatePossibleMoves(this, true);
            whitesMoves.addAll(whiteKing.getPossibleMoves());
        }
        else {
            blackKing.clearPossibleMoves();
            blackKing.calculatePossibleMoves(this, true);
            blacksMoves.addAll(blackKing.getPossibleMoves());
        }
    }


    private void resetMoves(Piece p, boolean isTurn) {
        p.clearPossibleMoves();
        p.calculatePossibleMoves(this, isTurn);
    }

    public void setBlacksMoves(boolean isTurn) {
        blacksMoves = setMoves(Color.BLACK, isTurn);
    }

    public void setWhitesMoves(boolean isTurn) {
        whitesMoves = setMoves(Color.WHITE, isTurn);
    }

    public Set<Coordinate> getBlacksMoves() {
        return blacksMoves;
    }

    public Set<Coordinate> getWhitesMoves() {
        return whitesMoves;
    }

    public King getKing(Color color) {
        return color == Color.WHITE ? whiteKing : blackKing;
    }

    public static void incrementFiftyMoveRule() {
        fiftyMoveRule++;
    }

    public static void resetFiftyMoveRule() {
        fiftyMoveRule = 0;
    }

    public static int getFiftyMoveRule() {
        return fiftyMoveRule;
    }

    
    public boolean addBoardPosition() {
        String s = makeFen();
        if (pastPositions.containsKey(s)) {
            int count = pastPositions.get(s);
            if (count + 1 >= 3) {
                return false; // 3fold repetition
            } 
            pastPositions.put(s, count + 1);
        }
        else {
            pastPositions.put(s, 1);
        }
        return true;
    }

}
