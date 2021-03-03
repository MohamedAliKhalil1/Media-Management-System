package simulations.simulation1;

import simulations.IEinfuegen;


public class Sim1TaskEinfügen implements Runnable{
    private IEinfuegen logik;

    public Sim1TaskEinfügen(IEinfuegen simLogik){
        this.logik = simLogik;
    }

    @Override
    public void run() {
        while (true){
            try {
                this.logik.addMedia();
            } catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
