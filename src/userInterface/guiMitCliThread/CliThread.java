package userInterface.guiMitCliThread;

import controller.MVcontroller;

public class CliThread extends Thread{
    private MVcontroller model;

    public CliThread(MVcontroller model) {
        this.model = model;
    }

    @Override
    public void run() {
        this.model.start();
    }
}
