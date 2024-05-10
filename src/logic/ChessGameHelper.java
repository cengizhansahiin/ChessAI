package logic;

import exceptions.PawnPromotionException;
import logic.dto.BoardNPieceDTO;
import logic.enums.Colors;
import logic.enums.PieceType;
import logic.move.Move;
import logic.pieces.King;
import logic.pieces.Pawn;
import logic.pieces.Piece;
import logic.pieces.Rook;
import players.Player;
import validation.Validator;

import java.util.Arrays;
import java.util.Optional;


public class ChessGameHelper{
    public static BoardNPieceDTO applyMoveToBoard(Move move, Piece[][] board) {

        var selectedPiece = board[move.sourceY()][move.sourceX()];
        Optional<Piece> capturedPiece;

        if(board[move.targetY()][move.targetX()] != null && board[move.targetY()][move.targetX()].getColor() != board[move.sourceY()][move.sourceX()].getColor()) {
            capturedPiece = Optional.of(board[move.targetY()][move.targetX()]);
            board[move.targetY()][move.targetX()] = selectedPiece;
            board[move.sourceY()][move.sourceX()] = null;
            return new BoardNPieceDTO(board, capturedPiece);
        }

        //Detect if the move is castle and then the logic comes
        else if(board[move.targetY()][move.targetX()] != null && board[move.targetY()][move.targetX()].getColor() == board[move.sourceY()][move.sourceX()].getColor()){
            if(move.targetX() - move.sourceX() > 0){
                board[move.sourceY()][move.sourceX() + 2] = selectedPiece;
                board[move.sourceY()][move.sourceX()] = null;
                board[move.targetY()][move.targetX() - 2] = board[move.targetY()][move.targetX()];
            }
            else {
                board[move.sourceY()][move.sourceX() - 3] = selectedPiece;
                board[move.sourceY()][move.sourceX()] = null;
                board[move.targetY()][move.targetX() + 2] = board[move.targetY()][move.targetX()];
            }
            board[move.targetY()][move.targetX()] = null;
        }
        else{
            board[move.targetY()][move.targetX()] = selectedPiece;
            board[move.sourceY()][move.sourceX()] = null;
        }
        return new BoardNPieceDTO(board, Optional.empty());

    }

    public static void pawnPromotion(Move move, Piece[][] board) throws PawnPromotionException {
        var selectedPiece = board[move.sourceY()][move.sourceX()];
        if(selectedPiece.getPieceType() == PieceType.PAWN){
            if((selectedPiece.getColor() == Colors.WHITE && move.targetY() == 7)
                    || (selectedPiece.getColor() == Colors.BLACK && move.targetY() == 0)){
                    throw new PawnPromotionException();
            }
        }
    }
    public static int[] findTheKing(Colors color, Piece[][] board){
        for(int axisY = 0; axisY < ChessGame.BOARD_SIZE; axisY++){
            for(int axisX = 0; axisX < ChessGame.BOARD_SIZE; axisX++){
                Piece piece = board[axisY][axisX];
                if(piece != null && piece.getPieceType() == PieceType.KING && piece.getColor().equals(color)) return new int[]{axisX, axisY};
            }
        }
        return null;
    }


    public static boolean isCheckMate(Piece[][] board, Player player, Validator validator) {

            for (Piece[] rows: board) {
                if(Arrays.stream(rows).anyMatch(piece -> {
                    if(piece != null && piece.getColor() == player.getColor()){
                    return piece.listPossibleMoves(board).stream().anyMatch(move -> validator.validate(board, move, player.getColor()));
                    }
                    return false;
                }
                )) return false;
            }
            return true;
        }

    public static Piece[][] resolveMove(BoardNPieceDTO boardNPieceDTO, Player player) {

        boardNPieceDTO.getCapturedPiece().ifPresent(player::capturePiece);

        return boardNPieceDTO.getBoard();

    }

    public static void setPromotedPawn(Piece[][] board, Piece promotedPiece, Optional<Move> move) {

        board[move.get().sourceY()][move.get().sourceX()] = promotedPiece;

    }

    public static void setKingUnderCheck(Colors color, Piece[][] board, boolean flag) {
        int[] playerKingLocation = ChessGameHelper.findTheKing(color, board);
        assert playerKingLocation != null;
        ((King) board[playerKingLocation[1]][playerKingLocation[0]]).setIsUnderCheck(flag);
    }

    public static Piece[][] transferBoardElementsToAnotherBoard(Piece[][] board){

        Piece[][] newBoard = new Piece[ChessGame.BOARD_SIZE][ChessGame.BOARD_SIZE];
        for(int axisY = 0; axisY < ChessGame.BOARD_SIZE; axisY++){
            System.arraycopy(board[axisY], 0, newBoard[axisY], 0, ChessGame.BOARD_SIZE);
        }
        return newBoard;

    }
    public static boolean detectCheckForColor(Colors playerColor, Piece[][] board) {

        var playersKingLocation = findTheKing(playerColor, board);
        boolean isChecked = false;

        for(Piece[] row: board){
            for(Piece piece: row){
                if(piece != null){
                    if (piece.getPieceType() != PieceType.KING && piece.getColor() != playerColor) {
                        var possibleMoves = piece.listPossibleMoves(board);
                        if (possibleMoves.stream().anyMatch((move -> {
                            assert playersKingLocation != null;
                            return move.targetX() == playersKingLocation[0] && move.targetY() == playersKingLocation[1];
                        }))) {
                            isChecked = true;
                        }
                    }
                }
            }
        }

        return isChecked;

    }

    public static void setPieceAttributes(Move move, Piece[][] board) {

        var selectedPiece = board[move.targetY()][move.targetX()];
        if(selectedPiece != null){
            if(selectedPiece.getPieceType() == PieceType.KING) ((King) selectedPiece).setHasMoved(true);
            if(selectedPiece.getPieceType() == PieceType.PAWN) ((Pawn) selectedPiece).setFirstMove(false);
            if(selectedPiece.getPieceType() == PieceType.ROOK) ((Rook) selectedPiece).setHasMoved(true);
            selectedPiece.setPosition(move.targetX(), move.targetY());
        }
        else{
            if(move.sourceX() - move.targetX() < 0){
                board[move.targetY()][move.targetX() - 1].setPosition(move.targetX() - 1,move.targetY());
                if(board[move.targetY()][move.targetX() - 1].getPieceType() == PieceType.KING) ((King) board[move.targetY()][move.targetX() - 1]).setHasMoved(true);
                board[move.sourceY()][move.sourceX() + 1].setPosition(move.sourceX() + 1, move.sourceY());
                if(board[move.sourceY()][move.sourceX() + 1].getPieceType() == PieceType.ROOK) ((Rook) board[move.sourceY()][move.sourceX() + 1]).setHasMoved(true);
            }
            else{
                board[move.targetY()][move.targetX() + 1].setPosition(move.targetX() + 1,move.targetY());
                if(board[move.targetY()][move.targetX() + 1].getPieceType() == PieceType.KING) ((King) board[move.targetY()][move.targetX() + 1]).setHasMoved(true);
                board[move.sourceY()][move.sourceX() - 2].setPosition(move.sourceX() - 2, move.sourceY());
                if(board[move.sourceY()][move.sourceX() - 2].getPieceType() == PieceType.ROOK) ((Rook) board[move.sourceY()][move.sourceX() - 2]).setHasMoved(true);

            }
        }
    }

    public static boolean isStalemate(Piece[][] board, Player player, Validator validator) {

        for (Piece[] rows: board) {
            if(Arrays.stream(rows).anyMatch(piece -> {
                        if(piece != null && piece.getColor() == player.getColor()){
                            return piece.listPossibleMoves(board).stream().anyMatch(move -> validator.validate(board, move, player.getColor()));
                        }
                        return false;
                    }
            )) return false;
        }
        return true;
    }

    public static void writeEndGameStatus(Colors winnerColor, int winnerNumber, boolean isPlaying, int turn) {

        ChessGameStatus.winnerColor = winnerColor;
        ChessGameStatus.winnerNumber = winnerNumber;
        ChessGameStatus.isPlaying = false;
        ChessGameStatus.moveCount = turn;

    }
}
