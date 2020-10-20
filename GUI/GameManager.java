package GUI;


import Logic.Enemies.Enemy;
import Logic.Level;
import Logic.Players.Player;
import Logic.Tiles.Empty;
import java.awt.*;
import java.util.List;

class GameManager  {

    private final int FIRST_LEVEL = 0;

    private List<Level> levels;
    private List<Point> startPositions;

    private int currentLevel = FIRST_LEVEL;
    private MessageDeliver messageDeliver;
    private boolean win = false;
    private boolean runGame = true;
    private boolean nextLevel = false;


    // inner anonymous class.
    // responsibilities - handle the death of a player or an enemy
    private UnitDeathCallback deathCallback = new UnitDeathCallback() {
        @Override
        public void onDeath(Player p) {
            p.setDISPLAY('X');
            runGame = false;
        }

        @Override
        public void onDeath(Enemy enemy) {
            nextLevel = levels.get(currentLevel).RemoveEnemy(enemy,new Empty('.',enemy.getPosition())); // returns true if level is over (all enemies are dead)
        }
    };



    GameManager(List<Level> levels, List<Point> startPositions){
        this.levels = levels;
        messageDeliver = System.out::println; // given string, just print it
        this.startPositions = startPositions;
    }


    private Level LevelSetup(int levelToSetUp){
        if (levelToSetUp < levels.size()){ // there is such level
            Level playedLevel = levels.get(levelToSetUp);
            Player player = playedLevel.getPlayer();
            player.setPosition(startPositions.get(levelToSetUp)); // set start position of the player
            player.setDeathCallback(deathCallback); // set the player deathcallback
            player.setMessageDeliver(messageDeliver); // set the player message deliver
            for (Enemy enemy : playedLevel.getEnemies()){ // set for each enemy the message deliver and callback
                enemy.setDeathCallback(deathCallback);
                enemy.setMessageDeliver(messageDeliver);
            }
            System.out.println(playedLevel);
            System.out.println(player.describe());
            return playedLevel;
        }
        // else - no more levels
        win = true;
        return null;
    }

    void StartGame(){
        Play(currentLevel);
    }
    private void Play(int currentLevel) {
        Level playedLevel = LevelSetup(currentLevel);
        while (!win && playedLevel != null && runGame){
            Player player = playedLevel.getPlayer();
            playedLevel.Round();
            if (nextLevel){
                this.currentLevel++;
                nextLevel = false;
                Play(this.currentLevel);
            }
            System.out.println(playedLevel.toString());
            System.out.println(playedLevel.getPlayer().describe());
        }
        if (!runGame) { // lost
            System.out.println("Game Over.");
        }
        else { // no more levels = win the game
            System.out.println("You won!");
        }
        System.exit(0);
    }

}

