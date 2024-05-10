package server;

import logic.move.Move;
import server.dto.ServerToClientDTO;

import java.util.Optional;

public interface Middleman {
    boolean isClientDataReady();
    void setClientDataReady(boolean value);
    boolean isClientResponseReady();
    void setClientResponseReady(boolean value);
    ServerToClientDTO getClientData();
    void setClientData(ServerToClientDTO data);
    Optional<Move> getClientResponse();
    void setClientResponse(Optional<Move> response);

}
