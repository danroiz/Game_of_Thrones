package GUI;

import Logic.Level;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameLoader {

    public static void main(String[] args){


        File levelsDir = new File(args[0]);
        String[] levelsDirFilesNames = levelsDir.list();
        assert levelsDirFilesNames != null;
        List<String> inDirFilesPath = Arrays.stream(levelsDirFilesNames).map(s -> args[0] + "/" + s).collect(Collectors.toList());
        List<List<String>> LevelFilesContent = new ArrayList<>();
        for (String filePath : inDirFilesPath){
            try {
                LevelFilesContent.add(Files.readAllLines(Paths.get(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        LevelParser levelParser = new LevelParser();
        List<Level> levels = levelParser.getAllLevels(LevelFilesContent);
        List<Point> startPositions = levelParser.getStartPositions();
        GameManager gameManager = new GameManager(levels, startPositions);
        gameManager.StartGame();

    }


}
