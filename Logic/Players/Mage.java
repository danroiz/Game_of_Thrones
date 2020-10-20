package Logic.Players;

import Logic.Enemies.Enemy;
import java.util.ArrayList;

public class Mage extends Player {

    private final String SPECIAL_ABILITY_NAME = "Blizzard";

    private int mana_pool;
    private int current_mana;
    private int mana_cost;
    private int spell_power;
    private int hits_count;
    private int abillity_range;

    public Mage(char display, String name, int health, int attack, int defense,int mana_pool, int mana_cost, int spell_power, int hits_count, int abillity_range){
        super.display = display;
        super.name = name;
        health_pool = health;
        health_amount = health_pool;
        attack_points = attack;
        defense_points = defense;
        this.mana_pool = mana_pool;
        this.current_mana = mana_pool/4;
        this.mana_cost = mana_cost;
        this.spell_power = spell_power;
        this.hits_count = hits_count;
        this.abillity_range = abillity_range;
    }

    @Override
    public void GameTick(){
        current_mana = Math.min(mana_pool, current_mana + getPlayer_level());
    }

    @Override
    protected void OnLevelUp() {
        int oldAttack = attack_points;
        int oldDefense = defense_points;
        int oldHealth = health_pool;
        int oldMana = mana_pool;
        int oldSpellPower = spell_power;

        GenericLevelUp();
        mana_pool = mana_pool + (25*getPlayer_level());
        current_mana = Math.min(current_mana+(mana_pool/4),mana_pool);
        spell_power = spell_power + (10*getPlayer_level());

        int attackDelta = attack_points - oldAttack;
        int defenseDelta = defense_points - oldDefense;
        int healthDelta = health_pool - oldHealth;
        int manaDelta = mana_pool - oldMana;
        int spellPowerDelta = spell_power - oldSpellPower;
        // +175 maximum mana, +70 spell power
        messageDeliver.sendMessage(name +" reached level " + getPlayer_level() +": +"+healthDelta+" Health, +"+attackDelta+" Attack, +"+defenseDelta+" Defense, +"+manaDelta+" Maximum Mana, "+spellPowerDelta+" Spell Power");
    }

    @Override
    public void castAbility(ArrayList<Enemy> enemies) {
        if (current_mana < mana_cost)
            // Thoros of Myr tried to cast Blizzard, but there was not enough mana: 1/20.
            messageDeliver.sendMessage(name + " tried to cast " + SPECIAL_ABILITY_NAME + ", but there was not enough mana: " + current_mana+"/"+mana_cost+".");
        else{
            current_mana = current_mana - mana_cost;
            //Melisandre cast Blizzard.
            messageDeliver.sendMessage(name +" cast "+SPECIAL_ABILITY_NAME+".");
            int hits = 0;

            //ArrayList<Enemy> inRange = GetInRangeEnemies(enemies, abillity_range); // get enemies within range of ability range
            Enemy enemy = GetRandomEnemy(GetInRangeEnemies(enemies, abillity_range)); // get random enemy from enemies in ability range
            while (hits < hits_count & enemy != null){
                SpecialAttack(enemy,spell_power);
                hits++;
                enemy = GetRandomEnemy(GetInRangeEnemies(enemies, abillity_range));
            }

        }
    }

    @Override
    public String  describe() {
        //  Mana: 48/150            Spell Power: 20
        return PlayerDescribe() + " Mana: "+current_mana+"/"+mana_pool + " Spell Power: " + spell_power;
    }
}
