package eva.mutation;

import base.Board;

import java.util.ArrayList;

public class ScrambleMutation extends Mutation implements IMutation {

    public Board doMutation(Board Board) {
        //if(Board.getNumberOfItems() == 0 || Board.getNumberOfItems() == 150)
        //    return new Board(Board);

        Board mutated;
        do{
            int location = random.nextInt(0, GENE_SIZE-1);
            int size =  random.nextInt(0, GENE_SIZE);
            mutated = scramble(Board,  location, size);
        }while(mutated == null || Board.equals(mutated));
        return mutated;
    }

    protected Board scramble(Board aBoard, int aLocation, int aSize){
        if(!checkInput(aLocation, aSize))
            return null;
        ArrayList<Boolean> gene = aBoard.getGenes();
        ArrayList<Boolean> removedItems = extractSublist(gene, aLocation, aSize);
        gene.addAll(aLocation, rearrange(removedItems));
        return new Board(gene);
    }

    protected <T> ArrayList<T> rearrange(ArrayList<T> aList){
        if(aList == null) return null;
        ArrayList<T> output = new ArrayList<>();
        while(aList.size() > 0){
            int randomIndex = random.nextInt(0, aList.size()-1);
            output.add(aList.remove(randomIndex));
        }
        return output;
    }
}