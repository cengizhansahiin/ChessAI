package players;

import logic.ChessGame;
import logic.enums.Colors;
import logic.move.Move;
import logic.pieces.*;
import server.Middleman;
import server.MiddlemanImpl;
import server.ServerDataCollector;
import server.ServerVisitor;
import server.dto.ServerToClientDTO;

import java.util.Optional;
import java.util.Stack;

public class ClientPlayer extends Player implements ServerDataCollector {

    private final ServerVisitor chessGameVisitor;
    private final Middleman middleman;
    private ChessGame chessGame;

    public ClientPlayer(Colors color, int playerNumber, ServerVisitor chessGameVisitor) {
        super(color, playerNumber, new Stack<>());
        this.chessGameVisitor = chessGameVisitor;
        this.middleman = new MiddlemanImpl();

    }

    @Override
    public Optional<Move> makeMove(Piece[][] board) {
        this.chessGame.accept(this.chessGameVisitor);
        while(true){
            if(this.middleman.isClientResponseReady()){
                this.middleman.setClientResponseReady(false);
                return this.middleman.getClientResponse();
            }
        }
    }

    @Override
    public Piece promotePawn() {
        var promotionServerToClient = new ServerToClientDTO("PROMOTION", null, 0, null, null, 0);
        this.middleman.setClientData(promotionServerToClient);
        this.middleman.setClientDataReady(true);
        while(true){
            if(this.middleman.isClientResponseReady()){
                this.middleman.setClientResponseReady(false);
                return resolveValueToPiece(this.middleman.getClientResponse());
            }
        }
    }

    private Piece resolveValueToPiece(Optional<Move> clientResponse) {
        if(clientResponse.isPresent()){
            var value = clientResponse.get();
            if(value.targetX() == 9) return new Queen(getColor(), 0, 0);
            if(value.targetX() == 5) return new Rook(getColor(), 0, 0);
            if(value.targetX() == 3 & value.targetY() == 2) return new Knight(getColor(), 0, 0);
            if(value.targetX() == 3 & value.targetY() == 1) return new Bishop(getColor(), 0, 0);

        }
        return new Queen(getColor(), 0, 0);
    }

    @Override
    public void invokeEndGame(){
        super.invokeEndGame();
        this.chessGame.accept(chessGameVisitor);
    }

    public void setChessGame(ChessGame chessGame){
        this.chessGame = chessGame;
    }

    @Override
    public ServerToClientDTO collect() {
        return new ServerToClientDTO(null, getCapturedPieces(), getPlayerNumber(), getColor(), null, 0);
    }
}
