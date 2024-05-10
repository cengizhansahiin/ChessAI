package players;

import logic.enums.AIEngine;
import logic.enums.Colors;
import logic.move.Move;
import logic.pieces.Piece;

import java.util.Optional;
import java.util.Stack;

public class AIPlayer extends Player{

    //Coming soon!
    private final AIEngine engine;

    public AIPlayer(Colors color, int playerNumber, AIEngine engine) {
        super(color, playerNumber, new Stack<>());
        this.engine = engine;
    }

    @Override
    public Optional<Move> makeMove(Piece[][] board) {
        return null;
    }

    @Override
    public Piece promotePawn() {
        return null;
    }
}
