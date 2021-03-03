

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import simulations.*;
import simulations.simulation3.Sim3Löschen;
import simulations.simulation3.Sim3TaskLoeschen;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulation3 {
    public static void main(String[] args) {
        simulation sim3 = new simulation();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(200));
        MediaVerwalter crud = new MediaVerwalter(sim3.getProducerList(), memoryManager);
        IEinfuegen iEinfuegen = new SimLogik(sim3.getMediaList(), sim3.getProducerList(), crud);
        ILoeschen iLoeschen = new Sim3Löschen(crud);
        Abrufen abrufen = new Abrufen(crud);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable Sim3TaskEinfuegen = new SimTaskEinfuegen(iEinfuegen);
        Runnable Sim3TaskEinfuegen2 = new SimTaskEinfuegen(iEinfuegen);
        Runnable sim3TaskLoeschen = new Sim3TaskLoeschen(iLoeschen);
        Runnable sim3TaskLoeschen2 = new Sim3TaskLoeschen(iLoeschen);
        Runnable sim3TaskAbrufen = new SimTaskAbrufen(abrufen);

        System.out.println("Einfuegende 1 Thread Execution");
        executorService.execute(Sim3TaskEinfuegen);

        System.out.println("Einfuegende 2 Thread Execution");
        executorService.execute(Sim3TaskEinfuegen2);

        System.out.println("Loeschende 1 Thread Execution");
        executorService.execute(sim3TaskLoeschen);

        System.out.println("Loeschende 2 Thread Execution");
        executorService.execute(sim3TaskLoeschen2);

        System.out.println("Abrufende Thread Execution");
        executorService.execute(sim3TaskAbrufen);

        executorService.shutdown();
    }
}
