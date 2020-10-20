package Logic.Enemies;

import Logic.Actions.*;
import Logic.Players.Player;

public class Monster extends Enemy {
    int vision_range;

    public Monster(char display,String name, int health, int attack, int defense, int vision_range, int experienceValue){
        super.display = display;
        super.name = name;
        health_pool = health;
        health_amount = health_pool;
        attack_points = attack;
        defense_points = defense;
        experience_value = experienceValue;
        this.vision_range = vision_range;
    }

    @Override
    public Action EnemyTurn(Player player) {
        if (RangeFrom(player) < vision_range){
            return FollowPlayer(player);
        }
        else
            return GetRandomMove();

    }

    Action FollowPlayer(Player player) {
        double rangeX = xRange(player);
        double rangeY = yRange(player);
        if (Math.abs(rangeX) > Math.abs(rangeY)){
            if (rangeX > 0)
                return new MoveLeft(this    );
            else
                return new MoveRight(this   );
        }
        else {
            if (rangeY > 0)
                return new MoveUp(this  );
            else
                return new MoveDown(this  );
        }
    }

    Action GetRandomMove(){
        int rand = random.nextInt(5 ); // 5 options, 4 directions and stay foot
        if (rand == 0)
            return new MoveDown(this);
        else if (rand == 1)
            return new MoveUp(this  );
        else if (rand == 2)
            return new MoveLeft(this);
        else if (rand == 3)
            return new MoveRight(this   );
        // else (rand == 4) - do nothing
        return new Nothing();
    }

    @Override
    public String describe() {
        return EnemyDescribe() + " Vision Range: " + vision_range;
    }
}
