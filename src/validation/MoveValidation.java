package validation;

import logic.enums.Colors;
import logic.move.Move;
import logic.pieces.Piece;

public class MoveValidation {

    public static boolean validate(Piece[][] board, Move move, Colors playerColor){

        var selectedPiece = board[move.sourceY()][move.sourceX()];
        if(selectedPiece == null) return false;
        if(selectedPiece.getColor() != playerColor) return false;
        var possibleMoves = selectedPiece.listPossibleMoves(board);
        if(possibleMoves.isEmpty()) return false;

        return possibleMoves.stream().anyMatch(possibleMove -> possibleMove.equals(move));

    }


}
