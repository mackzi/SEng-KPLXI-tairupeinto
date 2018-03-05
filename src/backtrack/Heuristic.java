package backtrack;

import base.Board;
import base.Region;
import config.Configuration;

import java.util.*;

public class Heuristic {
    Board board;

    public Heuristic(Board board){
        this.board = board;
    }

    public Set<Integer> doRowHeuristic(int row){

        //Extract regions that belong to ROW from board
        Set<Integer> barAmounts = new TreeSet<>();
        for(int i = 0; i< board.getBoardRegions().length; i++){
            barAmounts.add(board.getBoardRegions()[row][i]);
        }
        //System.out.println(barAmounts.size());System.out.println(barAmounts.toArray()[4]);

        //evaluate Max Number of combinations and generate them
        double numberOfCombinations = Math.pow(2, barAmounts.size());
        ArrayList<BitSet> combinations = new ArrayList<>();
        for(int i = 0; i < numberOfCombinations; i++)
            combinations.add(BitSet.valueOf(new long[]{i}));

        //create n-testboards
        ArrayList<Board> testBoards = new ArrayList<>();
        for(int i = 0; i < combinations.size(); i++)
            testBoards.add(new Board());

        //Mark spezific region from combinations
        for (int i = 0; i < testBoards.size(); i++) {
            for(int k = 0; k < barAmounts.size(); k++){
                if(combinations.get(i).get(k))
                    testBoards.get(i).getRegions().get((Integer) barAmounts.toArray()[k]).markRegion();
            }
        }

        //identifiy unvalid boards
        ArrayList<Board> unValidBoards = new ArrayList<>();
        for (Board testBoard : testBoards) {
            if (testBoard.evaluateRowValues()[row] != Configuration.ROW_SOLUTION[row])
                unValidBoards.add(testBoard);
        }

        //remove unvalid boards from testboard
        for (Board unValidBoard : unValidBoards) testBoards.remove(unValidBoard);

        //look fpr congruence
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

        System.out.println(result);
        return result;
    }

    public Set<Integer> doColHeuristic(int col){

        //Extract regions that belong to col from board
        Set<Integer> barAmounts = new TreeSet<>();
        for(int i = 0; i< board.getBoardRegions().length; i++){
            barAmounts.add(board.getBoardRegions()[i][col]);
        }
        //System.out.println(barAmounts.size());System.out.println(barAmounts.toArray()[4]);

        //evaluate Max Number of combinations and generate them
        double numberOfCombinations = Math.pow(2, barAmounts.size());
        ArrayList<BitSet> combinations = new ArrayList<>();
        for(int i = 0; i < numberOfCombinations; i++)
            combinations.add(BitSet.valueOf(new long[]{i}));

        //create n-testboards
        ArrayList<Board> testBoards = new ArrayList<>();
        for(int i = 0; i < combinations.size(); i++)
            testBoards.add(new Board());

        //Mark spezific region from combinations
        for (int i = 0; i < testBoards.size(); i++) {
            for(int k = 0; k < barAmounts.size(); k++){
                if(combinations.get(i).get(k))
                    testBoards.get(i).getRegions().get((Integer) barAmounts.toArray()[k]).markRegion();
            }
        }

        //identifiy unvalid boards
        ArrayList<Board> unValidBoards = new ArrayList<>();
        for (Board testBoard : testBoards) {
            if (testBoard.evaluateColValues()[col] != Configuration.COL_SOLUTION[col])
                unValidBoards.add(testBoard);
        }

        //remove unvalid boards from testboard
        for (Board unValidBoard : unValidBoards) testBoards.remove(unValidBoard);

        //look fpr congruence
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

        System.out.println(result);
        return result;
    }
}
