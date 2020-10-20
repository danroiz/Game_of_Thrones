package GUI;

import Logic.Enemies.Enemy;
import Logic.Players.Player;

public interface UnitDeathCallback {
    void onDeath(Player p);
    void onDeath(Enemy enemy);
}
