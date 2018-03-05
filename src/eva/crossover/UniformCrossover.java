package eva.crossover;

import java.util.ArrayList;
import base.Board;
import config.Configuration;


public class UniformCrossover implements ICrossover {

    private static final int MAXNUMBER_OF_ITERATIONS = 10000;

    /**
     * Does a eva.crossover based on a coinflip on every gene
     * @param parent1
     * @param parent2
     * @return children
     */
    public ArrayList<Board> doCrossover(Board parent1,Board parent2) {
        int iterations = 0;
        boolean noValidChild = true;
        ArrayList<Board> children = new ArrayList<>();
        ArrayList<Boolean> genesFromParent1 = new ArrayList<>(parent1.getGenes());
        ArrayList<Boolean> genesFromParent2 = new ArrayList<>(parent2.getGenes());

        do {
            ArrayList<Boolean> genesForChild1 = new ArrayList<>();
            ArrayList<Boolean> genesForChild2 = new ArrayList<>();

            for (int i = 0; i < Configuration.NUMBER_OF_REGIONS; i++) {
                boolean change = Configuration.instance.random.nextBoolean();
                if (change) {
                    genesForChild1.add(genesFromParent2.get(i));
                    genesForChild2.add(genesFromParent1.get(i));
                } else {
                    genesForChild1.add(genesFromParent1.get(i));
                    genesForChild2.add(genesFromParent2.get(i));
                }
            }
            Board child1 = new Board(genesForChild1);
            Board child2 = new Board(genesForChild2);

            //if (child1.isValid() && child2.isValid()) {
                children.add(child1);
                children.add(child2);
                noValidChild = false;
            //}

            iterations++;


        } while(noValidChild && iterations <= MAXNUMBER_OF_ITERATIONS);

        return children;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}