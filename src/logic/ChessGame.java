package logic;

import logic.pieces.*;
import logic.enums.Colors;
import logic.enums.PieceType;
import players.Player;
import server.ServerVisitor;
import validation.Validator;
import validation.ValidatorImpl;
import java.io.Serializable;

public abstract class ChessGame implements Serializable {

    protected final Validator validator;
    protected final PieceFactory pieceFactory;
    protected Player player1;
    protected Player player2;

    protected Piece[][] board;

    public static final int BOARD_SIZE = 8;
    protected Player winner;
    protected int turn;
    protected boolean isPlaying;

    public ChessGame(Player player1, Player player2){
        this.pieceFactory = new PieceFactoryImpl();
        this.validator = new ValidatorImpl();

        board = initPieces();
        this.player1 = player1;
        this.player2 = player2;
    }
    public abstract void play();
    protected abstract Piece[][] initPieces();
    public abstract void accept(ServerVisitor serverVisitor);



}
