package base;

import config.Configuration;

import java.util.ArrayList;

public class Board {

    private ArrayList<Region> regions;

    public int[][] getBoardRegions() {
        return boardRegions;
    }

    private int[][] boardRegions;

    public Board(ArrayList<Boolean> genesForChild) {
        regions = new ArrayList<>(Configuration.NUMBER_OF_REGIONS);
        for(int i = 0; i< Configuration.NUMBER_OF_REGIONS; i++){
            regions.add(new Region(i, Configuration.BOARD_REGIONS));
            if(genesForChild.get(i))
                regions.get(0).markRegion();
        }
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    private void initBoard(){
        boardRegions = Configuration.BOARD_REGIONS;
        regions = new ArrayList<>(Configuration.NUMBER_OF_REGIONS);
        for(int i = 0; i< Configuration.NUMBER_OF_REGIONS; i++){
            regions.add(new Region(i, Configuration.BOARD_REGIONS));
        }
    }

    public Board(){
        initBoard();
    }

    public Board(Board board){
        initBoard();
        for(int i = 0; i<28; i++){
            if(board.getRegions().get(i).isMarked())
                regions.get(i).markRegion();
        }
    }

    public boolean isSolved(){
        return evaluateFitness() == 0;
    }

    public boolean isValid(){
        for(int i = 0; i < 9; i++) {
            if( evaluateColValues()[i] > Configuration.COL_SOLUTION[i] ||
                evaluateRowValues()[i] > Configuration.ROW_SOLUTION[i]) {
                return false;
            }
        }
        return true;
    }

    public int evaluateFitness(){
        int fitness = 0;
        for(int i = 0; i < 9; i++) {
            fitness += Math.abs(evaluateColValues()[i] - Configuration.COL_SOLUTION[i]);
            fitness += Math.abs(evaluateRowValues()[i] - Configuration.ROW_SOLUTION[i]);
        }
        return fitness;
    }

    public int[] evaluateRowValues(){
        int[] rowValues = new int[9];
        for (Region r: regions) {
            for (Cell c: r.getCells()) {
                if(c.isMarked())
                    countMarkedCells(c.getRows(), rowValues);
            }
        }
        return rowValues;
    }

    public int[] evaluateColValues(){
        int[] colValues = new int[9];
        for (Region r: regions) {
            for (Cell c: r.getCells()) {
                if(c.isMarked())
                    countMarkedCells(c.getCols(), colValues);
            }
        }
        return colValues;
    }

    private void countMarkedCells(int index, int[] values){
        switch (index){
            case 0: values[0] += 1; break;
            case 1: values[1] += 1; break;
            case 2: values[2] += 1; break;
            case 3: values[3] += 1; break;
            case 4: values[4] += 1; break;
            case 5: values[5] += 1; break;
            case 6: values[6] += 1; break;
            case 7: values[7] += 1; break;
            case 8: values[8] += 1; break;
        }
    }

    public ArrayList<Boolean> getGenes() {
        ArrayList<Boolean> genes = new ArrayList<>(Configuration.NUMBER_OF_REGIONS);
        for (Region region : regions) {
            if (region.isMarked())
                genes.add(true);
            else
                genes.add(false);
        }
        return genes;
    }
}
