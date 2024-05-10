package validation;

import logic.ChessGame;
import logic.enums.Colors;
import logic.enums.PieceType;
import logic.move.Move;
import logic.pieces.Piece;

import static logic.ChessGameHelper.*;


public class RuleValidation {


    public static boolean validate(Piece[][] board, Move move, Colors playerColor){

        if(detectCheckForColor(playerColor, board) && !doesMovePreventCheck(move, playerColor, board)) return false;

        if(doesMoveLeadCheckForOwn(playerColor, move, board)) return false;

        if(!doesMoveFollowTheCastleRule(move, board, playerColor)) return false;

        return true;

    }

    private static boolean doesMoveFollowTheCastleRule(Move move, Piece[][] board, Colors playerColor) {

        boolean isAllowed = true;

        if(board[move.sourceY()][move.sourceX()].getPieceType() == PieceType.KING)
            if(board[move.targetY()][move.targetX()] != null && board[move.targetY()][move.targetX()].getPieceType() == PieceType.ROOK)
                if(board[move.sourceY()][move.sourceX()].getColor() == board[move.targetY()][move.targetX()].getColor()){
                    if(move.targetX() - move.sourceX() > 0){
                        if(doesMoveLeadCheckForOwn(playerColor, new Move(move.sourceX(), move.sourceY(), move.sourceX() + 1, move.sourceY() ), board))
                            isAllowed = false;
                    }
                    else{
                        if(doesMoveLeadCheckForOwn(playerColor, new Move(move.sourceX(), move.sourceY(), move.sourceX() - 1, move.sourceY() ), board))
                            isAllowed = false;
                        if(doesMoveLeadCheckForOwn(playerColor, new Move(move.sourceX(), move.sourceY(), move.sourceX() - 2, move.sourceY() ), board))
                            isAllowed = false;
                    }
                }
        return isAllowed;
    }

//    public static boolean isMoveLeadFacingKings(Move move, Piece[][] board, Colors playerColor) {
//
//        var tempBoard = transferBoardElementsToAnotherBoard(board);
//        tempBoard = applyMoveToBoard(move, tempBoard).getBoard();
//        var kingsPosition = findTheKing(playerColor, tempBoard);
//        assert kingsPosition != null;
//        var finalTempBoard = tempBoard;
//        return tempBoard[kingsPosition[1]][kingsPosition[0]].listPossibleMoves(tempBoard).stream()
//                .map(possibleMove -> finalTempBoard[possibleMove.targetY()][possibleMove.targetX()].getPieceType().equals(PieceType.KING)).findAny().isPresent();
//
//    }

    private static boolean doesMoveLeadCheckForOwn(Colors playerColor, Move move, Piece[][] board) { //Takes board as parameter and validation methods will be separated to another class

        Piece[][] tempBoard = new Piece[ChessGame.BOARD_SIZE][ChessGame.BOARD_SIZE];

        return detectCheckForColor(playerColor, tempBoard);

    }

    public static boolean doesMovePreventCheck(Move move, Colors playerColor, Piece[][] board) {

        var tempBoard = transferBoardElementsToAnotherBoard(board);

        tempBoard = applyMoveToBoard(move, tempBoard).getBoard();

        return !detectCheckForColor(playerColor, tempBoard);

    }

}
