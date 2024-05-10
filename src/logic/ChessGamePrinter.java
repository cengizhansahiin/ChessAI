package logic;

import logic.enums.Colors;
import logic.enums.PieceType;
import logic.pieces.Piece;

public class ChessGamePrinter {
    public static void printBoard(Piece[][] board){
        System.out.println("------------------------------------------");
        for(int axisY = ChessGame.BOARD_SIZE- 1; axisY>=0;axisY--){
            if(axisY == 7) System.out.println("   A    B    C    D    E    F    G    H");
            System.out.print(axisY + 1);
            for(int axisX = 0; axisX<ChessGame.BOARD_SIZE ;axisX++){
                System.out.print("|" + getPieceCode(board[axisY][axisX]) + "|");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------");

    }
    private static String getPieceCode(Piece piece){
        if(piece != null) {
            if (piece.getColor() == Colors.WHITE) {
                if (piece.getPieceType() == PieceType.PAWN) return "wPa";
                else if (piece.getPieceType() == PieceType.KNIGHT) return "wKn";
                else if (piece.getPieceType() == PieceType.BISHOP) return "wBi";
                else if (piece.getPieceType() == PieceType.ROOK) return "wRo";
                else if (piece.getPieceType() == PieceType.QUEEN) return "wQu";
                else return "wKi";
            } else {
                if (piece.getPieceType() == PieceType.PAWN) return "bPa";
                else if (piece.getPieceType() == PieceType.KNIGHT) return "bKn";
                else if (piece.getPieceType() == PieceType.BISHOP) return "bBi";
                else if (piece.getPieceType() == PieceType.ROOK) return "bRo";
                else if (piece.getPieceType() == PieceType.QUEEN) return "bQu";
                else return "bKi";
            }
        }
        else return "   ";
    }
}
