package Logic.Actions;

import Logic.Level;
import Logic.Tiles.Tile;
import Logic.Tiles.Unit;

import java.awt.*;

public class MoveRight implements Action {
    private Unit actedUnit;
    public MoveRight(Unit actedUnit){
        this.actedUnit = actedUnit;
    }
    public void Act(Level board) {
        Point destination = new Point(actedUnit.getX(),actedUnit.getY()+1);
        Tile interactedTile = board.getTile(destination);
        boolean canMove = actedUnit.Interact(interactedTile);
        if (canMove)
            board.Move(actedUnit,board.getTile(destination));
    }
}
