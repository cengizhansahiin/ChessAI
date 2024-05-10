package players;

import logic.ChessGame;
import logic.enums.AIEngine;
import logic.enums.Colors;
import server.ServerVisitor;

public class PlayerFactoryImpl implements PlayerFactory{
    @Override
    public Player initHumanPlayer(Colors color, int playerNumber) {
        return new HumanPlayer(color, playerNumber);
    }

    @Override
    public Player initAIPlayer(Colors color, int playerNumber, AIEngine engine) {
        return new AIPlayer(color, playerNumber, engine);
    }

    @Override
    public Player initClientPlayer(Colors color, int playerNumber, ServerVisitor serverVisitor) {
        return new ClientPlayer(color, playerNumber, serverVisitor);
    }
}
