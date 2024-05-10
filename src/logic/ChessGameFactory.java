package logic;

import logic.enums.Colors;
import server.ServerVisitor;

public interface ChessGameFactory {

    ChessGame initLocal2Player();
    ChessGame initServerClientPlayer(Colors player1Color, Colors player2Color);
}
