package eva.crossover;

import base.Board;

import java.util.ArrayList;

public interface ICrossover {
    ArrayList<Board> doCrossover(Board parent1, Board parent2);
}