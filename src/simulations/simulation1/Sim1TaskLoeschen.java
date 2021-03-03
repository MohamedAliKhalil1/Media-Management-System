package simulations.simulation1;

import simulations.ILoeschen;

public class Sim1TaskLoeschen implements Runnable {

    private ILoeschen logik;

    public Sim1TaskLoeschen(ILoeschen simLogik) {
        this.logik = simLogik;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.logik.deleteMedia();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
