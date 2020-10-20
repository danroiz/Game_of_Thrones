package Logic.Enemies;

import Logic.Actions.Action;
import Logic.Actions.Nothing;
import Logic.Players.Player;

public class Trap extends Enemy {

    private final String INVISIBLE_STRING = ".";
    private int visibility_time;
    private int invisibility_time;
    private int ticks_count;
    private boolean visible;

    public Trap(char display, String name, int health, int attack, int defense, int expereience, int visibility_time, int invisibility_time){
        super.display = display;
        super.name = name;
        health_pool = health;
        health_amount = health_pool;
        attack_points = attack;
        defense_points = defense;
        experience_value = expereience;

        this.visibility_time = visibility_time;
        this.invisibility_time = invisibility_time;
        ticks_count = 0;

        visible = true;
    }


    @Override
    public Action EnemyTurn(Player player) {
        visible = ticks_count < visibility_time;
        if (ticks_count == (visibility_time+invisibility_time))
            ticks_count = 0;
        else
            ticks_count++;
        if (RangeFrom(player) < 2)
            Combat(player);
        return new Nothing(); // traps don't move
    }


    @Override
    public String toString(){
        if (visible)
            return super.toString();
        return INVISIBLE_STRING;
    }

    @Override
    public String describe() {
        return EnemyDescribe();
    }
}
