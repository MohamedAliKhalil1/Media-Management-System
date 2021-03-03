


import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import simulations.*;
import simulations.simulation2Und3.Sim2Löschen;
import simulations.simulation2Und3.Sim2TaskLoeschen;


import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class simulation2 {

    public static void main(String[] args){
        simulation sim2 = new simulation();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(200));
        MediaVerwalter crud = new MediaVerwalter(sim2.getProducerList(), memoryManager);
        IEinfuegen iEinfuegen = new SimLogik(sim2.getMediaList(), sim2.getProducerList(), crud);
        ILoeschen iLoeschen = new Sim2Löschen(crud);
        Abrufen abrufen = new Abrufen(crud);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable sim2TaskEinfuegen = new SimTaskEinfuegen(iEinfuegen);
        Runnable sim2TaskAbrufen = new SimTaskAbrufen(abrufen);
        Runnable sim2TaskLoeschen = new Sim2TaskLoeschen(iLoeschen);


        System.out.println("Einfuegende Thread Execution");
        executorService.execute(sim2TaskEinfuegen);
        System.out.println("Abrufende Thread Execution");
        executorService.execute(sim2TaskAbrufen);
        System.out.println("Loeschende Thread Execution");
        executorService.execute(sim2TaskLoeschen);



        executorService.shutdown();
    }
}
