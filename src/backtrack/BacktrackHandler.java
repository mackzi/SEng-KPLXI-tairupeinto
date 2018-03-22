package backtrack;

import base.Board;
import gui.GuiController;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BacktrackHandler extends Thread{

    private final GuiController guiController;
    private final Board heuBoard;
    private final ArrayList<Integer> backtrackRegions;
    private int numberOfBTRegions;
    private int btCounter;
    private Board board;

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

    private boolean backtrack(int step) {
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
            if (step < numberOfBTRegions)
                btIndex = backtrackRegions.get(step);
            else
                return false;
            if(isColoringValid(btIndex, board)){
                board.getRegions().get(btIndex).markRegion();
                if (backtrack(step + 1))
                    return true;
                else
                    board.getRegions().get(btIndex).unMarkRegion();
            }
            return backtrack(step + 1);
        }
    }

    public boolean isColoringValid(int index, Board btBoard) {
        Board validCheckBoard = new Board(btBoard);
        validCheckBoard.getRegions().get(index).markRegion();
        return validCheckBoard.isValid();
    }
}
