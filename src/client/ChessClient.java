package client;

import logic.ChessGamePrinter;
import logic.move.Move;
import logic.pieces.Piece;
import players.Player;
import players.PlayerFactory;
import players.PlayerFactoryImpl;
import server.dto.ServerToClientDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChessClient {

    private final Socket socket;

    private final ObjectInputStream inputStream;

    private final ObjectOutputStream outputStream;

    private Player player;

    private final PlayerFactory playerFactory;

    public ChessClient(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.playerFactory = new PlayerFactoryImpl();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }
    public void play() throws IOException, ClassNotFoundException {
        while(socket.isConnected()){
            System.out.println("Waiting for server response...");
            var data = (ServerToClientDTO) inputStream.readObject();
            handleData(data);
            if(data.getMessage().equals("END")) break;
        }
        this.socket.close();
    }

    private void handleData(ServerToClientDTO data) throws IOException {
        switch(data.getMessage()){
            case "START" -> {
                System.out.println("Game is starting!");
                ChessGamePrinter.printBoard(data.getBoard());
                this.player = this.playerFactory.initHumanPlayer(data.getColor(), data.getPlayerNumber());
                System.out.println("Your color is " + data.getColor());
            }
            case "CONTINUE" -> {
                ChessGamePrinter.printBoard(data.getBoard());
                System.out.println("Your captured pieces: " + data.getCapturedPieces());
                System.out.println("Your turn.");
                var move = this.player.makeMove(data.getBoard());
                if(move.isPresent()) this.outputStream.writeObject(move.get());
                else this.outputStream.writeObject(null);
                this.outputStream.reset();
            }
            case "END" -> {
                System.out.println("Game Over!");
                ChessGamePrinter.printBoard(data.getBoard());
                System.out.println("Your captured pieces: " + data.getCapturedPieces());
                if(data.getColor() != null) System.out.println("Winner is " + data.getColor() + ", Player " + data.getPlayerNumber());
                else System.out.println("Stalemate !!");
                System.out.println("Total move count is " + data.getTurn());

            }
            case "PROMOTION" -> {
                var piece = player.promotePawn();
                var value = resolvePieceValue(piece);
                this.outputStream.writeObject(value);
                this.outputStream.reset();
            }
        }
    }

    private Move resolvePieceValue(Piece piece) {
        switch (piece.getPieceType()){
            case BISHOP -> {
                return new Move(3, 1, 0, 0);
            }
            case KNIGHT -> {
                return new Move(3, 2, 0, 0);
            }
            case ROOK -> {
                return new Move(5, 0, 0, 0);
            }
            case QUEEN -> {
                return new Move(9, 0, 0, 0);
            }
        }
        return null;
    }


}
