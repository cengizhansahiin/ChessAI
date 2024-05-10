package server;

import logic.ChessGame;
import logic.ChessGameFactory;
import logic.enums.Colors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChessServer {

    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private ChessGame chessGame;
    private ChessGameFactory chessGameFactory;

    public ChessServer(int port) throws Exception {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        this.pool = Executors.newSingleThreadExecutor();
    }
    public void run() throws IOException {
        System.out.println("Waiting for player...");
        Socket clientSocket = serverSocket.accept();
        this.pool.execute(new ClientProxy(clientSocket));
        System.out.println(clientSocket.getInetAddress().getHostAddress() + " is connected.");
        this.chessGame.play();
        this.pool.shutdown();

    }
    public void configure(Colors player1Color, Colors player2Color){
        this.chessGame = chessGameFactory.initServerClientPlayer(player1Color, player2Color);
        }

    public void setChessGameFactory(ChessGameFactory chessGameFactory){
        this.chessGameFactory = chessGameFactory;
    }
}
