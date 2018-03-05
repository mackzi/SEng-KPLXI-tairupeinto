package base;

import java.util.ArrayList;

public class Region {

    public ArrayList<Cell> getCells() {
        return cells;
    }

    private ArrayList<Cell> cells;

    public boolean isMarked() {
        return isMarked;
    }

    private boolean isMarked;

    public int getId() {
        return id;
    }

    private int id;

    Region(int id, int[][] boardRegions){
        cells = new ArrayList<>();
        for(int rows = 0; rows<boardRegions.length; rows++){
            for(int cols = 0; cols <boardRegions.length; cols++){
                if(boardRegions[rows][cols] == id)
                    cells.add(buildCell(rows, cols, boardRegions));
            }
        }
        this.id = id;
        isMarked = false;
    }

    private Cell buildCell(int rows, int cols, int[][] boardRegions){
        Cell c = new Cell(rows, cols);
        if (rows == 0)
            c.drawBorderTop();
        if (rows == 8)
            c.drawBorderBottom();
        if (cols == 0)
            c.drawBorderLeft();
        if (cols == 8)
            c.drawBorderRight();
        if(rows < 8){
            if(boardRegions[rows][cols]!= boardRegions[rows+1][cols])
                c.drawBorderBottom();
        }
        if(cols < 8){
            if(boardRegions[rows][cols]!= boardRegions[rows][cols+1])
                c.drawBorderRight();
        }
        if(rows > 0){
            if(boardRegions[rows][cols]!= boardRegions[rows-1][cols])
                c.drawBorderTop();
        }
        if(cols > 0){
            if(boardRegions[rows][cols]!= boardRegions[rows][cols-1])
                c.drawBorderLeft();
        }
        return c;
    }

    public void markRegion(){
        isMarked = true;
        for (Cell c: cells) {
            c.setMarked(true);
        }
    }

    public void unMarkRegion(){
        isMarked = false;
        for (Cell c: cells) {
            c.setMarked(false);
        }
    }
}
