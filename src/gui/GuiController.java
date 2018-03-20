package gui;

import backtrack.BacktrackHandler;
import base.Board;
import base.Cell;
import base.Region;
import config.Configuration;
import eva.EvaHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable{

    private Thread evaThread, backtrackThread;
    private String executionType;

    @FXML
    RadioButton eva, bt;

    @FXML
    Button runButton, stopButton;

    @FXML
    GridPane gridPane;

    @FXML
    Label labelFitness, labelGeneration;

    @FXML
    ChoiceBox<String> cBoxSelection, cBoxCrossover, cBoxMutation;

    @FXML
    Label labelRow0, labelRow1, labelRow2, labelRow3, labelRow4, labelRow5, labelRow6, labelRow7, labelRow8;

    @FXML
    Label labelCol0, labelCol1, labelCol2, labelCol3, labelCol4, labelCol5, labelCol6, labelCol7, labelCol8;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateBoard(new Board());
        cBoxSelection.getItems().addAll("Tournament", "RouletteWheel");
        cBoxSelection.setValue("Tournament");
        cBoxCrossover.getItems().addAll("OnePoint", "TwoPoint", "K-Point", "Uniform");
        cBoxCrossover.setValue("OnePoint");
        cBoxMutation.getItems().addAll("Displacement", "Exchange", "Insertion", "Inversion", "Scramble");
        cBoxMutation.setValue("Displacement");
    }

    public void showBestFitness(int fitness) {
        labelFitness.setText("Fitness: " + fitness);
    }

    public void showCurrentGeneration(int generation) {
        labelGeneration.setText("Generation: " + generation);
    }

    public void updateBoard(Board board){
        gridPane.getChildren().clear();
        for (Region r: board.getRegions()) {
            for (Cell c: r.getCells()) {
                gridPane.add(c, c.getCols(), c.getRows());
            }
        }
        updateLabels(board);
    }

    private void updateLabels(Board board){
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
            else
                rowLabels[i].setTextFill(Color.RED);
            if(colValues[i] == colSolution[i])
                colLabels[i].setTextFill(Color.GREEN);
            else
                colLabels[i].setTextFill(Color.RED);
        }
    }

    @FXML
    public void execute() {
        runButton.setDisable(true);
        //startEva();
        startBacktrack();
    }

    @FXML
    public void stop() {
        runButton.setDisable(false);

        updateBoard(new Board());

        if(executionType.equals("eva")){
            evaThread.interrupt();
            evaThread.stop();
        }
        if(executionType.equals("bt")){
            backtrackThread.interrupt();
            backtrackThread.stop();
        }


    }

    private void startEva(){
        executionType = "eva";
        EvaHandler eva = new EvaHandler(this);
        evaThread = new Thread(eva);
        evaThread.start();
    }

    private void startBacktrack() {
        executionType = "bt";
        BacktrackHandler backtrack = new BacktrackHandler(this);
        backtrackThread = new Thread(backtrack);
        backtrackThread.start();
    }
}
