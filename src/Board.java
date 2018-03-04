import java.util.ArrayList;

public class Board {

    private ArrayList<Region> regions;

    public ArrayList<Region> getRegions() {
        return regions;
    }

    Board(){
        regions = new ArrayList<>(Configuration.NUMBER_OF_REGIONS);

        for(int i = 0; i<Configuration.NUMBER_OF_REGIONS; i++){
            regions.add(new Region(i, Configuration.BOARD_REGIONS));
        }
    }
}
