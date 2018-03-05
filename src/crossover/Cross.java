package crossover;

import base.Board;
import config.Configuration;

import java.util.ArrayList;
import java.util.Collections;

class Cross {

    private ArrayList<Board> children = new ArrayList<>();
    private static final int MAXNUMBER_OF_ITERATIONS = 10000;

    public ArrayList<Board> crossover(int k, Board parent1, Board parent2) {

        int iterations = 0; //counter in case of invalid children

        boolean noValidChild = true;

        ArrayList<Boolean> genesFromParent1 = new ArrayList<>(parent1.getGenes());
        ArrayList<Boolean> genesFromParent2 = new ArrayList<>(parent2.getGenes());

        do {

            ArrayList<Integer> splitPoints = generateKsortedSplitPoints(k);     //Generates k splitpoint in a sorted Arraylist

            boolean change = true;

            //first SubList for Child 1
            ArrayList<Boolean> subList1 = new ArrayList<>(genesFromParent1.subList(0, splitPoints.get(0)));   //first sublist
            ArrayList<Boolean> genesForChild1 = new ArrayList<>(subList1);
            //first SubList for Child 2
            ArrayList<Boolean> subList2 = new ArrayList<>(genesFromParent2.subList(0, splitPoints.get(0)));
            ArrayList<Boolean> genesForChild2 = new ArrayList<>(subList2);

            //middle SubLists for Child 1 and Child 2
            for (int i = 0; i < (splitPoints.size() - 1); i++) {
                subList1 = new ArrayList<>(genesFromParent1.subList(splitPoints.get(i), splitPoints.get(i + 1)));
                subList2 = new ArrayList<>(genesFromParent2.subList(splitPoints.get(i), splitPoints.get(i + 1)));
                swapSubLists(change, genesForChild1, genesForChild2, subList1, subList2);
                change = !change;
            }

            //last SubLists for Child 1 and Child 2
            subList1 = new ArrayList<>(genesFromParent1.subList(splitPoints.get(splitPoints.size() - 1), Configuration.NUMBER_OF_REGIONS)); //last sublist
            subList2 = new ArrayList<>(genesFromParent2.subList(splitPoints.get(splitPoints.size() - 1), Configuration.NUMBER_OF_REGIONS));
            swapSubLists(change, genesForChild1, genesForChild2, subList1, subList2);

            Board child1 = new Board(genesForChild1);
            Board child2 = new Board(genesForChild2);
            
            //if (child1.isValid() && child2.isValid()) {
                children.add(child1);
                children.add(child2);
                noValidChild = false;
            //}

            iterations++;

        } while (noValidChild && iterations <= MAXNUMBER_OF_ITERATIONS);

        return children;
    }

    private void swapSubLists(boolean change, ArrayList<Boolean> genesForChild1, ArrayList<Boolean> genesForChild2, ArrayList<Boolean> subList1, ArrayList<Boolean> subList2) {
        if (change) {
            genesForChild1.addAll(subList2);
            genesForChild2.addAll(subList1);
        } else {
            genesForChild1.addAll(subList1);
            genesForChild2.addAll(subList2);
        }
    }

    private ArrayList<Integer> generateKsortedSplitPoints(int k) {
        ArrayList<Integer> splitPoints = new ArrayList<>();
        ArrayList<Integer> splitValues = new ArrayList<>();

        for(int i = 1; i < Configuration.NUMBER_OF_REGIONS-1; i++)
            splitValues.add(i);

        for(int i = 0; i < k; i++) {
            int randNumber = Configuration.instance.random.nextInt(0, (Configuration.NUMBER_OF_REGIONS-3)-i);
            splitPoints.add(splitValues.get(randNumber));
            splitValues.remove(randNumber);
        }
        Collections.sort(splitPoints);
        return splitPoints;
    }
}