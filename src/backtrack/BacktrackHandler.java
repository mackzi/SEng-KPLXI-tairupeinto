package backtrack;

import base.Board;
import config.Configuration;
import gui.GuiController;
import gui.Main;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BacktrackHandler extends Thread{

    GuiController guiController;
    Thread backtrackThread;
    Board board, heuboard;
    int count;
    int btCounter;

    ArrayList<Integer> backtrackRegions;

    public BacktrackHandler(GuiController guiController){
        setDaemon(true);
        this.guiController = guiController;
        board = new Board();
        heuboard = new Board();
        count = 0;
        btCounter = 0;
        backtrackRegions = new ArrayList<>();
        for(int i = 0; i<28; i++)
            backtrackRegions.add(i);
    }


    @Override
    public void run() {

        execute();

        while (!this.isInterrupted()){
            Platform.runLater(() -> guiController.updateBoard(board));
            try {
                sleep(TimeUnit.SECONDS.toMillis(3));
            } catch (InterruptedException ex) {
                Logger.getLogger(BacktrackHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void execute(){
        heuristic();
        backtrack(0);
            System.out.println("backtracking fertig " + btCounter);
    }

    private void testGui(){
        for(int i = 0; i< 100; i++){
            if(Configuration.instance.random.nextBoolean())
                board.getRegions().get(Configuration.instance.random.nextInt(0, 27)).markRegion();
            else
                board.getRegions().get(Configuration.instance.random.nextInt(0, 27)).unMarkRegion();
        }
    }

    private void heuristic(){
        Heuristic h = new Heuristic();

        for (Integer i : h.doHeuristic()) {
            heuboard.getRegions().get(i).markRegion();
            backtrackRegions.remove(i);
        }
        board = heuboard;
        System.out.println(backtrackRegions);
    }

    private boolean backtrack(int index){
        count++;
        System.out.println(index);

        if(index == 28 && !board.isSolved())
            return false;

        if(board.isSolved()){
            return true;
        }
        else {
            if(isColoringValid(index, board)){
                board.getRegions().get(index).markRegion();
                if(backtrack(index + 1))
                    return true;
                else {
                    board.getRegions().get(index).unMarkRegion();
                    btCounter += 1;
                }
            }
            backtrack(index+1);
        }
        return false;
    }

    private boolean isColoringValid(int index, Board btBoard){
        Board validCheckBoard = new Board(btBoard);
        validCheckBoard.getRegions().get(index).markRegion();
        return validCheckBoard.isValid();
    }
}
