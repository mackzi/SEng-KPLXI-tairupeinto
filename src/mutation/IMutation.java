package mutation;

import base.Board;

public interface IMutation {

    Board doMutation(Board board);

    String toString();
}