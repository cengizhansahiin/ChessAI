package server;

import logic.move.Move;
import server.dto.ServerToClientDTO;

import java.util.Optional;

public class MiddlemanImpl implements Middleman{

    private static ServerToClientDTO clientData;
    private static Optional<Move> clientResponse;
    private static boolean isClientDataReady = false;
    private static boolean isClientResponseReady = false;
    public synchronized boolean isClientDataReady() {
        return isClientDataReady;
    }

    public synchronized void setClientDataReady(boolean value) {
        isClientDataReady = value;
    }

    public synchronized boolean isClientResponseReady() {
        return isClientResponseReady;
    }

    public synchronized void setClientResponseReady(boolean value) {
        isClientResponseReady = value;
    }

    public synchronized ServerToClientDTO getClientData() {
        return clientData;
    }

    public synchronized void setClientData(ServerToClientDTO data) {
        clientData = data;
    }

    public synchronized Optional<Move> getClientResponse() {
        return clientResponse;
    }

    public synchronized void setClientResponse(Optional<Move> response) {
        clientResponse = response;
    }

}
