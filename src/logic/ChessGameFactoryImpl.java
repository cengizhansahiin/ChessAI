package logic;

import logic.enums.Colors;
import players.ClientPlayer;
import players.PlayerFactory;
import players.PlayerFactoryImpl;
import server.ChessGameVisitor;

public class ChessGameFactoryImpl implements ChessGameFactory{

    private final PlayerFactory playerFactory;

    public ChessGameFactoryImpl(){
        this.playerFactory = new PlayerFactoryImpl();
    }

    @Override
    public ChessGame initLocal2Player() {
        return new ChessGameImpl(this.playerFactory.initHumanPlayer(Colors.WHITE,1)
                ,this.playerFactory.initHumanPlayer(Colors.BLACK, 2));
    }

    @Override
    public ChessGame initServerClientPlayer(Colors player1Color, Colors player2Color) {
        ChessGameVisitor serverVisitor = new ChessGameVisitor();
        var clientPlayer = this.playerFactory.initClientPlayer(player2Color, 2, serverVisitor);
        ChessGameImpl chessGame;
        if(player1Color == Colors.BLACK) chessGame = new ChessGameImpl(clientPlayer, this.playerFactory.initHumanPlayer(player1Color,1));
        else chessGame = new ChessGameImpl(this.playerFactory.initHumanPlayer(player1Color,1), clientPlayer);
        ((ClientPlayer) clientPlayer).setChessGame(chessGame);
        serverVisitor.setGameCollector(chessGame);
        serverVisitor.setPlayerCollector((ClientPlayer) clientPlayer);
        chessGame.accept(serverVisitor);

        return chessGame;
    }

}
