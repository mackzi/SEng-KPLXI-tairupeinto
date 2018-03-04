import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private Board board;

    @FXML
    Button runButton;

    @FXML
    GridPane gridPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board = new Board();
        for (Region r: board.getRegions()) {
            for (Cell c: r.getCells()) {
                gridPane.add(c, c.getCols(), c.getRows());
            }
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
    }
}
