package com.github.idelstak.storymap;

import java.util.function.Function;

public class StringToNode implements Function<String, Node> {

    @Override
    public Node apply(String line) {
        String[] fields = line.split(",");
        
        int id = Integer.parseInt(fields[0]);
        int yesId = Integer.parseInt(fields[1]);
        int noId = Integer.parseInt(fields[2]);
        String description = fields[3];
        String question = fields[4];

        return new Node(id, yesId, noId, description, question);
    }

}
