package eva.selection;

import java.util.ArrayList;

import base.Board;
import config.Configuration;


/**
 * @author 3742367
 */
public class RouletteWheelSelection implements ISelection {
    public ArrayList<Board> doSelection(ArrayList<Board> Boards) {
        if (Boards.size() >= Configuration.instance.rouletteLimit) {
            int sum = 0;
            float[] probability = new float[Boards.size() + 1];
            probability[0] = 0;

            for (Board sack : Boards) {
                sum = sum + sack.evaluateFitness();
            }

            for (int i = 0; i < Boards.size(); i++) {
                float calculatedValue = (float)Boards.get(i).evaluateFitness() / sum;
                calculatedValue = calculatedValue - 0.00001F;
                probability[i+1] = (probability[i] + calculatedValue);
            }

            ArrayList<Board> result = new ArrayList<>();
            boolean maximumReached = false;
            while (!maximumReached) {
                int selectedIndex = -1;
                while (selectedIndex == -1) {
                    double twist = Configuration.instance.random.nextDouble();
                    for (int a = 0; a < probability.length; a++) {
                        if (probability[a] > twist) {
                            selectedIndex = a - 1;
                            break;
                        }
                    }
                }
                boolean alreadyRes = false;
                for (Board res : result) {
                    if (res == Boards.get(selectedIndex)) {
                        alreadyRes = true;
                        break;
                    }
                }
                if (!alreadyRes)
                    result.add(Boards.get(selectedIndex));
                int maxSelection = Configuration.instance.rouletteLimit;
                if ((result.size() == maxSelection))
                    maximumReached = true;
            }

            return result;
        }
        return Boards;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}