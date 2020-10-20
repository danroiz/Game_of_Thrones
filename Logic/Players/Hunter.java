package Logic.Players;

import Logic.Enemies.Enemy;

import java.util.ArrayList;

public class Hunter extends Player {
    private final String SPECIAL_ABILITY_NAME = "Shoot";
    private int range;
    private int arrows_count = getPlayer_level()*10;
    private int ticks_count = 0;

    public Hunter(char display, String name, int health, int attack, int defense, int range) {
        super.display = display;
        super.name = name;
        health_pool = health;
        health_amount = health_pool;
        attack_points = attack;
        defense_points = defense;
        this.range = range;
    }

    @Override
    protected void OnLevelUp() {
        int oldAttack = attack_points;
        int oldDefense = defense_points;
        int oldHealth = health_pool;
        int oldArrowsAmount = arrows_count;
        GenericLevelUp();

        arrows_count += (10*getPlayer_level());
        attack_points += (2*getPlayer_level());

        int attackDelta = attack_points - oldAttack;
        int defenseDelta = defense_points - oldDefense;
        int healthDelta = health_pool - oldHealth;
        int arrowsDelta = arrows_count - oldArrowsAmount;

        messageDeliver.sendMessage(name +" reached level " + getPlayer_level() +": +"+healthDelta+" Health, +"+attackDelta+" Attack, +"+defenseDelta+" Defense, +"+arrowsDelta+" Arrows");
    }

    @Override
    public void castAbility(ArrayList<Enemy> enemies) {
        ArrayList<Enemy> inRange = GetInRangeEnemies(enemies,range+1); // return all enemies within distance *<* range. hunter range is *<=* so added +1 to range
        if (arrows_count == 0){ // out of arrows msg
            messageDeliver.sendMessage(name + " tried to cast " + SPECIAL_ABILITY_NAME+", but there is not enough arrows: " + arrows_count);
        }
        else if (inRange.isEmpty()){ // no enemy in range
            messageDeliver.sendMessage(name + " tried to shoot an arrow, but there were no enemies in range");
        }
        else { // attack the madafaka
            Enemy closest = ClosestEnemy(inRange);
            arrows_count--;
            messageDeliver.sendMessage(name + " fired an arrow at "+closest.getName()+".");
            SpecialAttack(closest,attack_points);

        }
    }

    @Override
    public void GameTick() {
        if (ticks_count == 10){
            arrows_count += getPlayer_level();
            ticks_count = 0;
        }
        else
            ticks_count++;
    }

    @Override
    public String describe() {
        return PlayerDescribe() + " Arrows: "+arrows_count+ " Range: " + range;
    }
}
