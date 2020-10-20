package Logic.Enemies;

import Logic.Actions.Action;
import Logic.Players.Player;
import Logic.Tiles.*;

public abstract class Enemy extends Unit {
    int experience_value;

    public int getExpValue(){return experience_value;}


    String EnemyDescribe() {
        return UnitDescribe() + " Experience Value: " + experience_value;
    }

    public abstract Action EnemyTurn(Player player);

    @Override
    public boolean Interact(Tile tile){
        return tile.acceptInteraction(this); // each tire can accept interaction with enemy
    }


    public boolean acceptInteraction(Enemy enemy){ // block movement
        return false;
    }

    public boolean acceptInteraction(Player player) // Interacted by a player.
    {
        return player.Attack(this);

    }
    /*
    public boolean Attack(Player player){
        super.Attack(player);
        return false;
    }

     */


    public void acceptDeath(){
        deathCallback.onDeath(this);
    }

}
