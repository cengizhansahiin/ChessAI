package server;

import logic.ChessGame;
import server.dto.ServerToClientDTO;

public class ChessGameVisitor implements ServerVisitor{
    private ServerDataCollector gameCollector;
    private ServerDataCollector playerCollector;
    private ChessGame chessGame;
    private final Middleman middleman;
    public ChessGameVisitor() {
        this.middleman = new MiddlemanImpl();
    }
    public void visit(ChessGame chessGame) {
        if(this.chessGame == null) this.chessGame = chessGame;
        var clientData = createClientData();
        sendClientData(clientData);
    }

    private void sendClientData(ServerToClientDTO clientData) {
        middleman.setClientData(clientData);
        middleman.setClientDataReady(true);
    }

    private ServerToClientDTO createClientData() {
        var gameData = this.gameCollector.collect();
        var playerData = this.playerCollector.collect();
        return new ServerToClientDTO(null, playerData.getCapturedPieces(), playerData.getPlayerNumber(), playerData.getColor()
                ,gameData.getBoard(), gameData.getTurn());
    }
    public void setGameCollector(ServerDataCollector collector){
        this.gameCollector = collector;
    }
    public void setPlayerCollector(ServerDataCollector collector){
        this.playerCollector = collector;
    }


}
