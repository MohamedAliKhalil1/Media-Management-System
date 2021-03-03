

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import simulations.*;
import simulations.simulation1.Sim1Einfügen;
import simulations.simulation1.Sim1Loeschen;
import simulations.simulation1.Sim1TaskEinfügen;
import simulations.simulation1.Sim1TaskLoeschen;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulation1 {

    public static void main(String[] args) {
        simulation sim1 = new simulation();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(200));
        MediaVerwalter CRUD = new MediaVerwalter(sim1.getProducerList(), memoryManager);

        IEinfuegen sim1EinfuegenLogik = new Sim1Einfügen(sim1.getMediaList(), sim1.getProducerList(), CRUD);
        Runnable Sim1TaskEinfuegen = new Sim1TaskEinfügen(sim1EinfuegenLogik);

        ILoeschen sim1LoeschenLogik = new Sim1Loeschen(CRUD);
        Runnable Sim1TaskLoeschen = new Sim1TaskLoeschen(sim1LoeschenLogik);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("Einfuegende Thread Execution");
        executorService.execute(Sim1TaskEinfuegen);
        System.out.println("Loeschende Thread Execution");
        executorService.execute(Sim1TaskLoeschen);
        executorService.shutdown();
    }
}
