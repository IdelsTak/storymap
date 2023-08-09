package com.github.idelstak.storymap;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

public class Start {

    public static void main(String[] args) throws FileNotFoundException {
        String pathName = System.getProperty("user.dir") + File.separator + "game-story-map.csv";
        File csvFile = Paths.get(pathName).toFile();
        List<Node> nodes = new CsvToNodes().apply(csvFile);
        
         // StoryMap storyMap = new StoryMapConsole(nodes);
        StoryMap storyMap = new StoryMapSwing(nodes);

        storyMap.show();
    }
}
