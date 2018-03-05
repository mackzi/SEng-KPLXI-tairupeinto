package crossover;

import base.Board;

import java.util.ArrayList;

public class OnePointCrossover implements ICrossover {
    /**
     * Crossover mit 1 Points
     * @param parent1;
     * @param parent2;
     * @return an ArrayList of two Knapsacks - the children
     */
    public ArrayList<Board> doCrossover(Board parent1, Board parent2) {

        Cross c = new Cross();

        return new ArrayList<>(c.crossover(1, parent1, parent2));
    }

    public String toString() {

        return getClass().getSimpleName();
    }
}