package crossover;

import base.Board;

import java.util.ArrayList;

public class TwoPointCrossover implements ICrossover {
    /**
     * Crossover mit 2 Points
     * @param parent1;
     * @param parent2;
     * @return an ArrayList of two Knapsacks - the children
     */
    public ArrayList<Board> doCrossover(Board parent1, Board parent2) {
        Cross c = new Cross();

        return new ArrayList<>(c.crossover(2, parent1, parent2));

    }

    public String toString() {
        return getClass().getSimpleName();
    }
}