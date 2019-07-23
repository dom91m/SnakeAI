package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

//PLEASE, MODIFY THE CLASS NAME
public class MutationMUTATION_NAME <I extends RealVectorIndividual> extends Mutation<I> {

   
    public MutationMUTATION_NAME(double probability /*TODO?*/) {
        super(probability);
        // TODO
    }

    @Override
    public void run(I ind) {
        // TODO
        for (int i = 0; i <ind.getNumGenes() ; i++) {
            if(GeneticAlgorithm.random.nextDouble() < probability){
                ind.setGene(i,GeneticAlgorithm.random.nextDouble()*2-1);
            }
        }

        /*for(SnakeAIAgent : agents){
            if(!snake instanceof SnakeAIAgent){
                throw
            }
            (SnakeAIAgent) agent.setWeights(genome);

            environment.simulate();
            moves = environment.getIterations();

        }*/

    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation (" + probability /* + TODO?*/;
    }
}