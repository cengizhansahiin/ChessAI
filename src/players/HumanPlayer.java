package players;

import exceptions.NotValidInput;
import logic.enums.Colors;
import logic.move.Move;
import logic.move.MoveMapper;
import logic.pieces.*;
import java.util.Optional;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class HumanPlayer extends Player{

    public HumanPlayer(Colors color, int playerNumber) {
        super(color, playerNumber, new Stack<>());
    }

    @Override
    public Optional<Move> makeMove(Piece[][] board) {

        var userInput = getMoveFromUser();
        Optional<Move> move;
        try{
            move = decodeInputToMove(userInput);
            if(move.isEmpty()) return Optional.empty();
        }
        catch (NotValidInput e){
            System.err.println(e.getMessage());
            return makeMove(board);
        }


        return move;
    }

    private Optional<Move> decodeInputToMove(String userMove) throws NotValidInput{
        Pattern pattern = Pattern.compile("^[A-H][0-8] [A-H][0-8]$");
        if(userMove.isEmpty() || userMove.isBlank()) return Optional.empty();
        if(!pattern.matcher(userMove).matches()) throw new NotValidInput();
        var separated = userMove.split(" ");
        int sourceX = MoveMapper.decodeChar.get(String.valueOf(separated[0].charAt(0)));
        int sourceY = Integer.parseInt(String.valueOf(separated[0].charAt(1))) - 1;
        int targetX = MoveMapper.decodeChar.get(String.valueOf(separated[1].charAt(0)));
        int targetY = Integer.parseInt(String.valueOf(separated[1].charAt(1))) - 1;
        return Optional.of(new Move(targetX, targetY, sourceX, sourceY));
    }

    private String getMoveFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your move (Example: B4 D4): ");
        return sc.nextLine().toUpperCase();
    }

    public Piece promotePawn(){
        System.out.println("Please enter the piece code for promotion. (Example: Qu, Bi, Kn, Ro)");
        Scanner sc = new Scanner(System.in);
        var selection = sc.nextLine().trim();
        switch (selection) {
            case "Qu" -> {
                return new Queen(getColor(), 0, 0);
            }
            case "Bi" -> {
                return new Bishop(getColor(), 0, 0);
            }
            case "Kn" -> {
                return new Knight(getColor(), 0, 0);
            }
            case "Ro" -> {
                return new Rook(getColor(), 0, 0);
            }
            default -> {
                System.out.println("Please enter the correct piece code (Example: Qu, Bi, Kn, Ro)");
                return promotePawn();
            }
        }
    }

}
