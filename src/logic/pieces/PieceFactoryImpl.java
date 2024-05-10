package logic.pieces;

import logic.enums.Colors;

public class PieceFactoryImpl implements PieceFactory{
    @Override
    public Piece initPawn(Colors color, int axisX, int axisY) {
        return new Pawn(color, axisX, axisY);
    }

    @Override
    public Piece initRook(Colors color, int axisX, int axisY) {
        return new Rook(color, axisX, axisY);
    }

    @Override
    public Piece initBishop(Colors color, int axisX, int axisY) {
        return new Bishop(color, axisX, axisY);
    }

    @Override
    public Piece initKnight(Colors color, int axisX, int axisY) {
        return new Knight(color, axisX, axisY);
    }

    @Override
    public Piece initQueen(Colors color, int axisX, int axisY) {
        return new Queen(color, axisX, axisY);
    }

    @Override
    public Piece initKing(Colors color, int axisX, int axisY) {
        return new King(color, axisX, axisY);
    }
}
