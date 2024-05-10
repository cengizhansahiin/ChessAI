package logic.pieces;

import logic.ChessGame;
import logic.move.Move;
import logic.enums.Colors;
import logic.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private boolean hasMoved = false;

    private boolean isUnderCheck = false;

    public King(Colors color, int x, int y) {
        super(color, PieceType.KING, x ,y);
    }

    @Override
    public List<Move> listPossibleMoves(Piece[][] board) {
        List<Move> possibleMoves = new ArrayList<>();

        if(getX() + 1 < ChessGame.BOARD_SIZE && getY() + 1 < ChessGame.BOARD_SIZE){
            if(board[getY() + 1][getX() + 1] == null) possibleMoves.add(new Move(getX() + 1, getY() + 1, getX(), getY()));
            else if(board[getY() + 1][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX() + 1, getY() + 1, getX(), getY()));
        }
        if(getX() - 1 >= 0 && getY() + 1 < ChessGame.BOARD_SIZE){
            if(board[getY() + 1][getX() - 1] == null) possibleMoves.add(new Move(getX() - 1, getY() + 1, getX(), getY()));
            else if(board[getY() + 1][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY() + 1, getX(), getY()));
        }
        if(getX() + 1 < ChessGame.BOARD_SIZE && getY() - 1 >= 0){
            if(board[getY() - 1][getX() + 1] == null) possibleMoves.add(new Move(getX() + 1, getY() - 1, getX(), getY()));
            else if(board[getY() - 1][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX() + 1, getY() - 1, getX(), getY()));
        }
        if(getX() - 1 >= 0 && getY() - 1 >= 0){
            if(board[getY() - 1][getX() - 1] == null) possibleMoves.add(new Move(getX() - 1, getY() - 1, getX(), getY()));
            else if(board[getY() - 1][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY() - 1, getX(), getY()));
        }


        if(getY() + 1 < ChessGame.BOARD_SIZE){
            if(board[getY() + 1][getX()] == null) possibleMoves.add(new Move(getX(), getY() + 1, getX(), getY()));
            else if(board[getY() + 1][getX()].getColor() != getColor()) possibleMoves.add(new Move(getX(), getY() + 1, getX(), getY()));
        }
        if(getX() - 1 >= 0){
            if(board[getY()][getX() - 1] == null) possibleMoves.add(new Move(getX() - 1, getY(), getX(), getY()));
            else if(board[getY()][getX() - 1].getColor() != getColor()) possibleMoves.add(new Move(getX() - 1, getY(), getX(), getY()));
        }
        if(getX() + 1 < ChessGame.BOARD_SIZE){
            if(board[getY()][getX() + 1] == null) possibleMoves.add(new Move(getX() + 1, getY(), getX(), getY()));
            else if(board[getY()][getX() + 1].getColor() != getColor()) possibleMoves.add(new Move(getX(), getY() - 1, getX(), getY()));
        }
        if(getY() - 1 >= 0){
            if(board[getY() - 1][getX()] == null) possibleMoves.add(new Move(getX(), getY() - 1, getX(), getY()));
            else if(board[getY() - 1][getX()].getColor() != getColor()) possibleMoves.add(new Move(getX(), getY() - 1, getX(), getY()));
        }

        //Castle rule possible castling move addition
        if(!isUnderCheck && !hasMoved){
            for(int iterX = getX(); iterX < ChessGame.BOARD_SIZE; iterX++){ //Castle to right
                if(board[getY()][iterX] != null){
                    if(board[getY()][iterX].getPieceType() == PieceType.ROOK) {
                        if (!((Rook) board[getY()][iterX]).getHasMoved()) {
                            possibleMoves.add(new Move(iterX, getY(), getX(), getY()));
                        }
                    }
                }
            }
            for(int iterX = getX(); iterX >=0; iterX--){ //Castle to left
                if(board[getY()][iterX] != null){
                    if(board[getY()][iterX].getPieceType() == PieceType.ROOK) {
                        if (!((Rook) board[getY()][iterX]).getHasMoved()) {
                            possibleMoves.add(new Move(iterX, getY(), getX(), getY()));
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }
    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public void setIsUnderCheck(boolean underCheck) {
        isUnderCheck = underCheck;
    }
}
