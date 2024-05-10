package logic.pieces;

import logic.enums.Colors;

public interface PieceFactory {
    Piece initPawn(Colors color, int axisX, int axisY);
    Piece initRook(Colors color, int axisX, int axisY);
    Piece initBishop(Colors color, int axisX, int axisY);
    Piece initKnight(Colors color, int axisX, int axisY);
    Piece initQueen(Colors color, int axisX, int axisY);
    Piece initKing(Colors color, int axisX, int axisY);
}
