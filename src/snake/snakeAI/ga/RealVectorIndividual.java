package snake.snakeAI.ga;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{
    // TODO

    protected double[] genome;
    
    public RealVectorIndividual(P problem, int size, double probls) {//FEITO
        super(problem);
        // TODO
        genome = new double[size];
        for(int i = 0; i < genome.length; i++ ){
            genome[i] = GeneticAlgorithm.random.nextDouble()*2-1;
        }
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {//FEITO
        super(original);
        // TODO
        this.genome = original.genome.clone();
    }
    
    @Override
    public int getNumGenes() {//FEITO
        // TODO
        return genome.length;
    }
    
    public double getGene(int index) {//FEITO
        // TODO
        return genome[index];
    }
    
    public void setGene(int index, double newValue) {//FEITO
        // TODO
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {//FEITO
        // TODO
        double aux = genome[index];
        genome[index] = other.genome[index];
        other.genome[index] = aux;
    }
}
