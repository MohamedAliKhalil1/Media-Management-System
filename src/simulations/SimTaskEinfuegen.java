package simulations;

public class SimTaskEinfuegen implements Runnable{

    private IEinfuegen logik;

    public SimTaskEinfuegen(IEinfuegen simLogik){
        this.logik = simLogik;
    }

    @Override
    public void run() {
        while (true){
        System.out.println(Thread.currentThread().getId() + "EEEEEEEEEEEEEEEEEEEEEEEEE");
            try {
                this.logik.addMedia();
            } catch (Exception e){
                e.getStackTrace();
             }
        }
    }
}
