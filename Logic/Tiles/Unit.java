package Logic.Tiles;

import GUI.MessageDeliver;
import GUI.UnitDeathCallback;

import java.util.Random;

public abstract class Unit extends Tile implements Interactor, Deathable {

    protected String name;
    protected int health_pool;
    protected int  health_amount;
    protected int  attack_points;
    protected int  defense_points;

    protected Random random = new Random();

    protected UnitDeathCallback deathCallback;
    protected MessageDeliver messageDeliver;


    public void setDeathCallback(UnitDeathCallback deathCallback){
        this.deathCallback = deathCallback;
    }

    public void setMessageDeliver(MessageDeliver messageDeliver){
        this.messageDeliver = messageDeliver;
    }


    protected String UnitDescribe(){
        // Thoros of Myr           Health: 231/250         Attack: 25              Defense: 4
        return name + " Health: " + health_amount+"/"+health_pool + " Attack: " + attack_points + " Defense: " + defense_points;
    }

    public abstract void acceptDeath();

    private boolean isDead(){
        if (health_amount <= 0) {
            health_amount = 0;
            acceptDeath();
            return true;
        }
        return false;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){return name;}
    public abstract String describe();


    // returns true if attacked is dead
    public boolean Combat(Unit attacked){
        messageDeliver.sendMessage(name + " engaged in combat with " + attacked.name +".\n"+describe()+"\n"+attacked.describe());
        int attackPoints = random.nextInt(attack_points);
        int defensePoints = random.nextInt(attacked.defense_points );
        int totalDamage = attackPoints-defensePoints;
        if (totalDamage > 0){
            attacked.health_amount = attacked.health_amount - totalDamage;
        }
        else
            totalDamage = 0;
        messageDeliver.sendMessage(CombatMessageBuilder(this,attacked, attackPoints, defensePoints, totalDamage));
        return attacked.isDead();
    }

    private String CombatMessageBuilder(Unit attacker, Unit attacked, int attack_points, int defense_points, int damage){
        //String msg =  attacker.name + " engaged in combat with " + attacked.name +".\n"+attacker.describe()+"\n"+attacked.describe();

        return attacker.name + " rolled " + attack_points + " attack points." +
                "\n" + attacked.name + " rolled " + defense_points + " defense points." +
                "\n" + attacker.name + " dealt " + damage + " damage to " + attacked.name + ".";
    }
    // returns true if attacked is dead
    protected boolean SpecialAttack(Unit attacked, double attackPower){
        int defensePoints = random.nextInt(attacked.defense_points);
        int totalDamage = (int) attackPower-defensePoints;
        if (totalDamage > 0){
            attacked.health_amount = attacked.health_amount - totalDamage;
        }
        else
            totalDamage = 0;

        messageDeliver.sendMessage(SpecialAttackMessageBuilder(this,attacked,attackPower,defensePoints,totalDamage));
        return attacked.isDead();
    }

    private String SpecialAttackMessageBuilder(Unit attacker, Unit attacked, double attackPower, int defensePoints, int totalDamage){
        // Bonus Trap rolled 0 defense points.
        //Melisandre hit Bonus Trap for 15 ability damage.
        String msg = attacked.name + " rolled " + defensePoints + " defense points.";
        msg += "\n" + attacker.name + " hit " + attacked.name + " for " + totalDamage + " ability damage.";
        return msg;
    }

    public abstract boolean Interact(Tile tile); // get if it was enemy or player turn

}
