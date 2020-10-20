package Logic.Players;


import Logic.Enemies.Enemy;
import Logic.Tiles.HeroicUnit;
import Logic.Level;
import Logic.Tiles.Tile;
import Logic.Tiles.Unit;

import java.util.ArrayList;

public abstract class Player extends Unit implements HeroicUnit {


    private final int FIRST_LEVEL = 1;
    private final int BEGIN_EXP = 0;
    private int experience = BEGIN_EXP;
    private int player_level = FIRST_LEVEL;


    public void setDISPLAY(char display) {
        super.display = display;
    }

    int getPlayer_level() {
        return player_level;
    }


    private void addExperience(int value) {
        experience = experience + value;
        while (experience >= 50 * player_level)
            OnLevelUp();
    }

    void GenericLevelUp() {
        experience = experience - (50 * player_level);
        player_level++;

        health_pool = health_pool + 10*player_level;
        health_amount = health_pool;
        attack_points = attack_points + 4*player_level;
        defense_points = defense_points + player_level;
    }

    protected abstract void OnLevelUp();

    public void castAbility(Level board){
        castAbility(board.getEnemies());
    }

    protected abstract void castAbility(ArrayList<Enemy> enemies);

    Enemy GetRandomEnemy(ArrayList<Enemy> enemies) {
        if (enemies.size() > 0)
            return enemies.get(random.nextInt(enemies.size())); // get enemy at random index
        return null; // given empty enemy list
    }

    ArrayList<Enemy> GetInRangeEnemies(ArrayList<Enemy> enemies, double range){
        ArrayList<Enemy> inRange = new ArrayList<>();
        for (Enemy enemy : enemies){
            if (RangeFrom(enemy) < range) // in range
                inRange.add(enemy);
        }

        return inRange;
    }

    Enemy ClosestEnemy(ArrayList<Enemy> enemies){
        if (enemies.isEmpty())
            return null;
        Enemy closest = enemies.get(0);
        for (Enemy enemy : enemies){
            if (RangeFrom(enemy) < RangeFrom(closest))
                closest = enemy;
        }
        return closest;
    }
    public abstract void GameTick();


    public boolean Interact(Tile tile) {
        return tile.acceptInteraction(this); // each tire can accept interaction with player
    }
    public boolean acceptInteraction(Enemy enemy){ // Interacted by an enemy
        boolean player_died = enemy.Combat(this);
        // Jon Snow was killed by Lannister Solider.
        if (player_died) {
            messageDeliver.sendMessage(name + " was killed by " + enemy.getName() + ".");
            messageDeliver.sendMessage("You lost.");
        }
        return false; // if player dies enemy doesn't take his position
    }
    public boolean acceptInteraction(Player player)
    { // block movement
        return false;

    } // do nothing
    // player attacking enemy
    public boolean Attack(Enemy attacked){
        boolean enemy_died = Combat(attacked);
        if (enemy_died) { // if enemy is dead
            messageDeliver.sendMessage(attacked.getName() + " died. " + this.name + " gained " + attacked.getExpValue() + " experience" );
            addExperience(attacked.getExpValue());
        }
        return enemy_died;
    }

    void SpecialAttack(Enemy attacked, double attackPower){
        boolean enemy_died = super.SpecialAttack(attacked, attackPower);
        if (enemy_died) { // if enemy is dead
            messageDeliver.sendMessage(attacked.getName() + " died. " + this.name + " gained " + attacked.getExpValue() + " experience" );
            addExperience(attacked.getExpValue());
        }

    }

    public void acceptDeath(){
        deathCallback.onDeath(this);
    }



    String PlayerDescribe(){
        return UnitDescribe() + " Level: " + player_level + " Experience: " + experience+"/"+50*player_level;
    }
}

