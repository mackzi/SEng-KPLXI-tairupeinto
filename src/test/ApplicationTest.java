package test;

import backtrack.BacktrackHandler;
import backtrack.Heuristic;
import base.Board;
import gui.GuiController;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ApplicationTest {

    private Board solution;

    @Before
    public void init() {
        solution = new Board();
        solution.getRegions().get(0).markRegion();
        solution.getRegions().get(2).markRegion();
        solution.getRegions().get(4).markRegion();
        solution.getRegions().get(5).markRegion();
        solution.getRegions().get(7).markRegion();
        solution.getRegions().get(8).markRegion();
        solution.getRegions().get(13).markRegion();
        solution.getRegions().get(14).markRegion();
        solution.getRegions().get(17).markRegion();
        solution.getRegions().get(19).markRegion();
        solution.getRegions().get(23).markRegion();
        solution.getRegions().get(24).markRegion();
        solution.getRegions().get(25).markRegion();
    }

    @Test
    public void testHeuristic() {
        Heuristic heuristic = new Heuristic();
        Set<Integer> expected = new TreeSet<>();
        expected.add(0);
        expected.add(2);
        expected.add(5);
        expected.add(7);
        expected.add(23);
        assertEquals(expected, heuristic.doHeuristic());
    }

    @Test
    public void testEmptyBoardIsColoringValid() {
        BacktrackHandler backtrackHandler = new BacktrackHandler(new GuiController());
        assertTrue(backtrackHandler.isColoringValid(0, new Board()));
    }

    @Test
    public void testSolvedBoard() {

        assertTrue(solution.isSolved());
    }
}
