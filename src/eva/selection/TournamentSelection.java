package eva.selection;
import java.util.ArrayList;

import base.Board;
import config.Configuration;

//@author 2484253

public class TournamentSelection implements ISelection {
    public ArrayList<Board> doSelection(ArrayList<Board> givenBoards) {
        ArrayList<Board> Boards = new ArrayList<>(givenBoards);
        ArrayList<Board> fighters = new ArrayList<>(); //selected Tournament participants (even)
        ArrayList<Board> finishedSacks = new ArrayList<>();

        if (Boards.size() >= Configuration.instance.tournamentParticipants) { //if there are not enough Bags
            for (int i = 0; i < Configuration.instance.tournamentParticipants; i++) { //select 52 Tournament participant

                int randomFighter = Configuration.instance.random.nextInt(Boards.size()); //select random Bag
                fighters.add(Boards.get(randomFighter));
                Boards.remove(randomFighter);
            }

            Board fighter1;
            Board fighter2;

            for (int i = 0; i < (Configuration.instance.tournamentParticipants / 2); i++) {

                int randomInt1 = Configuration.instance.random.nextInt(fighters.size());
                fighter1 = fighters.get(randomInt1);
                fighters.remove(randomInt1);

                int randomInt2 = Configuration.instance.random.nextInt(fighters.size());
                fighter2 = fighters.get(randomInt2);
                fighters.remove(randomInt2);

                if (fighter1.evaluateFitness() < fighter2.evaluateFitness()) {
                    finishedSacks.add(fighter1);
                } else {
                    finishedSacks.add(fighter2);
                }
            }
            return finishedSacks;
        }
        return Boards;
    }
    public String toString() {
        return getClass().getSimpleName();
    }
}