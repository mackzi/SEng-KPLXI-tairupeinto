package eva.mutation;

import base.Board;

import java.util.ArrayList;

public class ExchangeMutation extends Mutation implements IMutation {

    public Board doMutation(Board Board){
        //if(Board.getNumberOfItems() == 0 || Board.getNumberOfItems() == 150)
        //    return new Board(Board);

        Board mutated;
        do {
            int indexA = random.nextInt(0, GENE_SIZE-1);
            int indexB = random.nextInt(0, GENE_SIZE-1);
            mutated = swap(Board, indexA, indexB);
        }while(mutated == null || Board.equals(mutated));
        return mutated;
    }

    protected Board swap(Board aBoard, int indexA, int indexB){
        ArrayList<Boolean> gene = aBoard.getGenes();
        boolean value = gene.get(indexA);
        gene.set(indexA, gene.get(indexB));
        gene.set(indexB, value);
        return new Board(gene);
    }
}