package GUI;

import Logic.Enemies.Boss;
import Logic.Enemies.Enemy;
import Logic.Enemies.Monster;
import Logic.Enemies.Trap;
import Logic.Level;
import Logic.Players.*;
import Logic.Tiles.Empty;
import Logic.Tiles.Tile;
import Logic.Tiles.Wall;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;

class LevelParser {
    private List<Point> startPositions = new ArrayList<>();
    private Map<Character, Supplier<Enemy>> enemiesMap = new HashMap<>();
    private ArrayList<Supplier<Player>> playersMap = new ArrayList<>();

    LevelParser(){
        InitPlayersMap();
        InitEnemiesMap();
    }

    private Player ChoosePlayer() {
        Player player = null;
        System.out.println("Select Player:");
        StringBuilder PlayerMenu = new StringBuilder();
        for (int i = 0; i < playersMap.size(); i++){
            PlayerMenu.append(i + 1).append(". ").append(playersMap.get(i).get().describe()).append("\n");
        }
        System.out.print(PlayerMenu);
        Scanner scanner = new Scanner(System.in);
        boolean notValidInput = true;
        while (notValidInput){
            try {
                int input =Integer.parseInt(scanner.nextLine());
                if (input > 0 & input <= playersMap.size()) {
                    player = playersMap.get(input-1).get();
                    System.out.println("You have selected:");
                    System.out.println(player.getName());
                    notValidInput = false;
                }
                else
                    System.out.print(PlayerMenu);
            }
            catch(Exception e){
                System.out.println("Not a number." );
                System.out.print(PlayerMenu);
            }
        }
        return player;
    }

    private void InitPlayersMap() {
        //Warriors
        playersMap.add(()->new Warrior('@',"Jon Snow",300,30,4,3));
        playersMap.add(()->new Warrior('@',"The Hound",400,20,6,5));
        //Mages
        playersMap.add(() ->new Mage('@', "Melisandre",100,5,1,300,30,15,5,6));
        playersMap.add(() ->new Mage('@',"Thoros of Myr",250,25,4,150,20,20,3,4));
        //Rogues
        playersMap.add(()->new Rogue('@',"Arya Stark",150,40,2,20));
        playersMap.add(()->new Rogue('@',"Bronn",250,35,3,50));
        //Hunters
        playersMap.add(()->new Hunter('@',"Ygritte",220,30,2,6));

    }

    private void InitEnemiesMap() {
        // Monsters
        enemiesMap.put('s',()->new Monster('s',"Lannister Solider",80,8,3,3,25));
        enemiesMap.put('q',()->new Monster('q',"Queen's Guard",400,20,15,5,100));
        enemiesMap.put('z',()->new Monster('z',"Wright",600,30,15,3,100));
        enemiesMap.put('k',()->new Monster('k',"Lannister Knight",200,14,8,4,50));
        enemiesMap.put('b',()->new Monster('b',"Bear-Wright",1000,75,30,4,250));
        enemiesMap.put('g',()->new Monster('g',"Giant-Wright",1500,100,40,5,500));
        enemiesMap.put('w',()->new Monster('w',"White Walker",2000,150,50,6,1000));
        // Traps
        enemiesMap.put('B',()->new Trap('B',"Bonus Trap",1,1,1,250,1,5));
        enemiesMap.put('Q',()->new Trap('Q',"Bonus Trap",250,50,10,100,3,7));
        enemiesMap.put('D',()->new Trap('D',"Bonus Trap",500,100,20,250,1,10));
        // Boss
        enemiesMap.put('M',()->new Boss('M',"The Mountain",1000,60,25,6,500,1));
        enemiesMap.put('C',()->new Boss('C',"Queen Cersei",100,10,10,1,1000,3));
        enemiesMap.put('K',()->new Boss('K',"Night's King",5000,300,150,8,5000,2));
    }

    List<Level> getAllLevels(List<List<String>> levelFilesContent) {
        List<Level> levels = new ArrayList<>();
        Player player = ChoosePlayer();
        for (List<String> fileContent : levelFilesContent)
            levels.add(getLevel(fileContent,player));
        return levels;
    }

    private Level getLevel(List<String> fileContent,Player chosenPlayer) {
        Tile[][] tiles = new Tile[fileContent.size()][fileContent.get(0).length()];
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < fileContent.size(); i++){
            for (int j = 0; j < fileContent.get(i).length(); j++){
                char currentChar = fileContent.get(i).charAt(j);
                    if (currentChar == '#') // wall
                        tiles[i][j] = new Wall('#', new Point(i, j));
                    else if (currentChar == '.') // empty tile
                        tiles[i][j] = new Empty('.',new Point(i,j)  );
                    else if (currentChar == '@') { // player
                        tiles[i][j] = chosenPlayer;
                        startPositions.add(new Point(i,j));
                    }
                    else { // enemy
                        try {
                            Enemy enemy = enemiesMap.get(currentChar).get(); // init the relevant enemy
                            tiles[i][j] = enemy;
                            enemy.setPosition(new Point(i,j));
                            enemies.add(enemy);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("level map has undefined chars");
                            System.exit(-1);
                        }
                    }
                }
            }

        return new Level(tiles,chosenPlayer,enemies);
    }

    List<Point> getStartPositions() {
        return startPositions;
    }
}
