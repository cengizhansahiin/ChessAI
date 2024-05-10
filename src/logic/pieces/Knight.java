package logic.pieces;

import logic.ChessGame;
import logic.move.Move;
import logic.enums.Colors;
import logic.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Colors color, int x, int y) {
        super(color, PieceType.KNIGHT, x, y);
    }

    @Override
    public List<Move> listPossibleMoves(Piece[][] board) {
        List<Move> possibleMoves = new ArrayList<>();

        if(getX() + 2 < ChessGame.BOARD_SIZE && getY() + 1 < ChessGame.BOARD_SIZE){
            if(board[getY() + 1][getX() + 2] == null) possibleMoves.add(new Move(getX() + 2, getY() + 1, getX(), getY()));
            else if(board[getY() + 1][getX() + 2].getColor() != getColor()) possibleMoves.add(new Move(getX() + 2, getY() + 1, getX(), getY()));
        }
        if(getX() + 1 < ChessGame.BOARD_SIZE && getY() + 2 < ChessGame.BOARD_SIZE){
            if(board[getY() + 2][getX() + 1] == null) possibleMoves.add(new Move(getX() + 1, getY() + 2, getX(), getY()));
            else if(board[getY() + 2][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX() + 1, getY() + 2, getX(), getY()));
        }


        if(getX() - 2 >= 0 && getY() + 1 < ChessGame.BOARD_SIZE){
            if(board[getY() + 1][getX() - 2] == null) possibleMoves.add(new Move(getX() - 2, getY() + 1, getX(), getY()));
            else if(board[getY() + 1][getX() - 2].getColor() != getColor()) possibleMoves.add(new Move(getX() - 2, getY() + 1, getX(), getY()));
        }
        if(getX() - 1 >= 0 && getY() + 2 < ChessGame.BOARD_SIZE){
            if(board[getY() + 2][getX() - 1] == null) possibleMoves.add(new Move(getX() - 1, getY() + 2, getX(), getY()));
            else if(board[getY() + 2][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY() + 2, getX(), getY()));
        }


        if(getX() + 2 < ChessGame.BOARD_SIZE && getY() - 1 >= 0){
            if(board[getY() - 1][getX() + 2] == null) possibleMoves.add(new Move(getX() + 2, getY() - 1, getX(), getY()));
            else if(board[getY() - 1][getX() + 2].getColor() != getColor()) possibleMoves.add(new Move(getX() + 2, getY() - 1, getX(), getY()));
        }
        if(getX() + 1 < ChessGame.BOARD_SIZE && getY() - 2 >= 0){
            if(board[getY() - 2][getX() + 1] == null) possibleMoves.add(new Move(getX() + 1, getY() - 2, getX(), getY()));
            else if(board[getY() - 2][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX() + 1, getY() - 2, getX(), getY()));
        }


        if(getX() - 2 >= 0 && getY() - 1 >= 0){
            if(board[getY() - 1][getX() - 2] == null) possibleMoves.add(new Move(getX() - 2, getY() - 1, getX(), getY()));
            else if(board[getY() - 1][getX() - 2].getColor() != getColor()) possibleMoves.add(new Move(getX() - 2, getY() - 1, getX(), getY()));
        }
        if(getX() - 1 >= 0 && getY() - 2 >= 0){
            if(board[getY() - 2][getX() - 1] == null) possibleMoves.add(new Move(getX() - 1, getY() - 2, getX(), getY()));
            else if(board[getY() - 2][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY() - 2, getX(), getY()));
        }
        return possibleMoves;
    }
}
