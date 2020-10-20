package Logic.Tiles;

import Logic.Enemies.Enemy;
import Logic.Players.Player;

import java.awt.*;

public abstract class Tile implements Interactable {
    protected char display;
    Point position;




    public int getX(){
        return (int) position.getX();
    }
    public int getY(){
        return (int) position.getY();
    }

    public String toString() {
        return display + "";
    }


    // return the euclidean distance from this to input tile
    protected double RangeFrom(Tile tile) {
        double rangeX = xRange(tile);
        double rangeY = yRange(tile);
        return Math.sqrt(Math.pow(rangeX, 2) + Math.pow(rangeY, 2));
    }
    // the distance between the widths
    protected double xRange(Tile tile) {
        return this.position.getY() - tile.position.getY();
    }

    // the height distance
    protected double yRange(Tile tile) {
        return this.position.getX() - tile.position.getX();
    }

    // visitor pattern
    public abstract boolean acceptInteraction(Enemy enemy);
    public abstract boolean acceptInteraction(Player player);

    public Point getPosition(){
        return position;
    }
    public void setPosition(Point newPosition){
        this.position = newPosition;
    }

}