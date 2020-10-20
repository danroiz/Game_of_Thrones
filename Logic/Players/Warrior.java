package Logic.Players;

import Logic.Enemies.Enemy;
import java.util.ArrayList;

public class Warrior extends Player {
    private final String SPECIAL_ABILITY_NAME = "Avenger's Shield";
    private int ability_cooldown;
    private int remaining_cooldown;

    public Warrior(char display, String name, int health, int attack, int defense, int ability_cooldown){
        super.display = display;
        super.name = name;
        health_pool = health;
        health_amount = health_pool;
        attack_points = attack;
        defense_points = defense;
        this.ability_cooldown = ability_cooldown;
        remaining_cooldown = 0;
    }
    public void GameTick(){
        if (remaining_cooldown > 0)
            remaining_cooldown--;
    }

    @Override
    protected void OnLevelUp() {
        int oldAttack = attack_points;
        int oldDefense = defense_points;
        int oldHealth = health_pool;

        GenericLevelUp();

        remaining_cooldown = 0;
        health_pool = health_pool + (5*getPlayer_level());
        attack_points = attack_points + (2*getPlayer_level());
        defense_points = defense_points + getPlayer_level();

        int attackDelta = attack_points - oldAttack;
        int defenseDelta = defense_points - oldDefense;
        int healthDelta = health_pool - oldHealth;

        messageDeliver.sendMessage(name +" reached level " + getPlayer_level() +": +"+healthDelta+" Health, +"+attackDelta+" Attack, +"+defenseDelta+" Defense");
    }

    @Override
    public void castAbility(ArrayList<Enemy> enemies) {
        if (remaining_cooldown > 0){
            // Jon Snow tried to cast Avenger's Shield, but there is a cooldown: 2.
            messageDeliver.sendMessage(name + " tried to cast " + SPECIAL_ABILITY_NAME+", but there is a cooldown: " + remaining_cooldown);
        }
        else {
            remaining_cooldown = ability_cooldown+1;
            //int beforeHealing = health_amount;
            health_amount = Math.min(health_pool,health_amount+(10*defense_points));
            //int totalHealPoints = beforeHealing-health_amount;
            // Jon Snow used Avenger's Shield, healing for 40.
            messageDeliver.sendMessage(name + " used " + SPECIAL_ABILITY_NAME +", healing for " + (10*defense_points) +".");

            ArrayList<Enemy> inRange = GetInRangeEnemies(enemies,3); // get enemies within range of 3
            Enemy enemy = GetRandomEnemy(inRange);
            if (enemy != null) { // there's random enemy in valid range
                SpecialAttack(enemy, 0.1 * health_pool);
            }
        }

    }

    @Override
    public String describe() {
        return PlayerDescribe() + " Cooldown: "+ remaining_cooldown+"/"+ability_cooldown;
    }
}
