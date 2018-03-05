package eva.crossover;

import base.Board;
import config.Configuration;

import java.util.ArrayList;

public class KPointCrossover implements ICrossover {
    /**
     * Crossover with k Points
     * @param parent1;
     * @param parent2;
     * @return an ArrayList of two Boards - the children
     */

    public ArrayList<Board> doCrossover(Board parent1,Board parent2) {

        //number of points to split the parent chromosomes: k-value
        int randInt = Configuration.instance.random.nextInt(1, Configuration.NUMBER_OF_REGIONS-2);

        Cross c = new Cross();
        //System.out.println(randInt);

        return new ArrayList<>(c.crossover(randInt, parent1, parent2));
    }

    public String toString() {

        return getClass().getSimpleName();
    }
}