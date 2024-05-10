package validation;

import logic.enums.Colors;
import logic.move.Move;
import logic.pieces.Piece;

public interface Validator {
    boolean validate(Piece[][] board, Move move, Colors playerColor);
}
