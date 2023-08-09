package com.github.idelstak.storymap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StoryMapConsole implements StoryMap {

    private final List<Node> nodes;

    public StoryMapConsole(Collection<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    @Override
    public void show() {
        Node next = nodes.get(0);

        try ( Scanner scanner = new Scanner(System.in)) {
            while (next.getId() != 51) {
                int response = getResponse(next, scanner);

                if (response == -1) {
                    int yesId = next.getYesId();

                    next = nodes.stream().filter(n -> n.getId() == yesId).findFirst().orElseThrow();
                } else {
                    int yesId = next.getYesId();
                    int noId = next.getNoId();

                    next = nodes.stream().filter(n -> n.getId() == (response == 1 ? yesId : noId)).findFirst().orElseThrow();
                }
            }

            if (next.getId() == 51) {
                System.out.println("\n" + next.getDescription());
            }
        }
    }

    private int getResponse(Node node, Scanner in) {
        System.out.println("\n" + node.getDescription());

        String question = node.getQuestion();

        if (!Objects.equals(question, "-")) {
            String[] acts = question.split(" or ");

            acts[1] = acts[1].replace("?", "");

            System.out.println("%s or %s? (press 1 for %s or 2 for %s)".formatted(acts[0], acts[1], acts[0], acts[1]));

            System.out.print("> ");

            if (in.hasNext()) {
                int response;

                try {
                    response = Integer.parseInt(in.next());
                } catch (NumberFormatException ex) {
                    System.out.println("You've not selected a number");
                    return getResponse(node, in);
                }

                if (response == 1 || response == 2) {
                    return response;
                } else {
                    System.out.println("You've not selected 1 nor 2");
                    return getResponse(node, in);
                }
            } else {
                System.out.println("You've not selected a number");
                return getResponse(node, in);
            }
        } else {
            return -1;
        }
    }
}
