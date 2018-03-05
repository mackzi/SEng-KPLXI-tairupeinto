package eva.selection;

import java.util.ArrayList;
import base.Board;

public interface ISelection {
    ArrayList<Board> doSelection(ArrayList<Board> Boards);
}