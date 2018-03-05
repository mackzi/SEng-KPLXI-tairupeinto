import javafx.application.Platform;

public class EvaHandler implements Runnable{
    GuiController guiController;
    private Thread evaThread;

    EvaHandler(GuiController guiController){
        this.guiController = guiController;
    }

    @Override
    public void run() {
        try {
            evaThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(this::execute);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
            Thread.currentThread().interrupt();
            evaThread.interrupt();
        }
    }

    private void execute(){
            Board board = new Board();
            for (int j = 0; j < Configuration.NUMBER_OF_REGIONS; j++) {
                if (Configuration.instance.random.nextBoolean())
                    board.getRegions().get(j).markRegion();
            }
            guiController.updateBoard(board);
            guiController.showCurrentFitness(board.evaluateFitness());
            if(board.evaluateFitness() == 0)
                evaThread.interrupt();
    }


}
