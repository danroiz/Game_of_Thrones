package Logic.Tiles;

import Logic.Enemies.Enemy;
import Logic.Players.Player;

public interface Interactable { // for now tile can be interacted by only enemy or player units
    boolean acceptInteraction(Enemy enemy);
    boolean acceptInteraction(Player player);
}
