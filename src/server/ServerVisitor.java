package server;

import logic.ChessGame;
import server.dto.ServerToClientDTO;

public interface ServerVisitor {

    void visit(ChessGame chessGame);

}
