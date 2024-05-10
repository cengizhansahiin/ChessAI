package server;

import logic.ChessGameStatus;
import logic.move.Move;
import server.dto.ServerToClientDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class ClientProxy implements Runnable{

    private final Socket clientSocket;

    private final Middleman middleman;

    private final ObjectOutputStream outputStream;

    private final ObjectInputStream inputStream;

    public ClientProxy(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.middleman = new MiddlemanImpl();
        this.outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (sendStartData()) ;
            while (ChessGameStatus.isPlaying) {
                if (this.middleman.isClientDataReady()) {
                    var data = this.middleman.getClientData();
                    this.middleman.setClientDataReady(false);
                    sendData(data);
                    var clientResponse = getDataFromClient();
                    handleClientResponse(clientResponse);

                }
            }
            while (sendEndData()) ;
            this.outputStream.close();
            this.inputStream.close();
            this.clientSocket.close();
        }
        catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
            run();
        }
    }

    private void handleClientResponse(Optional<Move> clientResponse) {
        this.middleman.setClientResponse(clientResponse);
        this.middleman.setClientResponseReady(true);
    }

    private Optional<Move> getDataFromClient() throws IOException, ClassNotFoundException {
        Move clientResponse = (Move) this.inputStream.readObject();
        if(clientResponse == null) return Optional.empty();
        return Optional.of(clientResponse);
    }

    private boolean sendEndData() throws IOException {
        if(this.middleman.isClientDataReady()){
            var data = this.middleman.getClientData();
            this.middleman.setClientDataReady(false);
            data.setColor(ChessGameStatus.winnerColor);
            data.setTurn(ChessGameStatus.moveCount);
            data.setPlayerNumber(ChessGameStatus.winnerNumber);
            data.setMessage("END");
            sendData(data);
            return false;
        }
        return true;
    }

    private boolean sendStartData() throws IOException {
        if(this.middleman.isClientDataReady()){
            var data = this.middleman.getClientData();
            this.middleman.setClientDataReady(false);
            data.setMessage("START");
            sendData(data);
            return false;
        }
        return true;
    }
    private void sendData(ServerToClientDTO data) throws IOException {
        if(data.getMessage() == null) data.setMessage("CONTINUE");
        this.outputStream.writeObject(data);
        this.outputStream.reset();
    }
}
