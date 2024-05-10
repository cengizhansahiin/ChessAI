package players;

import logic.ChessGame;
import logic.enums.AIEngine;
import logic.enums.Colors;
import server.ServerVisitor;

public interface PlayerFactory {

    Player initHumanPlayer(Colors color, int playerNumber);
    Player initAIPlayer(Colors color, int playerNumber, AIEngine engine);
    Player initClientPlayer(Colors color, int playerNumber, ServerVisitor serverVisitor);

}
