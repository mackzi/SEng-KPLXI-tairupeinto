public class EvaHandler {
    Controller controller;

    EvaHandler(Controller controller){
        this.controller = controller;
    }

    public void execute(){
        //for(int i = 0; i<100; i++) {
            Board board = new Board();
            for (int j = 0; j < Configuration.NUMBER_OF_REGIONS; j++) {
                if (Configuration.instance.random.nextBoolean())
                    board.getRegions().get(j).markRegion();
            }
            controller.updateBoard(board);
            controller.updateLabels(board);
            /*
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
        //}
    }
}
