package test;

import backtrack.BacktrackHandler;
import backtrack.Heuristic;
import base.Board;
import config.Configuration;
import eva.EvaHandler;
import gui.GuiController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;


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

    @Test
    public void testIsBoardValid() {
        assertTrue(solution.isValid());
    }

    @Test
    public void testEvaluateFitness() {
        assertEquals(0, solution.evaluateFitness());
    }

    @Test
    public void testGetGenesFromBoard() {
        ArrayList<Boolean> genes = new ArrayList<>();
        Board parent = new Board();
        parent.getRegions().get(0).markRegion();
        genes.add(true);
        for (int i = 1; i < 28; i++) {
            genes.add(false);
        }

        assertEquals(genes, parent.getGenes());
    }

    @Test
    public void testEvaluateCellCount() {
        assertArrayEquals(Configuration.ROW_SOLUTION, solution.evaluateRowValues());
        assertArrayEquals(Configuration.COL_SOLUTION, solution.evaluateColValues());
    }

    @Test
    public void testInitEvaPopulation() {
        EvaHandler eva = new EvaHandler(new GuiController(), "Tournament", "OnePoint", "Displacement");
        assertEquals(Configuration.INITIAL_POPULATION_SIZE, eva.getPopulation().size());
    }
}
