package simulations;

public class SimTaskAbrufen implements Runnable{

    private Abrufen logik;
    public SimTaskAbrufen(Abrufen simLogik){
        this.logik = simLogik;
    }

    @Override
    public void run() {
        while (true){
            try {
                this.logik.abrufen();
            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
