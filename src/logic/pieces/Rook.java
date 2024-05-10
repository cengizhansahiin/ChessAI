package logic.pieces;

import logic.ChessGame;
import logic.move.Move;
import logic.enums.Colors;
import logic.enums.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    private boolean hasMoved = false;
    public Rook(Colors color, int x, int y) {
        super(color, PieceType.ROOK, x, y);
    }

    @Override
    public List<Move> listPossibleMoves(Piece[][] board) {
        List<Move> possibleMoves = new ArrayList<>();

        for(int iterY = getY(), iterX = getX() + 1; iterX < ChessGame.BOARD_SIZE;iterX++){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }
        for(int iterY = getY(), iterX = getX() - 1; iterX >= 0; iterX--){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }
        for(int iterY = getY() + 1, iterX = getX(); iterY < ChessGame.BOARD_SIZE;iterY++){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }
        for(int iterY = getY() - 1, iterX = getX(); iterY >= 0; iterY--){

            if(board[iterY][iterX] == null) possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
            else if(board[iterY][iterX].getColor() != getColor()){
                possibleMoves.add(new Move(iterX, iterY, getX(), getY()));
                break;
            }
            else if(board[iterY][iterX].getColor() == getColor()) break;

        }

        return possibleMoves;
    }
    public boolean getHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }
}
