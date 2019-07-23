package snake.snakeAdhoc;

import snake.*;

import java.awt.*;

public class SnakeAdhocAgent extends SnakeAgent {

    public SnakeAdhocAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        // TODO

        Cell n = perception.getN();
        Cell e = perception.getE();
        Cell s = perception.getS();
        Cell w = perception.getW();

        // ***** A cobra tenta ir pelo caminho mais rápido, em direçao a comida ***** //
        if (n != null && !n.hasAgent() && !n.hasCauda() && cell.getLine() > perception.getFood().getLine()) {
            return Action.NORTH;
        }

        if (s != null && !s.hasAgent() && !s.hasCauda() && cell.getLine() < perception.getFood().getLine()) {
            return Action.SOUTH;
        }

        if (w != null && !w.hasAgent() && !w.hasCauda() && cell.getColumn() > perception.getFood().getColumn()) {
            return Action.WEST;
        }
        if (e != null && !e.hasAgent() && !e.hasCauda() && cell.getColumn() < perception.getFood().getColumn()) {
            return Action.EAST;
        }


        // ***** Caso ela não o consiga fazer porque tem o seu corpo nas células ao lado... ***** //
        if (e != null && !e.hasAgent() && !e.hasCauda()) {
            return Action.EAST;
        }

        if (w != null && !w.hasAgent() && !w.hasCauda()) {
            return Action.WEST;
        }

        if (n != null && !n.hasAgent() && !n.hasCauda()) {
            return Action.NORTH;
        }

        if (s != null && !s.hasAgent() && !s.hasCauda()) {
            return Action.SOUTH;
        }

        return null;
    }

}
