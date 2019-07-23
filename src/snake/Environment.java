package snake;

import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.Color;
import java.util.*;

public class Environment {

    public Random random;
    public Cell[][] grid;
    private List<SnakeAgent> agents;
    private Food food;
    private final int maxIterations;
    private SnakeAIAgent snakeAI, snakeAI2;
    private int foods;
    private int movs;

    private int tipo;

    public Environment(int size, int maxIterations, int tipo) {

        this.tipo = tipo;
        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        this.agents = new ArrayList<>();
        this.random = new Random();

    }

    public void initialize(int seed) {
        random.setSeed(seed);
        //this.end = false;
        for (SnakeAgent snakeAgent : agents) {
            if (snakeAgent.getCell() != null) {
                snakeAgent.getCell().setAgent(null);
            }
            snakeAgent.clearAgent();
        }

        agents.clear();

        placeAgents();

        if (food != null) {
            food.getCell().setFood(null);
        }
        placeFood();
    }

    // TODO MODIFY TO PLACE ADHOC OR AI SNAKE AGENTS
    private void placeAgents() {

        switch (tipo) {
            case 0:
                SnakeRandomAgent snakeRandomAgent =
                        new SnakeRandomAgent(grid[random.nextInt(grid.length)][random.nextInt(grid.length)], Color.GREEN);

                agents.add(snakeRandomAgent);
                break;
            case 1:
                SnakeAdhocAgent snakeAdhocAgent =
                        new SnakeAdhocAgent(grid[random.nextInt(grid.length)][random.nextInt(grid.length)], Color.BLACK);

                agents.add(snakeAdhocAgent);
                break;

            case 2:
                snakeAI = new SnakeAIAgent(new Cell(random.nextInt(grid.length),random.nextInt(grid.length)),
                        9,8,4);

                agents.add(snakeAI);
                break;


            case 3: // 2 cobras homogenas

                do {
                    snakeAI = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),
                            9, 8, 4);

                    snakeAI2 = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),
                            9, 8, 4);
                }
                while (snakeAI == snakeAI2);

                agents.add(snakeAI);
                agents.add(snakeAI2);
                break;

                /*
            case 4: // 2 cobras heterogeneas

                do {
                    snakeAI = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),
                            9, 8, 4);

                    snakeAI2 = new SnakeAIAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)),
                            9, 8, 4);
                }
                while (snakeAI == snakeAI2);

                agents.add(snakeAI);
                agents.add(snakeAI2);
                break;
                */

        }



    }

    public void placeFood() {

        //para teste

        Cell cell = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        while (cell.hasAgent() || cell.hasCauda()) {
            cell = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        }
        food = new Food(cell);

    }

    public void simulate() {
        // TODO
        foods=movs=0;
        for (int i = 0; i < maxIterations; i++) {
            movs++;
            for (SnakeAgent snakeAgent : agents) {
                snakeAgent.act(this);
                if(!snakeAgent.isAlive){
                    for(SnakeAgent agent:agents){
                        foods+=agent.getFoods();

                    }
                    System.out.println("Estatisticas Comida: " + foods);
                    System.out.println("Estatisticas Movimentos: " + movs);
                    return ;
                }
                fireUpdatedEnvironment();
            }
        }
        fireUpdatedEnvironment();
    }

    public int[] simulateWithoutDrawing() {
        // TODO
        foods=movs=0;
        int[] toReturn = new int[2];
        for (int i = 0; i < maxIterations; i++) {
            movs++;
            for (SnakeAgent snakeAgent : agents) {
                snakeAgent.act(this);
                if( !snakeAgent.isAlive || i== maxIterations-1){
                    for(SnakeAgent agent:agents){
                        foods+=agent.getFoods();
                    }
                    toReturn[0] = foods;
                    toReturn[1] = movs;
                    return  toReturn;
                }
            }
        }
        return toReturn;
    }

    public int getSize() {
        return grid.length;
    }

    public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    public Food getFood() {
        return food;
    }

    public SnakeAIAgent getSnakeAI() {
        return (SnakeAIAgent)agents.get(0);
    }

    public SnakeAIAgent getSnakeAI2() {
        return (SnakeAIAgent)agents.get(1);
    }


    public int getFoods() {
        return foods;
    }

    public int getMovs() {
        return movs;
    }

    public int getTipo() {
        return tipo;
    }

    public int getAgentsSize(){
        return agents.size();
    }
}
