package validation;

import logic.enums.Colors;
import logic.move.Move;
import logic.pieces.Piece;

public class ValidatorImpl implements Validator{

    public boolean validate(Piece[][] board, Move move, Colors playerColor){

        return MoveValidation.validate(board, move, playerColor) && RuleValidation.validate(board, move, playerColor);

    }

}
