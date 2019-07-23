package snake.snakeAI;

import snake.Perception;
import snake.snakeAI.ga.RealVectorIndividual;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private double value;
    private double weight;
    private Perception perception;
    private double foodsEaten;
    private double movesmade;

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/, double prob1s) { //FEITO
        super(problem, size, prob1s);
        //TODO?
        this.foodsEaten = 0.0;
        this.movesmade = 0.0;

    }

    public SnakeIndividual(SnakeIndividual original) { //FEITO
        super(original);
        //TODO
        this.weight = original.weight;
        this.value = original.value;
        this.perception = original.perception;
        this.movesmade = original.getMovesmade();
        this.foodsEaten = original.getFoodsEaten();


    }

    @Override
    public double computeFitness() {
        //TODO

        // para cada simulacao (utilizar var de iterecao como seed do random)
        // ir ao genoma buscar os pesos das sinapes e coloca-los na rede neurona (setWeights)
        // mandar a SnakeAI decidir
            // colocar os inputs com os valores percepcionados
            // mandar executar o forwardpropagation
            // observar os valores dos outputs
            //decidir açao
        // manda a cobra iterar o maximo de x vezes
        // recolhe estatisticas (comidas, itreçaoes)
        // atribuir e devolver fitess (valorizar mais as comidas que as iteracoes

        int foods = 0;
        int movs = 0;
        int[] estatisticas = new int[2];
        for (int i = 0; i < problem.getNumEvironmentSimulations(); i++) {
            problem.getEnvironment().initialize(i);
            problem.getEnvironment().getSnakeAI().setWeights(genome);

            if(problem.getEnvironment().getTipo() == 3){ // se for 2 cobras homogeneas
                problem.getEnvironment().getSnakeAI2().setWeights(genome);
            }

            /*
            if(problem.getEnvironment().getTipo() == 4){ // se for 2 cobras heterogeneas
                problem.getEnvironment().getSnakeAI2().setWeights(genome);
            }
            */

            estatisticas = problem.getEnvironment().simulateWithoutDrawing();
            foods += estatisticas[0];
            movs += estatisticas[1];

        }
        this.foodsEaten = foods/problem.getNumEvironmentSimulations();
        this.movesmade = movs/problem.getNumEvironmentSimulations();

        fitness = (foods*1000 +movs)/problem.getNumEvironmentSimulations();

        return fitness;

    }

    public double[] getGenome(){ //FEITO
        //TODO
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\nComidas: ");
        sb.append(foodsEaten);
        sb.append("\nMovimentos: ");
        sb.append(movesmade);
        /*
        // ------------------------------
        sb.append("\nOutputLayer Soma cobra 1: ");
        sb.append(problem.getEnvironment().getSnakeAI().getSomaOutputlayer());
        sb.append("\nOutputLayer Soma cobra 2: ");
        sb.append(problem.getEnvironment().getSnakeAI2().getSomaOutputlayer());
        // ------------------------------
        */

        //TODO
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(SnakeIndividual i) {
        if(this.fitness > i.getFitness()){
            return 1;
        }
        if(this.fitness == i.getFitness()) {
            return 0;
        }
        return -1;
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }

    public Perception getPerception() {
        return perception;
    }

    public double getFoodsEaten() {
        return foodsEaten;
    }

    public double getMovesmade() {
        return movesmade;
    }


}
