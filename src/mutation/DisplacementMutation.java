package mutation;

import base.Board;

import java.util.ArrayList;

public class DisplacementMutation extends Mutation implements IMutation {

    public Board doMutation(Board Board) {
        //if(Board.getNumberOfItems() == 0 || Board.getNumberOfItems() == 150)
        //    return new Board(Board);

        Board mutated;
        do{
            int location = random.nextInt(0, GENE_SIZE-1);
            int destinaton =  random.nextInt(0, GENE_SIZE);
            int displacementSize =  random.nextInt(1, GENE_SIZE);
            mutated = displace(Board,  location, destinaton, displacementSize);
        }while(mutated == null || Board.equals(mutated));
        return mutated;
    }

    protected Board displace(Board aBoard, int aLocation, int aDestination, int aDisplacementSize) {
        if(!checkInput(aLocation, aDestination, aDisplacementSize))
            return null;

        ArrayList<Boolean> gene = aBoard.getGenes();
        ArrayList<Boolean> removedItems = extractSublist(gene, aLocation, aDisplacementSize);

        if(aDestination > aLocation)
            gene.addAll(aDestination-aDisplacementSize, removedItems);
        else
            gene.addAll(aDestination, removedItems);

        return new Board(gene);
    }

    private boolean checkInput(int aLocation, int aDestination, int aDisplacementSize) {
        return
                checkInputBounds(aLocation, aDestination, aDisplacementSize) &&
                checkDisplacementNotExcedingGene(aLocation, aDisplacementSize) &&
                checkDestinationNotWithinDisplacement(aLocation, aDestination, aDisplacementSize);
    }

    private boolean checkInputBounds(int aLocation, int aDestination, int aDisplacementSize) {
        return
                checkBounds(aLocation, GENE_SIZE) &&
                checkBounds(aDestination, GENE_SIZE + 1) &&
                checkBounds(aDisplacementSize, GENE_SIZE);
    }

    private boolean checkDisplacementNotExcedingGene(int aLocation, int aDisplacementSize){
        return aLocation + aDisplacementSize <= GENE_SIZE;
    }

    private boolean checkDestinationNotWithinDisplacement(int aLocation, int aDestination, int aDisplacementSize) {
        return
                !(aDestination > aLocation && aDestination < aLocation + aDisplacementSize) &&
                !(aDestination < aLocation && aDestination > aLocation + aDisplacementSize);
    }
}