package logic.move;

import java.io.Serializable;
import java.util.Objects;


public record Move(int targetX, int targetY, int sourceX, int sourceY) implements Serializable {

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Move move)) return false;
        return (move.sourceX() == this.sourceX) &&
                (move.sourceY() == this.sourceY) &&
                (move.targetX() == this.targetX) &&
                (move.targetY() == this.targetY);

    }

    @Override
    public int hashCode(){

        return Objects.hash(targetX, targetY, sourceX, sourceY);
    }


}
