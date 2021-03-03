package simulations.simulation2Und3;

import simulations.ILoeschen;

public class Sim2TaskLoeschen implements Runnable{
    private ILoeschen logik;

    public Sim2TaskLoeschen(ILoeschen simLogik){
        this.logik = simLogik;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getId() + "LLLLLLLLLLLLLLLLLLLLLLLL");
            try {
                this.logik.deleteMedia();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
