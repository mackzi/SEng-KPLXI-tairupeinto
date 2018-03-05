package mutation;

import base.Board;

import java.util.ArrayList;
import java.util.Collections;

public class InversionMutation extends Mutation implements IMutation {

    public Board doMutation(Board Board) {
        //if(Board.getNumberOfItems() == 0 || Board.getNumberOfItems() == 150)
        //    return new Board(Board);

        Board mutated;
        do{
            int location = random.nextInt(0, GENE_SIZE-1);
            int aSize =  random.nextInt(0, GENE_SIZE);
            mutated = inverse(Board,  location, aSize);
        }while(mutated == null || Board.equals(mutated));
        return mutated;
    }

    protected Board inverse(Board aBoard, int aLocation, int aSize){
        if(!checkInput(aLocation, aSize))
            return null;
        ArrayList<Boolean> gene = aBoard.getGenes();
        ArrayList<Boolean> removedItems = extractSublist(gene, aLocation, aSize);
        Collections.reverse(removedItems);
        gene.addAll(aLocation, removedItems);
        return new Board(gene);
    }


}