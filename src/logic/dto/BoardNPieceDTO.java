package logic.dto;

import logic.pieces.Piece;

import java.io.Serializable;
import java.util.Optional;

public class BoardNPieceDTO implements Serializable {

    public BoardNPieceDTO(Piece[][] board, Optional<Piece> capturedPiece) {
        this.board = board;
        this.capturedPiece = capturedPiece;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Optional<Piece> getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Optional<Piece> capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    private Piece[][] board;
    private Optional<Piece> capturedPiece;


}
