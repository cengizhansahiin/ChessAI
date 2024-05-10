package players;

import logic.move.Move;
import logic.enums.Colors;
import logic.pieces.Piece;

import java.io.Serializable;
import java.util.Optional;
import java.util.Stack;

public abstract class Player implements Serializable {

    private Colors color;
    private int playerNumber;

    private Stack<Piece> capturedPieces;

    public Player(Colors color, int playerNumber, Stack<Piece> capturedPieces){
        this.color = color;
        this.playerNumber = playerNumber;
        this.capturedPieces = capturedPieces;
    }

    public abstract Optional<Move> makeMove(Piece[][] board);

    public Colors getColor() {
        return color;
    }
    public int getPlayerNumber(){
        return playerNumber;
    }
    public void capturePiece(Piece piece){
        capturedPieces.add(piece);
    }
    public abstract Piece promotePawn();

    public Stack<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void invokeEndGame(){
        this.color = null;
        this.playerNumber = 0;
        this.capturedPieces = null;
    }
}
