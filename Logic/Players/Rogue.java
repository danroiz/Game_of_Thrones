package Logic.Players;

import Logic.Enemies.Enemy;

import java.util.ArrayList;

public class Rogue extends Player {
    private final String SPECIAL_ABILITY_NAME = "Fan of Knives";
    private final int DEFAULT_ENERGY = 100;
    private int cost;
    private int current_energy;

    public Rogue(char display, String name, int health, int attack, int defense,int cost){
        super.display = display;
        super.name = name;
        health_pool = health;
        health_amount = health_pool;
        attack_points = attack;
        defense_points = defense;
        this.cost = cost;
        current_energy = DEFAULT_ENERGY;
    }

    @Override
    protected void OnLevelUp() {
        int oldAttack = attack_points;
        int oldDefense = defense_points;
        int oldHealth = health_pool;

        GenericLevelUp();

        current_energy = DEFAULT_ENERGY;
        attack_points = attack_points + (3*getPlayer_level());

        int attackDelta = attack_points - oldAttack;
        int defenseDelta = defense_points - oldDefense;
        int healthDelta = health_pool - oldHealth;

        messageDeliver.sendMessage(name +" reached level " + getPlayer_level() +": +"+healthDelta+" Health, +"+attackDelta+" Attack, +"+defenseDelta+" Defense");
    }

    @Override
    public void GameTick(){
        current_energy = Math.min(current_energy+10,DEFAULT_ENERGY);
    }


    @Override
    public void castAbility(ArrayList<Enemy> enemies){
        if (current_energy < cost) {
            // Arya Stark tried to cast Fan of Knives, but there was not enough energy: 10/20.
            messageDeliver.sendMessage(name + " tried to cast " + SPECIAL_ABILITY_NAME + ", but there was not enough energy: " + current_energy+"/"+DEFAULT_ENERGY+".");
        }
        else {
            current_energy = current_energy - cost;
            messageDeliver.sendMessage(name +" cast "+SPECIAL_ABILITY_NAME+".");

            ArrayList<Enemy> inRange = GetInRangeEnemies(enemies, 2); // get all enemies within range of 2
            for (Enemy enemy : inRange) {
                SpecialAttack(enemy, attack_points);
            }
        }

    }

    @Override
    public String describe() {
        return PlayerDescribe() + " Energy: "+ current_energy+"/"+DEFAULT_ENERGY;
    }
}
