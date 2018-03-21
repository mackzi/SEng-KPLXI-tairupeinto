package eva;

import base.Board;
import config.Configuration;
import eva.crossover.*;
import eva.mutation.*;
import eva.selection.RouletteWheelSelection;
import gui.GuiController;
import javafx.application.Platform;
import eva.selection.ISelection;
import eva.selection.TournamentSelection;

import java.util.ArrayList;

public class EvaHandler implements Runnable{
    private GuiController guiController;
    private Thread evaThread;
    private ArrayList<Board> population;
    private int generation;
    private int bestFitness;
    private int bestFitnessIndex;
    private String selectionType;
    private String crossoverType;
    private String mutationType;

    public EvaHandler(GuiController guiController, String selectionType, String crossoverType, String mutationType){
        population = new ArrayList<>(Configuration.NUMBER_OF_REGIONS);
        this.guiController = guiController;
        generation = 0;
        bestFitness = 99;
        this.selectionType = selectionType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        initPopulation();
    }

    @Override
    public void run() {
        try {
            evaThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(this::execute);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            Thread.currentThread().interrupt();
            evaThread.interrupt();
        }
    }

    private void initPopulation(){
        for(int i = 0; i < Configuration.INITIAL_POPULATION_SIZE; i++){
            Board board = new Board();
            for (int j = 0; j < Configuration.NUMBER_OF_REGIONS; j++) {
                if (Configuration.instance.random.nextBoolean())
                    board.getRegions().get(j).markRegion();
            }
            population.add(board);
        }
    }

    private void execute() {
        evaluateBestFitnessIndex();
        guiController.updateBoard(population.get(bestFitnessIndex));
        guiController.showBestFitness(bestFitness);
        guiController.showCurrentGeneration(generation);

        //SELECTION
        ArrayList<Board> selectedBoards;
        ISelection selection;
        switch (selectionType){
            case "Tournament":
                selection = new TournamentSelection();
                break;
            case "RouletteWheel":
                selection = new RouletteWheelSelection();
                break;
            default:
                selection = new TournamentSelection();
                break;
        }
        selectedBoards = selection.doSelection(population);

        //CROSSOVER
        ICrossover crossover;
        switch (crossoverType){
            case "OnePoint":
                crossover = new OnePointCrossover();
                break;
            case "TwoPoint":
                crossover = new TwoPointCrossover();
                break;
            case "K-Point":
                crossover = new KPointCrossover();
                break;
            case "Uniform":
                crossover = new UniformCrossover();
                break;
            default:
                crossover = new OnePointCrossover();
                break;
        }

        for(int i = 0; i< selectedBoards.size()/2; i++) {
            Board parent1 = selectedBoards.get(Configuration.instance.random.nextInt(0, selectedBoards.size() - 1));
            selectedBoards.remove(parent1);
            Board parent2 = selectedBoards.get(Configuration.instance.random.nextInt(0, selectedBoards.size() - 1));
            selectedBoards.remove(parent2);
            population.addAll(crossover.doCrossover(parent1, parent2));
        }
        //MUTATION
        IMutation mutation;

        switch (mutationType){
            case "Displacement":
                mutation = new DisplacementMutation();
                break;
            case "Exchange":
                mutation = new ExchangeMutation();
                break;
            case "Insertion":
                mutation = new InsertionMutation();
                break;
            case "Inversion":
                mutation = new InversionMutation();
                break;
            case "Scramble":
                mutation = new ScrambleMutation();
                break;
            default:
                mutation = new DisplacementMutation();
                break;
        }

        population.add(mutation.doMutation(population.get(Configuration.instance.random.nextInt(0, population.size()-1))));

        if(population.size() > Configuration.instance.MAX_POPULATION_SIZE)
            doPest();

        //System.out.println("PopulationSize: " + population.size());
        generation += 1;
        if(bestFitness == 0)
            evaThread.interrupt();
    }

    private void doPest(){
        int sum = 0;
        for (Board b: population)
            sum += b.evaluateFitness();

        sum = sum / population.size();
        for (int i = 0; i < population.size(); i++) {
            if(population.get(i).evaluateFitness() >= sum)
                population.remove(population.get(i));
        }
    }

    private void evaluateBestFitnessIndex(){
        int localBestFitness = population.get(0).evaluateFitness();
        for (int i = 0; i < population.size(); i++) {
            if(population.get(i).evaluateFitness() < localBestFitness){
                bestFitnessIndex = i;
                bestFitness = population.get(i).evaluateFitness();
                localBestFitness = population.get(i).evaluateFitness();
            }
        }
    }
}
