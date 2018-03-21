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

    private GuiController guiController;
    private Board board, heuboard;
    private int numberOfBTRegions;

    private ArrayList<Integer> backtrackRegions;

    public BacktrackHandler(GuiController guiController){
        setDaemon(true);
        this.guiController = guiController;
        board = new Board();
        heuboard = new Board();
        backtrackRegions = new ArrayList<>();
        for(int i = 0; i<28; i++)
            backtrackRegions.add(i);
    }


    @Override
    public void run() {
        heuristic();
        if(backtrack(0))
            guiController.stop();
        else
            System.out.println("ERROR");
    }

    private void heuristic(){
        Heuristic h = new Heuristic();
        for (Integer i : h.doHeuristic()) {
            heuboard.getRegions().get(i).markRegion();
            backtrackRegions.remove(i);
        }
        board = heuboard;
        numberOfBTRegions = backtrackRegions.size();
        System.out.println(backtrackRegions);
        System.out.println(backtrackRegions.size());
    }

    private boolean backtrack(int index){
        Platform.runLater(() -> guiController.updateBoard(board));
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(BacktrackHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        int btIndex;
        if(board.isSolved())
            return true;
        else {
            if(index < numberOfBTRegions)
                btIndex = backtrackRegions.get(index);
            else
                return false;
            if(isColoringValid(btIndex, board)){
                board.getRegions().get(btIndex).markRegion();
                if(backtrack(index + 1))
                    return true;
                else
                    board.getRegions().get(btIndex).unMarkRegion();
            }
            if(backtrack(index+1))
                return true;
        }
        return false;
    }

    private boolean isColoringValid(int index, Board btBoard){
        Board validCheckBoard = new Board(btBoard);
        validCheckBoard.getRegions().get(index).markRegion();
        return validCheckBoard.isValid();
    }
}
