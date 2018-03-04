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
        int[] rowValues = new int[9];
        int[] colValues = new int[9];

        Label[] rowLabels = new Label[]{labelRow0, labelRow1, labelRow2, labelRow3, labelRow4, labelRow5, labelRow6, labelRow7, labelRow8};
        Label[] colLabels = new Label[]{labelCol0, labelCol1, labelCol2, labelCol3, labelCol4, labelCol5, labelCol6, labelCol7, labelCol8};

        int[] rowSolution = Configuration.ROW_SOLUTION;
        int[] colSolution = Configuration.COL_SOLUTION;

        for (Region r: board.getRegions()) {
            for (Cell c: r.getCells()) {
                if(c.isMarked()){
                    switch (c.getCols()){
                        case 0: colValues[0] += 1; break;
                        case 1: colValues[1] += 1; break;
                        case 2: colValues[2] += 1; break;
                        case 3: colValues[3] += 1; break;
                        case 4: colValues[4] += 1; break;
                        case 5: colValues[5] += 1; break;
                        case 6: colValues[6] += 1; break;
                        case 7: colValues[7] += 1; break;
                        case 8: colValues[8] += 1; break;
                    }
                    switch (c.getRows()){
                        case 0: rowValues[0] += 1; break;
                        case 1: rowValues[1] += 1; break;
                        case 2: rowValues[2] += 1; break;
                        case 3: rowValues[3] += 1; break;
                        case 4: rowValues[4] += 1; break;
                        case 5: rowValues[5] += 1; break;
                        case 6: rowValues[6] += 1; break;
                        case 7: rowValues[7] += 1; break;
                        case 8: rowValues[8] += 1; break;
                    }
                }
            }
        }

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
