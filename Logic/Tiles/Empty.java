package Logic.Tiles;

import Logic.Enemies.Enemy;
import Logic.Players.Player;

import java.awt.*;

public class Empty extends Tile {


    public Empty(char c, Point point) {
        super.display = c;
        super.position = point;
    }

    public boolean acceptInteraction(Enemy enemy) { return true; }
    public boolean acceptInteraction(Player player){
        return true;
    }
}
