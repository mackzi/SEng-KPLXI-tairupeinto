package mutation;

import base.Board;

public class InsertionMutation extends mutation.DisplacementMutation implements IMutation {

    @Override
    public Board doMutation(Board Board) {
        //if(Board.getNumberOfItems() == 0 || Board.getNumberOfItems() == 150)
        //    return new Board(Board);

        Board mutated;
        do{
            int location = random.nextInt(0, GENE_SIZE - 1);
            int destinaton = random.nextInt(0, GENE_SIZE);
            mutated = displace(Board, location, destinaton, 1);
        }while (mutated == null || Board.equals(mutated));
        return mutated;
    }
}