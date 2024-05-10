package logic.pieces;

import logic.ChessGame;
import logic.move.Move;
import logic.enums.Colors;
import logic.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    private boolean firstMove = true;

    public Pawn(Colors color, int x, int y) {
        super(color, PieceType.PAWN, x, y);
    }

    @Override
    public List<Move> listPossibleMoves(Piece[][] board) {

        List<Move> possibleMoves = new ArrayList<>();
        if(getColor() == Colors.WHITE){
            if(getY() + 1 < ChessGame.BOARD_SIZE){
                if(board[getY() + 1][getX()] == null) possibleMoves.add(new Move(getX(), getY() + 1, getX(), getY()));
            }
            if(getX() + 1 < ChessGame.BOARD_SIZE && getY() + 1 < ChessGame.BOARD_SIZE){
                if(board[getY() + 1][getX() + 1] != null && board[getY() + 1][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX() + 1, getY() + 1, getX(), getY()));
            }
            if(getX() - 1 >= 0 && getY() + 1 < ChessGame.BOARD_SIZE){
                if(board[getY() + 1][getX() - 1] != null && board[getY() + 1][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY() + 1, getX(), getY()));
            }
            if(firstMove && getY() + 2 < ChessGame.BOARD_SIZE){
                if(board[getY() + 1][getX()] == null && board[getY() + 2][getX()] == null) {
                    possibleMoves.add(new Move(getX(), getY() + 2, getX(), getY()));
                }
            }
        }
        else {
            if(getY() - 1 >= 0){
                if(board[getY() - 1][getX()] == null) possibleMoves.add(new Move(getX(), getY() - 1, getX(), getY()));
            }
            if(getX() + 1 < ChessGame.BOARD_SIZE && getY() - 1 >= 0){
                if(board[getY() - 1][getX() + 1] != null && board[getY() - 1][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX() + 1, getY() - 1, getX(), getY()));
            }
            if(getX() - 1 >= 0 && getY() - 1 >= 0){
                if(board[getY() - 1][getX() - 1] != null && board[getY() - 1][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY() - 1, getX(), getY()));
            }
            if(firstMove && getY() - 2 >= 0){
                if(board[getY() - 1][getX()] == null && board[getY() - 2][getX()] == null) {
                    possibleMoves.add(new Move(getX(), getY() - 2, getX(), getY()));
                }
            }
        }
        return possibleMoves;

    }
    public void setFirstMove(boolean hasMoved){
        firstMove = hasMoved;
    }

}
