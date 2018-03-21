package backtrack;

import base.Board;
import gui.GuiController;
import javafx.application.Platform;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BacktrackHandler extends Thread{

    private GuiController guiController;
    private Board board, heuBoard;
    private int numberOfBTRegions;
    private int btCounter;

    private ArrayList<Integer> backtrackRegions;

    public BacktrackHandler(GuiController guiController){
        setDaemon(true);
        btCounter = 0;
        this.guiController = guiController;
        board = new Board();
        heuBoard = new Board();
        numberOfBTRegions = 28;
        backtrackRegions = new ArrayList<>();
        for(int i = 0; i<28; i++)
            backtrackRegions.add(i);
    }


    @Override
    public void run() {
        heuristic();
        backtrack(0);
        Platform.runLater(() -> {guiController.updateBoard(board);
                                guiController.stop();});
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(BacktrackHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void heuristic(){
        Heuristic h = new Heuristic();
        for (Integer i : h.doHeuristic()) {
            heuBoard.getRegions().get(i).markRegion();
            backtrackRegions.remove(i);
        }
        board = heuBoard;
        numberOfBTRegions = backtrackRegions.size();
    }

    private boolean backtrack(int index){
        btCounter++;
        if(btCounter % 1000 == 0){
            Platform.runLater(() -> guiController.updateBoard(board));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(BacktrackHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            return backtrack(index + 1);
        }
    }

    private boolean isColoringValid(int index, Board btBoard){
        Board validCheckBoard = new Board(btBoard);
        validCheckBoard.getRegions().get(index).markRegion();
        return validCheckBoard.isValid();
    }
}
