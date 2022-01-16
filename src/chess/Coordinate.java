package chess;

import java.util.Objects;

public class Coordinate {
    private int file;
    private int rank;

    public Coordinate(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "(" + file + " " + rank + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        Coordinate other = (Coordinate)o;
        return this.file == other.file && this.rank == other.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
}
