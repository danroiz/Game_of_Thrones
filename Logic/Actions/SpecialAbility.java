package Logic.Actions;

import Logic.Tiles.HeroicUnit;
import Logic.Level;

public class SpecialAbility implements Action {

    private HeroicUnit heroicUnit;
    public SpecialAbility(HeroicUnit heroicUnit){
        this.heroicUnit = heroicUnit;
    }
    @Override
    public void Act(Level board) {
        heroicUnit.castAbility(board);
    }

}
