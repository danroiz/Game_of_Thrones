package Logic;

import Logic.Actions.*;
import Logic.Enemies.*;
import Logic.Players.Player;
import Logic.Tiles.Tile;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class Level {
    private Tile[][] board;
    private Player player;
    private ArrayList<Enemy> enemies;


    private Map<Character, Supplier<Action>> PlayerActionsMap = new HashMap<>();
    private void InitActionsMap() {
        PlayerActionsMap.put('w',()->new MoveUp(player));
        PlayerActionsMap.put('s',()->new MoveDown(player));
        PlayerActionsMap.put('d',()->new MoveRight(player));
        PlayerActionsMap.put('a',()->new MoveLeft(player));
        PlayerActionsMap.put('e',()->new SpecialAbility(player));
        PlayerActionsMap.put('q', Nothing::new);
    }

    public Level(Tile[][] board, Player player, ArrayList<Enemy> enemies){
        this.board = board;
        this.player = player;
        this.enemies = enemies;
        InitActionsMap();
    }



    public Player getPlayer(){return player;}
    public ArrayList<Enemy> getEnemies(){return enemies;}
    public Tile getTile(Point position){
        return board[(int) position.getX()][(int) position.getY()];
    }


    private void EnemiesTurn()  {
        for (Enemy enemy : enemies){
            Action action = enemy.EnemyTurn(player);
            action.Act(this);
        }
    }

    public void Round()  {
        GetPlayerAction().Act(this);
        player.GameTick();
        EnemiesTurn();
    }

    private Action GetPlayerAction() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        while (!ValidCharInput(input))
            input = scanner.next();
        return PlayerActionsMap.get(input.charAt(0)).get();
    }

    private boolean ValidCharInput(String input) {
        if (input != null && input.length() == 1) {
            char inputChar = input.charAt(0);
            return PlayerActionsMap.containsKey(inputChar);
        }
        else
            return false;
    }


    public void Move(Tile tile, Tile destination){

        // update the board itself
        board[destination.getX()][destination.getY()] = tile;
        board[tile.getX()][tile.getY()] = destination;

        // update the tile's new positions
        Point tempPosition = tile.getPosition();
        tile.setPosition(destination.getPosition());
        destination.setPosition(tempPosition);

    }

    public boolean RemoveEnemy(Enemy enemy, Tile replace){
        enemies.remove(enemy);
        board[enemy.getX()][enemy.getY()] = replace;
        return enemies.size() == 0; // return true if all enemies died
    }

    public String toString(){
        StringBuilder boardString = new StringBuilder();
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                boardString.append(tile);
            }
            boardString.append("\n");
        }

        return boardString.toString();
    }

}
