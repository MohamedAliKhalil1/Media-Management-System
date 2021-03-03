package simulations.simulation3;

import simulations.ILoeschen;
import simulations.SimLogik;

public class Sim3TaskLoeschen implements Runnable{
    private ILoeschen logik;

    public Sim3TaskLoeschen(ILoeschen simLogik){
        this.logik = simLogik;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getId() + "löschen");
            try {
                this.logik.deleteMedia();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
