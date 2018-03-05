package backtrack;

import base.Board;
import config.Configuration;
import gui.GuiController;
import gui.Main;
import javafx.application.Platform;

import java.util.*;

public class BacktrackHandler implements Runnable{

    GuiController guiController;
    Thread backtrackThread;
    Board board;
    int count;

    public BacktrackHandler(GuiController guiController){
        this.guiController = guiController;
        board = new Board();
        count = 0;
    }


    @Override
    public void run() {
        try {
            backtrackThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(this::execute);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            Thread.currentThread().interrupt();
            backtrackThread.interrupt();
        }
    }

    private void execute(){
        //while (!backtrack(board, 0))
        //{}
        heuristic();
        System.out.println("Fuck");

        guiController.updateBoard(board);
    }

    private void heuristic(){
        //evaluate n choose k combinations for each row
        //filter nonValid combinations
        //look for congruence

        Heuristic h = new Heuristic(board);
        //h.doColHeuristic(1);
        for(int row = 0; row < 9; row++) {
            for (Integer i : h.doRowHeuristic(row)) {
                board.getRegions().get(i).markRegion();
            }
        }

        for(int col = 0; col < 9; col++) {
            for (Integer i : h.doColHeuristic(col)) {
                board.getRegions().get(i).markRegion();
            }
        }

        //look through cols
    }

    private boolean backtrack(Board b, int region){

        count++;
        //if(count % 1000 == 0)
            System.out.println(count);

        //guiController.updateBoard(board);
        if(b.isSolved()){
            return true;
        }
        else {
            if(b.isValid()) {
                b.getRegions().get(region).markRegion();
                if (backtrack(b, region + 1))
                    return true;
                else
                    b.getRegions().get(region).unMarkRegion();
            }
        }

        return false;
    }
}
