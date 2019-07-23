package gui;

import snake.Cell;
import snake.Environment;
import snake.snakeAI.ga.geneticOperators.*;
import snake.snakeAI.ga.selectionMethods.RouletteWheel;
import snake.snakeAI.ga.selectionMethods.SelectionMethod;
import snake.snakeAI.ga.selectionMethods.Tournament;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import snake.snakeAI.SnakeIndividual;
import snake.snakeAI.SnakeProblem;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import static snake.snakeAI.ga.GeneticAlgorithm.random;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;

    // TODO MODIFY TO CHANGE THE DEFAULT PARAMETER VALUES
    public static final String SEED = "1";
    public static final String POPULATION_SIZE = "200";
    public static final String GENERATIONS = "1000";
    public static final String TOURNAMENT_SIZE = "10";
    public static final String PROB_RECOMBINATION = "0.75";
    public static final String PROB_MUTATION = "0.01";

    private MainFrame mainframe;

    JTextField textFieldSeed = new JTextField(SEED, TEXT_FIELD_LENGHT);
    JTextField textFieldN = new JTextField(POPULATION_SIZE, TEXT_FIELD_LENGHT);
    JTextField textFieldGenerations = new JTextField(GENERATIONS, TEXT_FIELD_LENGHT);
    String[] selectionMethods = {"Tournament", "Roulette"};
    JComboBox comboBoxSelectionMethods = new JComboBox(selectionMethods);
    JTextField textFieldTournamentSize = new JTextField(TOURNAMENT_SIZE, TEXT_FIELD_LENGHT);
    String[] recombinationMethods = {"One cut", "Two cuts", "Uniform"};
    JComboBox comboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField textFieldProbRecombination = new JTextField(PROB_RECOMBINATION, TEXT_FIELD_LENGHT);
    JTextField textFieldProbMutation = new JTextField(PROB_MUTATION, TEXT_FIELD_LENGHT);

    String[] snakes = {"SnakeRandomAgent", "SnakeAdhocAgent", "SnakeAIAgent", "SnakeAI-2-homogeneas", "SnakeAI-2-heterogeneas"};
    JComboBox snakeComboBox = new JComboBox(snakes);

    //TODO - MORE PARAMETERS?

    public PanelParameters(MainFrame mainframe) {

        this.mainframe = mainframe;

        title = "Genetic algorithm parameters";

        labels.add(new JLabel("Seed: "));
        valueComponents.add(textFieldSeed);
        textFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Population size: "));
        valueComponents.add(textFieldN);
        textFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(textFieldGenerations);
        textFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(comboBoxSelectionMethods);
        comboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_KeyAdapter(this));

        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(textFieldTournamentSize);
        textFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(comboBoxRecombinationMethods);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(textFieldProbRecombination);

        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(textFieldProbMutation);

        labels.add(new JLabel("Choose a snake: "));
        valueComponents.add(snakeComboBox);
        snakeComboBox.addActionListener(new JComboBoxSnakeSelection_ActionAdapter(this));

        //TODO - MORE PARAMETERS?
        configure();
    }

    public void actionPerformedSelectionMethods(ActionEvent e) {
        textFieldTournamentSize.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 0);
    }

    public SelectionMethod<SnakeIndividual, SnakeProblem> getSelectionMethod() {
        switch (comboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament<>(
                        Integer.parseInt(textFieldN.getText()),
                        Integer.parseInt(textFieldTournamentSize.getText()));
            case 1:
                return new RouletteWheel<>(
                        Integer.parseInt(textFieldN.getText()));

        }
        return null;
    }

    public Recombination<SnakeIndividual> getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(textFieldProbRecombination.getText());

        switch (comboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationOneCut<>(recombinationProb);
            case 1:
                return new RecombinationTwoCuts<>(recombinationProb);
            case 2:
                return new RecombinationUniform<>(recombinationProb);
        }
        return null;
    }

    public Mutation<SnakeIndividual> getMutationMethod() {
        double mutationProbability = Double.parseDouble(textFieldProbMutation.getText());
        //TODO
        return new MutationMUTATION_NAME<>(mutationProbability/*TODO?*/);
    }

    public void actionPerformedSnakeSelection(ActionEvent e) {
        mainframe.setTipo(snakeComboBox.getSelectedIndex());
    }


}

class JComboBoxSelectionMethods_KeyAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionMethods_KeyAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}

class JComboBoxSnakeSelection_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSnakeSelection_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSnakeSelection(e);
    }
}


class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }



}
