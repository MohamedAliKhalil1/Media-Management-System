package simulations.simulation1;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.mediaDB.MediaContentUploadable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulations.IEinfuegen;
import simulations.ILoeschen;
import simulations.simulation;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Simulation1Test {

    private IEinfuegen iEinfuegen;
    private ILoeschen iLoeschen;
    private simulation sim;
    private MediaVerwalter crud;

    @BeforeEach
    void init(){
        this.sim = new simulation();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(300));
        this.crud = new MediaVerwalter(sim.getProducerList(), memoryManager);
    }

    @Test
    void SIM1_EINFUEGEN_TEST() throws InterruptedException {
        iEinfuegen = new Sim1Einfügen(this.sim.getMediaList(), this.sim.getProducerList(), this.crud);
        assertEquals(this.crud.auflisten().size() , 0);
        iEinfuegen.addMedia();
        assertEquals(this.crud.auflisten().size() , 1);
    }

    @Test
    <T extends MediaContentUploadable> void SIM1_LOESCHEN_TEST() throws InterruptedException {
        this.iLoeschen = new Sim1Loeschen(this.crud);

        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(0)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(1)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(2)));

        this.iLoeschen.deleteMedia();
        assertTrue(this.crud.auflisten().size() == 2);
    }

}
