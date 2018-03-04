import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.util.Vector;

public class DrawEngine {

    private GraphicsContext graphicsContext;

    public DrawEngine(Canvas canvas){
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void clearCanvas(){
        graphicsContext.clearRect(0, 0, 500, 500);
    }

    public void draw(){
        //graphicsContext.setStroke(Color.AZURE);

        graphicsContext.strokePolygon(new double[]{0.0, 100.0, 100.0, 0.0},
                                      new double[]{0.0, 0.0, 100.0, 100.0}, 4);

        Vector<Double> point = new Vector<>(2);

    }
}
