package Checkers;

public class Player extends Person{
    private int piecesLeft, piecesTaken;

    public Player(String name) {
        super(name);
        setPiecesLeft();
        setPiecesTaken();

    }
    public int getPiecesLeft() {return piecesLeft;}

    private void setPiecesLeft() {
        this.piecesLeft = 12;
    }

    public int getPiecesTaken() {
        return piecesTaken;
    }

    private void setPiecesTaken() {
        this.piecesTaken = 0;
    }

    public String toString() {
        return "Name: " + getName() + "\nPieces left: " + getPiecesLeft() + "\nPieces taken: " + getPiecesTaken();
    }
}