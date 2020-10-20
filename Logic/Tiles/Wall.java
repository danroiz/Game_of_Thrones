package Logic.Tiles;

import Logic.Enemies.Enemy;
import Logic.Players.Player;

import java.awt.*;

public class Wall extends Tile {

    public Wall (char display, Point position){
        super.display = display;
        super.position = position;
    }

    public boolean acceptInteraction(Enemy enemy) {
        return false;
    }
    public boolean acceptInteraction(Player player){
        return false;
    }

}
