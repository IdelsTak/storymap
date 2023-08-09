package com.github.idelstak.storymap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class CsvToNodes implements Function<File, List<Node>>{

    @Override
    public List<Node> apply(File csvFile) {
        List<Node> nodes = new ArrayList<>();
        Function<String, Node> stringToNode = new StringToNode();
        
        try ( Scanner scanner = new Scanner(csvFile)) {
            scanner.useDelimiter("\\,");

            while (scanner.hasNext()) {
                Node node = stringToNode.apply(scanner.nextLine());
                
                nodes.add(node);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("CSV file was not found");
        }
        
        return nodes;
    }

}
