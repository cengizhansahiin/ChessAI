package server.dto;

import logic.enums.Colors;
import logic.pieces.Piece;

import java.io.Serializable;
import java.util.Stack;

public class ServerToClientDTO implements Serializable {


    private String message;
    private Stack<Piece> capturedPieces;
    private int playerNumber;
    private Colors color;
    private Piece[][] board;
    private int turn;

    public ServerToClientDTO(String message, Stack<Piece> capturedPieces, int playerNumber, Colors color, Piece[][] board, int turn) {
        this.message = message;
        this.capturedPieces = capturedPieces;
        this.playerNumber = playerNumber;
        this.color = color;
        this.board = board;
        this.turn = turn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Stack<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void setCapturedPieces(Stack<Piece> capturedPieces) {
        this.capturedPieces = capturedPieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


}
