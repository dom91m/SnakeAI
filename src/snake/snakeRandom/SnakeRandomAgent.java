package snake.snakeRandom;

import snake.*;

import java.awt.*;
import java.util.Random;

public class SnakeRandomAgent extends SnakeAgent {
    // TODO
    private Random random;

    public SnakeRandomAgent(Cell cell, Color color) {
        super(cell, color);
        random = new Random();
    }

    @Override
    protected Action decide(Perception perception) {
        // TODO
        Cell n = perception.getN();
        Cell e = perception.getE();
        Cell s = perception.getS();
        Cell w = perception.getW();

        //Se tiver comida à volta

        if(n != null && n.hastFood() && !n.hasAgent()){
            return Action.NORTH;
        }

        if(e != null && e.hastFood() && !e.hasAgent()){
            return Action.EAST;
        }

        if(s != null && s.hastFood() && !s.hasAgent()){
            return Action.SOUTH;
        }

        if(w != null && w.hastFood() && !w.hasAgent()){
            return Action.WEST;
        }

        //Senão tiver comida à volta

        int move = random.nextInt(4);
        Action action = null;

        while(action == null) {
            if (move == 0){
                if(n != null && !n.hasAgent() && !n.hasCauda()) {
                    action = Action.NORTH;
                }
                move = random.nextInt(4);
            }
            if (move == 1){
                if(e != null && !e.hasAgent() && !e.hasCauda()) {
                    action = Action.EAST;
                }
                move = random.nextInt(4);
            }
            if (move == 2){
                if(s != null && !s.hasAgent() && !s.hasCauda()) {
                    action = Action.SOUTH;
                }
                move = random.nextInt(4);
            }
            if (move == 3){
                if(w != null && !w.hasAgent() && !w.hasCauda()) {
                    action = Action.WEST;
                }
                move = random.nextInt(4);
            }
        }//while
        return action;
    }
}
