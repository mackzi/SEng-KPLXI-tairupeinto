package eva;

import base.Board;
import config.Configuration;
import crossover.ICrossover;
import crossover.OnePointCrossover;
import crossover.UniformCrossover;
import gui.GuiController;
import javafx.application.Platform;
import mutation.DisplacementMutation;
import mutation.IMutation;
import selection.ISelection;
import selection.TournamentSelection;

import java.util.ArrayList;

public class EvaHandler implements Runnable{
    GuiController guiController;
    private Thread evaThread;
    private int generation;
    private int bestFitness;
    private int bestFitnessIndex;
    private ArrayList<Board> population;

    public EvaHandler(GuiController guiController){
        population = new ArrayList<>(Configuration.NUMBER_OF_REGIONS);
        this.guiController = guiController;
        generation = 0;
        bestFitness = 99;
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
        ISelection selection = new TournamentSelection();
        selectedBoards = selection.doSelection(population);

        //CROSSOVER
        ICrossover crossover = new OnePointCrossover();
        for(int i = 0; i< selectedBoards.size()/2; i++) {
            Board parent1 = selectedBoards.get(Configuration.instance.random.nextInt(0, selectedBoards.size() - 1));
            selectedBoards.remove(parent1);
            Board parent2 = selectedBoards.get(Configuration.instance.random.nextInt(0, selectedBoards.size() - 1));
            selectedBoards.remove(parent2);
            population.addAll(crossover.doCrossover(parent1, parent2));
        }
        //MUTATION
        IMutation mutation = new DisplacementMutation();
        population.add(mutation.doMutation(population.get(Configuration.instance.random.nextInt(0, population.size()-1))));

        if(population.size() > Configuration.instance.maxPopulationSize){
            doPest();
        }

        System.out.println("PopulationSize: " + population.size());
        generation += 1;
        if(bestFitness == 0)
            evaThread.interrupt();
    }

    private void doPest(){
        int sum = 0;
        for (Board b: population) {
            sum += b.evaluateFitness();
        }
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
