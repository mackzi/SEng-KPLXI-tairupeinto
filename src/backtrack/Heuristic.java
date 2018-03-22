package backtrack;

import base.Board;
import base.Region;
import config.Configuration;

import java.util.*;

public class Heuristic {
    private final Board board;

    public Heuristic() {
        this.board = new Board();
    }


    /**
     * This function returns regions on the board that are marked/black due to
     * a small heuristic which looks for overlapping solutions and marks them.
     */
    public Set<Integer> doHeuristic(){
        Set<Integer> result = new TreeSet<>();
        for(int i = 0; i< 9; i++){
            result.addAll(doRowHeuristic(i));
            result.addAll(doColHeuristic(i));
        }
        return result;
    }

    private Set<Integer> doRowHeuristic(int row){

        //Extract regions that belong to ROW from board
        Set<Integer> barAmounts = new TreeSet<>();
        for(int i = 0; i< board.getBoardRegions().length; i++){
            barAmounts.add(board.getBoardRegions()[row][i]);
        }

        //evaluate Max Number of combinations and generate them
        double numberOfCombinations = Math.pow(2, barAmounts.size());
        ArrayList<BitSet> combinations = new ArrayList<>();
        for(int i = 0; i < numberOfCombinations; i++)
            combinations.add(BitSet.valueOf(new long[]{i}));

        //create n-testboards
        ArrayList<Board> testBoards = new ArrayList<>();
        for(int i = 0; i < combinations.size(); i++)
            testBoards.add(new Board());

        //Mark specific region from combinations
        for (int i = 0; i < testBoards.size(); i++) {
            for(int k = 0; k < barAmounts.size(); k++){
                if(combinations.get(i).get(k))
                    testBoards.get(i).getRegions().get((Integer) barAmounts.toArray()[k]).markRegion();
            }
        }

        //identify unvalid boards
        ArrayList<Board> unValidBoards = new ArrayList<>();
        for (Board testBoard : testBoards) {
            if (testBoard.evaluateRowValues()[row] != Configuration.ROW_SOLUTION[row])
                unValidBoards.add(testBoard);
        }

        //remove unvalid boards from testboard
        for (Board unValidBoard : unValidBoards)
            testBoards.remove(unValidBoard);

        //look for congruence
        ArrayList<Integer> congruence = new ArrayList<>();
        for (Board testBoard: testBoards) {
            for (Region r: testBoard.getRegions()) {
                if(r.isMarked())
                    congruence.add(r.getId());
            }
        }

        Set<Integer> result = new TreeSet<>();
        for (Integer i: congruence) {
            if(Collections.frequency(congruence, i) == testBoards.size())
                result.add(i);
        }
        return result;
    }

    private Set<Integer> doColHeuristic(int col){

        //Extract regions that belong to col from board
        Set<Integer> barAmounts = new TreeSet<>();
        for(int i = 0; i< board.getBoardRegions().length; i++){
            barAmounts.add(board.getBoardRegions()[i][col]);
        }

        //evaluate Max Number of combinations and generate them
        double numberOfCombinations = Math.pow(2, barAmounts.size());
        ArrayList<BitSet> combinations = new ArrayList<>();
        for(int i = 0; i < numberOfCombinations; i++)
            combinations.add(BitSet.valueOf(new long[]{i}));

        //create n-testboards
        ArrayList<Board> testBoards = new ArrayList<>();
        for(int i = 0; i < combinations.size(); i++)
            testBoards.add(new Board());

        //Mark specific region from combinations
        for (int i = 0; i < testBoards.size(); i++) {
            for(int k = 0; k < barAmounts.size(); k++){
                if(combinations.get(i).get(k))
                    testBoards.get(i).getRegions().get((Integer) barAmounts.toArray()[k]).markRegion();
            }
        }

        //identify unvalid boards
        ArrayList<Board> unValidBoards = new ArrayList<>();
        for (Board testBoard : testBoards) {
            if (testBoard.evaluateColValues()[col] != Configuration.COL_SOLUTION[col])
                unValidBoards.add(testBoard);
        }

        //remove unvalid boards from testboard
        for (Board unValidBoard : unValidBoards)
            testBoards.remove(unValidBoard);

        //look for congruence
        ArrayList<Integer> congruence = new ArrayList<>();
        for (Board testBoard: testBoards) {
            for (Region r: testBoard.getRegions()) {
                if(r.isMarked())
                    congruence.add(r.getId());
            }
        }

        Set<Integer> result = new TreeSet<>();
        for (Integer i: congruence) {
            if(Collections.frequency(congruence, i) == testBoards.size())
                result.add(i);
        }
        return result;
    }
}
