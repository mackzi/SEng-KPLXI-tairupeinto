import java.util.ArrayList;

public class Board {

    public ArrayList<Region> getRegions() {
        return regions;
    }

    private ArrayList<Region> regions;
                //[zeile][spalte]
    private int[][] boardRegions = new int[][]{
            { 0,  0,  1,  2,  2,  2,  4,  5,  5},
            { 0,  0,  1,  3,  3,  4,  4,  6,  6},
            { 7,  7,  7,  7,  8,  8,  9,  9,  6},
            {10, 10, 11,  7, 12,  8, 13, 13, 13},
            {14, 14, 11, 12, 12, 12, 12, 15, 15},
            {16, 17, 17, 18, 18, 18, 19, 20, 20},
            {16, 21, 21, 22, 22, 23, 19, 24, 24},
            {16, 16, 25, 25, 22, 23, 23, 24, 24},
            {16, 25, 25, 26, 26, 23, 23, 27, 27}
    };


    public Board(){
        regions = new ArrayList<>();
        System.out.println(boardRegions[4][0]);
        for(int i = 0; i<28; i++){
            regions.add(new Region(i, boardRegions));
        }
    }
}
