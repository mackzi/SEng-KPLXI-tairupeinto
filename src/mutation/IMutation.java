package mutation;

import base.Board;

public interface IMutation {

    /**
     *
     * @param knapsack Knapsack der mutiert werden soll.
     * @return Der mutierte Knapsack.
     */
    Board doMutation(Board knapsack);

    String toString();
}