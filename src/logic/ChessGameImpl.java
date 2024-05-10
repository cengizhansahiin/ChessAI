package logic;

import exceptions.PawnPromotionException;
import logic.enums.Colors;
import logic.move.Move;
import logic.pieces.Piece;
import players.Player;
import server.ServerDataCollector;
import server.ServerVisitor;
import server.dto.ServerToClientDTO;

import java.io.Serializable;
import java.util.Optional;

public class ChessGameImpl extends ChessGame implements Serializable, ServerDataCollector {

    public ChessGameImpl(Player player1, Player player2) {
        super(player1, player2);
    }
    @Override
    public void play() {
        this.turn = 0;
        this.isPlaying = true;
        while (this.isPlaying) {
            System.out.println(player1.getColor() + "'s captured pieces: " + player1.getCapturedPieces());
            System.out.println(player2.getColor() + "'s captured pieces: " + player2.getCapturedPieces());
            ChessGamePrinter.printBoard(board);
            Optional<Move> move;
            if (this.turn % 2 == 0) {
                System.out.println("Player 1's turn");
                move = player1.makeMove(board);
                if (move.isPresent()) { //Surrender option is entering empty input as move
                    if (!validator.validate(board, move.get(), player1.getColor())) {
                        System.out.println("Not a valid move!");
                        continue;
                    } //Continue to player 1 make move by avoid incrementing the turn
                    try {
                        ChessGameHelper.pawnPromotion(move.get(), board);
                    } catch (PawnPromotionException e) {
                        var promotedPiece = player1.promotePawn();
                        ChessGameHelper.setPromotedPawn(board, promotedPiece, move);
                    }

                    var boardNPieceDTO = ChessGameHelper.applyMoveToBoard(move.get(), board);
                    ChessGameHelper.setPieceAttributes(move.get(), board);
                    board = ChessGameHelper.resolveMove(boardNPieceDTO, player1);

                    ChessGameHelper.setKingUnderCheck(player2.getColor(), board, ChessGameHelper.detectCheckForColor(player2.getColor(), board));

                    if (ChessGameHelper.isCheckMate(board, player2, validator)) {
                        this.isPlaying = false;
                        winner = player1;
                    } else if (ChessGameHelper.isStalemate(board, player2, validator)) {
                        this.isPlaying = false;
                    }
                } else {
                    this.isPlaying = false;
                    winner = player2;
                }
            } else {
                System.out.println("Player 2's turn");
                move = player2.makeMove(board);
                if (move.isPresent()) { //Surrender option is entering empty input as move
                    if (!validator.validate(board, move.get(), player2.getColor())) {
                        System.out.println("Not a valid move!");
                        continue;
                    } //Continue to player 2 make move by avoid incrementing the turn
                    try {
                        ChessGameHelper.pawnPromotion(move.get(), board);
                    } catch (PawnPromotionException e) {
                        var promotedPiece = player2.promotePawn();
                        ChessGameHelper.setPromotedPawn(board, promotedPiece, move);
                    }


                    var boardNPieceDTO = ChessGameHelper.applyMoveToBoard(move.get(), board);
                    ChessGameHelper.setPieceAttributes(move.get(), board);
                    board = ChessGameHelper.resolveMove(boardNPieceDTO, player2);

                    ChessGameHelper.setKingUnderCheck(player1.getColor(), board, ChessGameHelper.detectCheckForColor(player1.getColor(), board));


                    if (ChessGameHelper.isCheckMate(board, player1, validator)) {
                        this.isPlaying = false;
                        winner = player2;
                    } else if (ChessGameHelper.isStalemate(board, player1, validator)) {
                        this.isPlaying = false;
                    }
                } else {
                    this.isPlaying = false;
                    winner = player1;
                }
            }
            this.turn++;
        }
        invokeEndGame();
    }

    private void invokeEndGame() {
        if (winner != null) System.out.println("Player " + winner.getPlayerNumber() + ", " + this.winner.getColor() + " won!");
        else System.out.println("Stalemate!");
        System.out.println("Total move count is " + this.turn);
        System.out.println("------------------------------------------------------\n");
        ChessGameHelper.writeEndGameStatus(this.winner.getColor(), this.winner.getPlayerNumber(), this.isPlaying, this.turn );
        player1.invokeEndGame();
        player2.invokeEndGame();
        this.board = null;
        this.player1 = null;
        this.player2 = null;
        this.turn = 0;
        this.winner = null;
    }

    @Override
    protected Piece[][] initPieces() {
        Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int axisY = 0; axisY < ChessGame.BOARD_SIZE; axisY++) {
            for (int axisX = 0; axisX < ChessGame.BOARD_SIZE; axisX++) {
                if (axisY == 0) {
                    if (axisX == 0 || axisX == 7)
                        board[axisY][axisX] = pieceFactory.initRook(Colors.WHITE, axisX, axisY);
                    else if (axisX == 1 || axisX == 6)
                        board[axisY][axisX] = pieceFactory.initKnight(Colors.WHITE, axisX, axisY);
                    else if (axisX == 2 || axisX == 5)
                        board[axisY][axisX] = pieceFactory.initBishop(Colors.WHITE, axisX, axisY);
                    else if (axisX == 3) board[axisY][axisX] = pieceFactory.initQueen(Colors.WHITE, axisX, axisY);
                    else board[axisY][axisX] = pieceFactory.initKing(Colors.WHITE, axisX, axisY);
                } else if (axisY == 1) board[axisY][axisX] = pieceFactory.initPawn(Colors.WHITE, axisX, axisY);
                else if (axisY == 6) board[axisY][axisX] = pieceFactory.initPawn(Colors.BLACK, axisX, axisY);
                else if (axisY == 7) {
                    if (axisX == 0 || axisX == 7)
                        board[axisY][axisX] = pieceFactory.initRook(Colors.BLACK, axisX, axisY);
                    else if (axisX == 1 || axisX == 6)
                        board[axisY][axisX] = pieceFactory.initKnight(Colors.BLACK, axisX, axisY);
                    else if (axisX == 2 || axisX == 5)
                        board[axisY][axisX] = pieceFactory.initBishop(Colors.BLACK, axisX, axisY);
                    else if (axisX == 3) board[axisY][axisX] = pieceFactory.initQueen(Colors.BLACK, axisX, axisY);
                    else board[axisY][axisX] = pieceFactory.initKing(Colors.BLACK, axisX, axisY);
                }
            }
        }
        return board;
    }

    @Override
    public void accept(ServerVisitor serverVisitor) {
        serverVisitor.visit(this);
    }

    @Override
    public ServerToClientDTO collect() {
        return new ServerToClientDTO(null, null, 0, null, this.board, this.turn);
    }
}

