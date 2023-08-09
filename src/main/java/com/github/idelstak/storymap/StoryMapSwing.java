package com.github.idelstak.storymap;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class StoryMapSwing implements StoryMap {

    private final List<Node> nodes;

    public StoryMapSwing(Collection<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    @Override
    public void show() {
        EventQueue.invokeLater(() -> new Gui(nodes).setVisible(true));
    }

    private class Gui extends JFrame {

        private final List<NodePanel> panels = new ArrayList<>();
        private final List<Node> ns;

        private Gui(Collection<Node> ns) {
            super("StoryMap - Goblin Attack!");
            
            this.ns = new ArrayList<>(ns);

            initComponents();
        }

        private void initComponents() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            setMaximumSize(new Dimension(350, 500));
            setMinimumSize(new Dimension(350, 500));
            setPreferredSize(new Dimension(350, 500));
            setSize(new Dimension(350, 500));

            getContentPane().setLayout(new CardLayout());

            panels.addAll(ns.stream().map(NodePanel::new).collect(Collectors.toList()));

            panels.forEach(panel -> getContentPane().add(panel, panel.getName()));

            pack();
            
            setLocationRelativeTo(null);
        }

        private void switchTo(String paneName) {
            ((CardLayout) getContentPane().getLayout()).show(this.getContentPane(), paneName);
        }

        private class NodePanel extends JPanel {

            private final JPanel decisionsPanel;
            private final JLabel descriptionLabel;
            private final JPanel descriptionPanel;
            private final JButton yesButton;
            private final JButton noButton;
            private Timer timer;
            private final JProgressBar progressBar;
            private final Node node;

            private NodePanel(Node node) {
                super(new BorderLayout());

                this.node = node;

                descriptionPanel = new JPanel();
                descriptionLabel = new JLabel();
                decisionsPanel = new JPanel();
                yesButton = new JButton();
                noButton = new JButton();
                progressBar = new JProgressBar();

                yesButton.addActionListener(evt -> {
                    switchTo(Integer.toString(node.getYesId()));
                });
                noButton.addActionListener(evt -> {
                    switchTo(Integer.toString(node.getNoId()));
                });

                timer = new Timer(30, (evt -> {
                    int val = progressBar.getValue();
                    if (val >= 100) {
                        timer.stop();
                        if (node.getDescription().trim().equalsIgnoreCase("good bye")) {
                            System.exit(0);
                        } else {
                            switchTo(Integer.toString(node.getYesId()));
                        }
                        return;
                    }
                    progressBar.setValue(++val);
                }));

                initComponents();

                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentShown(ComponentEvent e) {
                        if (progressBar.isShowing()) {
                            timer.start();
                        }
                    }
                });
            }

            @Override
            public String getName() {
                return Integer.toString(node.getId());
            }

            private void initComponents() {
                descriptionPanel.setBackground(Color.white);
                descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 96, 12));
                descriptionPanel.setLayout(new BorderLayout());

                descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(descriptionLabel.getFont().getSize() + 3f));
                descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                descriptionLabel.setText("<html><body style=\"text-align: center;\">%s</html>".formatted(node.getDescription()));
                descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);

                add(descriptionPanel, BorderLayout.CENTER);

                decisionsPanel.setBackground(new Color(52, 200, 241));
                decisionsPanel.setPreferredSize(new Dimension(250, 70));
                decisionsPanel.setLayout(new GridLayout());

                String question = node.getQuestion();

                if (!Objects.equals(question, "-") && question.contains(" or ")) {
                    String[] acts = question.split(" or ");
                    acts[1] = acts[1].replace("?", "");

                    yesButton.setFont(yesButton.getFont().deriveFont(yesButton.getFont().getStyle() | Font.BOLD, yesButton.getFont().getSize() - 2));
                    yesButton.setText(acts[0].toUpperCase());
                    yesButton.setToolTipText(acts[0].toLowerCase());
                    noButton.setFont(noButton.getFont().deriveFont(noButton.getFont().getStyle() | Font.BOLD, noButton.getFont().getSize() - 2));
                    noButton.setText(acts[1].toUpperCase());
                    noButton.setToolTipText(acts[1].toLowerCase());

                    decisionsPanel.add(yesButton);
                    decisionsPanel.add(noButton);
                } else {
                    decisionsPanel.add(progressBar);
                }

                add(decisionsPanel, BorderLayout.SOUTH);
            }

        }

    }

}
