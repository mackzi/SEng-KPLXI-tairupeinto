import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private Board board;

    @FXML
    Button runButton;

    @FXML
    GridPane gridPane;

    @FXML
    Label labelRow0, labelRow1, labelRow2, labelRow3, labelRow4, labelRow5, labelRow6, labelRow7, labelRow8;

    @FXML
    Label labelCol0, labelCol1, labelCol2, labelCol3, labelCol4, labelCol5, labelCol6, labelCol7, labelCol8;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board();
        for (Region r: board.getRegions()) {
            for (Cell c: r.getCells()) {
                gridPane.add(c, c.getCols(), c.getRows());
            }
        }
    }

    public void updateLabels(){
        int[] rowValues = board.evaluateRowValues();
        int[] colValues = board.evaluateColValues();

        Label[] rowLabels = new Label[]{labelRow0, labelRow1, labelRow2, labelRow3, labelRow4, labelRow5, labelRow6, labelRow7, labelRow8};
        Label[] colLabels = new Label[]{labelCol0, labelCol1, labelCol2, labelCol3, labelCol4, labelCol5, labelCol6, labelCol7, labelCol8};

        int[] rowSolution = Configuration.ROW_SOLUTION;
        int[] colSolution = Configuration.COL_SOLUTION;

        for(int i = 0; i < 9; i++){
            rowLabels[i].setText(String.valueOf(rowValues[i]));
            colLabels[i].setText(String.valueOf(colValues[i]));
            if(rowValues[i] == rowSolution[i])
                rowLabels[i].setTextFill(Color.GREEN);
            if(colValues[i] == colSolution[i])
                colLabels[i].setTextFill(Color.GREEN);
        }
    }

    public void execute(){
        board.getRegions().get(0).markRegion();
        board.getRegions().get(2).markRegion();
        board.getRegions().get(4).markRegion();
        board.getRegions().get(5).markRegion();
        board.getRegions().get(7).markRegion();
        board.getRegions().get(8).markRegion();
        board.getRegions().get(13).markRegion();
        board.getRegions().get(14).markRegion();
        board.getRegions().get(17).markRegion();
        board.getRegions().get(19).markRegion();
        board.getRegions().get(23).markRegion();
        board.getRegions().get(24).markRegion();
        board.getRegions().get(25).markRegion();
        updateLabels();
    }
}
