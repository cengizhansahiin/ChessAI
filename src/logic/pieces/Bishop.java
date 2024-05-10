package logic.pieces;

import logic.ChessGame;
import logic.move.Move;
import logic.enums.Colors;
import logic.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Colors color, int x, int y) {
        super(color, PieceType.BISHOP, x, y);
    }

    @Override
    public List<Move> listPossibleMoves(Piece[][] board) {
        List<Move> possibleMoves = new ArrayList<>();

        for(int iterY = getY() + 1, iterX = getX() + 1; iterY < ChessGame.BOARD_SIZE && iterX < ChessGame.BOARD_SIZE; iterY++, iterX++){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }

        for(int iterY = getY() + 1 , iterX = getX() - 1; iterY < ChessGame.BOARD_SIZE && iterX >= 0; iterY++, iterX--){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }

        for(int iterY = getY() - 1 , iterX = getX() + 1; iterY >= 0 && iterX < ChessGame.BOARD_SIZE; iterY--, iterX++){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }

        for(int iterY = getY() - 1 , iterX = getX() - 1; iterY >= 0 && iterX >= 0; iterY--, iterX--){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;


        }



        return possibleMoves;
    }
}
