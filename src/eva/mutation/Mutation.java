package eva.mutation;

import config.Configuration;
import config.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.List;

public abstract class Mutation implements IMutation{
    protected static final int GENE_SIZE = Configuration.BORDER_SIZE;
    protected static final MersenneTwisterFast random = Configuration.instance.random; // new MersenneTwisterFast();

    boolean checkInput(int aLocation, int aSize) {
        return (aLocation + aSize) < GENE_SIZE;
    }

    public static <T> ArrayList<T> extractSublist(List<T> aList, int aLocation, int aSize){
        ArrayList<T> removedItems = new ArrayList<>();
        for (int i = 0; i < aSize; ++i)
            removedItems.add(aList.remove(aLocation));
        return removedItems;
    }

    boolean checkBounds(int aLocation, int upperBound){
        return aLocation >= 0 && aLocation < upperBound;
    }
}
