package snake.snakeAI.nn;

import snake.*;

import java.awt.Color;
import java.util.Random;

public class SnakeAIAgent extends SnakeAgent {
   
    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;

    // Resolucao
    final private double[] outputLayerOutput;    // Output layer activation values.

    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private int[] output;

    public SnakeAIAgent(
            Cell cell,
            int inputLayerSize,
            int hiddenLayerSize,
            int outputLayerSize) {
        super(cell, Color.BLUE);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new int[outputLayerSize];

        // Resolucao
        outputLayerOutput = new double[outputLayerSize];
    }


    /**
     * Initializes the network's weights
     * 
     * @param weights vector of weights comming from the individual.
     */

    public void setWeights(double[] weights) { //TA FEITO
        // TODO
        int incrementa = 0;

        // percorrer pesos w1
        for (int i = 0; i < inputLayerSize; i++) {
            for (int j = 0; j < hiddenLayerSize; j++) {
                w1[i][j] = weights[incrementa++];
            }
        }

        // percorrer pesos w2
        for (int i = 0; i < hiddenLayerSize + 1; i++) {
            for (int j = 0; j < outputLayerSize; j++) {
                w2[i][j] = weights[incrementa++];
            }
        }
    }
    
    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    public void forwardPropagation() {
        // TODO
        double somaPesada;

        for (int i = 0; i < hiddenLayerSize; i++) {         //percorre os neruronios da camada escondida
            somaPesada = 0;
            for (int j = 0; j < inputLayerSize; j++) {      //percorre os inputs
                somaPesada += (inputs[j] * w1[j][i]);
            }
            //sigmoide function
            hiddenLayerOutput[i] = 1 / (1 + Math.exp(-somaPesada));
        }

        for (int i = 0; i < outputLayerSize; i++) {             //percorre os neruronios da camada de saida
            somaPesada = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {     //percorre os outputs da hidden layer
                somaPesada += hiddenLayerOutput[j] * w2[j][i];
            }
            outputLayerOutput[i] = somaPesada;
        }
    }


    @Override
    public Action decide(Perception perception) {
        // TODO

        for(int i = 0; i < 8; i++){
            inputs[i]=0;
        }

        Cell n = perception.getN();
        Cell e = perception.getE();
        Cell s = perception.getS();
        Cell w = perception.getW();

        Cell food = perception.getFood();

        if(food.getLine() < cell.getLine()){
            inputs[0]=1;
        }

        if(food.getLine() > cell.getLine()){
            inputs[1]=1;
        }
        if(food.getColumn() < cell.getColumn()){
            inputs[2]=1;
        }
        if(food.getColumn() > cell.getColumn()){
            inputs[3]=1;
        }
        if(n == null || n.hasAgent()||n.hasCauda()){
            inputs[4]=1;
        }
        if(s == null || s.hasAgent()||s.hasCauda()){
            inputs[5]=1;
        }
        if(w == null || w.hasAgent()||w.hasCauda()){
            inputs[6]=1;
        }
        if(e == null || e.hasAgent()||e.hasCauda()){
            inputs[7]=1;
        }

        forwardPropagation();

        int max = 0;
        for (int i = 1; i < outputLayerSize; i++) {
            if(outputLayerOutput[i]> outputLayerOutput[max]){
                max = i;
            }
        }

        switch(max) {
            case 0:
                return Action.NORTH;
            case 1:
                return Action.SOUTH;
            case 2:
                return Action.EAST;
            case 3:
                return Action.WEST;
        }
        return null;
    }

    public double getSomaOutputlayer(){
        double soma =0;
        for(int i=0; i<outputLayerSize; i++){
            soma += outputLayerOutput[i];
        }
        return soma;

    }
}
