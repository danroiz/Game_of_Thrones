package Tests;
import GUI.MessageDeliver;
import GUI.UnitDeathCallback;
import Logic.*;
import Logic.Actions.*;
import Logic.Enemies.Enemy;
import Logic.Enemies.Monster;
import Logic.Players.Player;
import Logic.Players.Warrior;
import Logic.Tiles.Empty;
import Logic.Tiles.Tile;
import Logic.Tiles.Wall;
import org.junit.*;

import java.awt.*;
import java.util.ArrayList;

public class MovementTests {
    private static Player player;
    private static Enemy enemy;
    private static Tile[][] board;
    private static ArrayList<Enemy> enemies;
    private static Level level;
    private static Action action;
    private static MessageDeliver messageDeliver;
    private static UnitDeathCallback deathCallback;

    @BeforeClass
    public static void initFields() {
        player = new Warrior('@', "Jon Snow", 30000, 30000, 40000, 3);
        enemy = new Monster('s', "Lannister Solider", 0, 1, 1, 3, 25);
        board = new Tile[2][3];
        enemies = new ArrayList<>();
        messageDeliver = s -> {};
        deathCallback = new UnitDeathCallback() {
            @Override
            public void onDeath(Player p) {
                player.setDISPLAY('X');
                System.out.println("GAME OVER");
            }

            @Override
            public void onDeath(Enemy enemy) {
                level.RemoveEnemy(enemy,new Empty('.',enemy.getPosition()));
            }
        };
    }
    @Before
    public void initTest(){
        /*
        [#][s][.]
        [.][@][#]
         */

        board[0][0] = new Wall('#', new Point(0,0));
        board[0][1] = enemy; enemy.setPosition(new Point(0,1));
        board[0][2] = new Empty('.',new Point(0,2));
        board[1][0] = new Empty('.',new Point(1,0));
        board[1][1] = player; player.setPosition(new Point(1,1));
        board[1][2] = new Wall('#', new Point(1,2));;

        enemies.add(enemy);

        player.setDeathCallback(deathCallback); // set the player deathcallback
        player.setMessageDeliver(messageDeliver); // set the player message deliver
        enemy.setDeathCallback(deathCallback); // set the player deathcallback
        enemy.setMessageDeliver(messageDeliver); // set the player message deliver
        level = new Level(board,player,enemies);
    }

    @Test
    public void MoveLeft_EnemyOnWall_BlockMovement(){
        //arrange
        Point afterPosition = enemy.getPosition();
        action = new MoveLeft(enemy);

        //act
        action.Act(level);

        //assert
        Assert.assertEquals("expected position: " + afterPosition + " ,actual position: " + enemy.getPosition(), afterPosition, enemy.getPosition());
    }

    @Test
    public void MoveRight_EnemyOnEmpty_AllowMovement(){
        //arrange
        Point afterPosition = board[0][2].getPosition();
        action = new MoveRight(enemy);

        //act
        action.Act(level);

        //assert
        Assert.assertEquals("expected position: " + afterPosition + " ,actual position: " + enemy.getPosition(), afterPosition, enemy.getPosition());
    }

    @Test
    public void MoveLeft_PlayerOnEmpty_AllowMovement(){
        //arrange
        Point afterPosition = board[1][0].getPosition();
        action = new MoveLeft(player);

        //act
        action.Act(level);

        //assert
        Assert.assertEquals("expected position: " + afterPosition + " ,actual position: " + player.getPosition(), afterPosition, player.getPosition());
    }

    @Test
    public void MoveRight_PlayerOnWall_BlockMovement(){
        //arrange
        Point afterPosition = player.getPosition();
        action = new MoveRight(player);

        //act
        action.Act(level);

        //assert
        Assert.assertEquals("expected position: " + afterPosition + " ,actual position: " + player.getPosition(), afterPosition, player.getPosition());
    }

    @Test
    public void MoveDown_EnemyOnPlayer_BlockMovement(){
        //arrange
        Point afterPosition = enemy.getPosition();
        action = new MoveDown(enemy);

        //act
        action.Act(level);

        //assert
        Assert.assertEquals("expected position: " + afterPosition + " ,actual position: " + enemy.getPosition(), afterPosition, enemy.getPosition());
    }

    @Test
    public void MoveUp_PlayerOnEnemy_AllowMovement(){ // enemy 0 health will die sure.
        //arrange
        Point afterPosition = board[0][1].getPosition();
        action = new MoveUp(player);

        //act
        action.Act(level);

        //assert
        Assert.assertEquals("expected position: " + afterPosition + " ,actual position: " + player.getPosition(), afterPosition, player.getPosition());
    }
}
