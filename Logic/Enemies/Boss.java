package Logic.Enemies;

import Logic.Actions.*;
import Logic.Tiles.HeroicUnit;
import Logic.Level;
import Logic.Players.Player;


public class Boss extends Monster implements HeroicUnit {
    private final String SPECIAL_ABILITY_NAME = "Boss Rage";
    private int ability_frequency;
    private int combat_ticks = 0;

    public Boss(char display,String name, int health, int attack, int defense, int vision_range, int experienceValue, int ability_frequency){
        super(display,name,health,attack,defense,vision_range,experienceValue);
        this.ability_frequency = ability_frequency;
    }

    @Override
    public Action EnemyTurn(Player player) {
        if (RangeFrom(player) < vision_range) {
            if (combat_ticks == ability_frequency) {
                combat_ticks = 0;
                return new SpecialAbility(this);
            } else {
                combat_ticks++;
                return FollowPlayer(player);
            }
        } else {
            combat_ticks = 0;
            return GetRandomMove();
        }
    }

    @Override
    public void castAbility(Level board) {
        castAbility(board.getPlayer());
    }

    private void castAbility(Player player) {
        messageDeliver.sendMessage(name +" cast "+SPECIAL_ABILITY_NAME+".");
        SpecialAttack(player,attack_points  );
    }

}
