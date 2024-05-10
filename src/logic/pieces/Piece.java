package logic.pieces;


import java.io.Serializable;
import java.util.List;

import logic.move.Move;
import logic.enums.Colors;
import logic.enums.PieceType;

public abstract class Piece implements Serializable {

    private final Colors color;
    private final PieceType pieceType;
    private int x;
    private int y;

    public Piece(Colors color, PieceType pieceType, int x, int y){
        this.x = x;
        this.y = y;
        this.color = color;
        this.pieceType = pieceType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract List<Move> listPossibleMoves(Piece[][] board);

    public Colors getColor(){
        return color;
    }
    public PieceType getPieceType(){
        return pieceType;
    }

    @Override
    public String toString(){
        return String.valueOf(getPieceType());
    }



}
