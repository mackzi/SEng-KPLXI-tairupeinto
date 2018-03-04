import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Cell extends javafx.scene.layout.Pane{

    private boolean isMarked;


    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    private int cols, rows;


    private int topBorder,rightBorder,bottomBorder,leftBorder;

    public Cell(int rows, int cols){
        super();
        super.setStyle("-fx-background-color: lightgray;");
        this.cols = cols;
        this.rows = rows;
        isMarked = false;
        drawBorder(1, 1, 1, 1);
    }


    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
        super.setStyle("-fx-background-color: grey");
    }

    public void drawBorder( int top, int right, int bottom, int left){
        if(left>=0 && top>=0 && right>=0 &&bottom>=0){
            super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(top,right,bottom,left))));
            topBorder=top;
            rightBorder=right;
            bottomBorder=bottom;
            leftBorder=left;
        }
    }

    public void drawBorderLeft(int left){
        if(left>=0){
            super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder,rightBorder,bottomBorder,left))));
            leftBorder=left;
        }
    }

    public void drawBorderTop(int top){
        if(top>=0){
            super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(top,rightBorder,bottomBorder,leftBorder))));
            topBorder=top;
        }
    }

    public void drawBorderRight(int right){
        if(right>=0){
            super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder,right,bottomBorder,leftBorder))));
            rightBorder=right;
        }
    }

    public void drawBorderBottom(int bottom){
        if(bottom>=0){
            super.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder,rightBorder,bottom,leftBorder))));
            bottomBorder= bottom;
        }
    }
}
