import java.io.Serializable;

public class Player extends Person implements Serializable{
    private int piecesLeft, piecesTaken;

    public Player() {
        this("Unknown");
    }

    public Player(String name) {
        super(name);
        setPiecesLeft(12);
        setPiecesTaken(12);
    }
    public int getPiecesLeft() {return piecesLeft;}

    public void setPiecesLeft(int piecesLeft) {
        this.piecesLeft = piecesLeft;
    }

    public int getPiecesTaken() {
        return piecesTaken;
    }

    public void setPiecesTaken(int piecesTaken) {
        this.piecesTaken = piecesTaken;
    }

    public String toString() {
        return "Name: " + getName() + "\nPieces left: " + getPiecesLeft() + "\nPieces taken: " + getPiecesTaken();
    }
}