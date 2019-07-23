package snake;

import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;
    protected LinkedList<Cell> cauda;

    protected boolean isAlive;
    protected int foods;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
        cauda = new LinkedList<>();
        isAlive = true;
        foods = 0;
    }

    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);
    }

    protected Perception buildPerception(Environment environment) {
        // TODO
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell),
                environment.getFood().getCell());

    }

    protected void execute(Action action, Environment environment)
    {
        // TODO

        Cell nextCell = null;

        if (action == Action.NORTH) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST) {
            nextCell = environment.getEastCell(cell);
        }
        if(nextCell == null|| nextCell.hasCauda() || nextCell.hasAgent()){
            isAlive = false;
            return;
        }

        if(!nextCell.hastFood()){
            if(!cauda.isEmpty()) {
                cauda.getLast().removeCauda();
                cauda.removeLast();
            }
        }

        if(nextCell.hastFood()){
            foods++;
            // System.out.println("Apanhou comida, nº comida: " + foods);
            nextCell.setFood(null);
            environment.placeFood();
        }

        cauda.addFirst(cell);
        cell.setCauda(true);
        setCell(nextCell);

        }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if(this.cell != null){
            this.cell.setAgent(null);
        }

        this.cell = newCell;
        if(newCell != null){
            newCell.setAgent(this);
        }
    }    
    
    public Color getColor() {
        return color;
    }

    public int getFoods() {
        return foods;
    }

    public void clearAgent(){
        for(Cell cell : cauda){
            cell.setCauda(false);
        }
        cauda.clear();
        this.cell = null;
        foods = 0;
        isAlive = false;

    }
}
