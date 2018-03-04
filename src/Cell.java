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
        super.setStyle("-fx-background-color: #f7f7f7;");
        this.cols = cols;
        this.rows = rows;
        isMarked = false;
        topBorder=1;
        rightBorder=1;
        bottomBorder=1;
        leftBorder=1;
    }


    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
        super.setStyle("-fx-background-color: #636363");
    }

    public void drawBorderLeft(){
        leftBorder= Configuration.BORDER_THINKNESS;
        drawBorder();
    }

    public void drawBorderTop(){
        topBorder=Configuration.BORDER_THINKNESS;
        drawBorder();
    }

    public void drawBorderRight(){
        rightBorder=Configuration.BORDER_THINKNESS;
        drawBorder();
    }

    public void drawBorderBottom(){
        bottomBorder= Configuration.BORDER_THINKNESS;
        drawBorder();
    }

    private void drawBorder(){
        super.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID,
                                                    CornerRadii.EMPTY,
                                    new BorderWidths(topBorder,rightBorder,bottomBorder,leftBorder))));
    }
}