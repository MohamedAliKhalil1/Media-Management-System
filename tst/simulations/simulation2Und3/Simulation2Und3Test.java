package simulations.simulation2Und3;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.mediaDB.MediaContentUploadable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.producerManager.ProducersManager;
import simulations.*;
import simulations.simulation3.Sim3Löschen;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Simulation2Und3Test {

    private IEinfuegen iEinfuegen;
    private ILoeschen iLoeschen;
    private simulation sim;
    private MediaVerwalter crud;
    private Abrufen abrufen;

    @BeforeEach
    void init(){
        this.sim = new simulation();
        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(300));
        this.crud = new MediaVerwalter(sim.getProducerList(), memoryManager);
    }

    @Test
    void SIM2_EINFUEGEN_TEST() throws InterruptedException {
        this.iEinfuegen = new SimLogik(this.sim.getMediaList(), this.sim.getProducerList(), this.crud);
        assertEquals(this.crud.auflisten().size() , 0);
        iEinfuegen.addMedia();
        assertEquals(this.crud.auflisten().size() , 1);
    }

    @Test
    <T extends MediaContentUploadable> void SIM2_LOESCHEN_TEST() throws InterruptedException {

        this.iLoeschen = new Sim2Löschen(this.crud);

        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(0)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(1)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(2)));
        assertTrue(this.crud.update((T)this.sim.getMediaList().get(2)));
        assertTrue(((T) this.sim.getMediaList().get(2)).getAC()==1);

        //delete object with max Access count = 1 --> the objekt with index 2 in sim
        this.iLoeschen.deleteMedia();
        assertTrue(this.crud.auflisten().size() == 2);
        assertTrue(((T)this.sim.getMediaList().get(0)).getAC() == 0);
        assertTrue(((T)this.sim.getMediaList().get(1)).getAC() == 0);

    }

    @Test
    <T extends MediaContentUploadable> void SIM3_LOESCHEN_TEST() throws InterruptedException {
        this.iLoeschen = new Sim3Löschen(this.crud);

        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(0)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(1)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(2)));
        for(int i=0; i<3; i++)
            this.iLoeschen.deleteMedia();
        // <= 2 because the random can be = 0, then nothing will be deleted !
        assertTrue(this.crud.auflisten().size() <= 2);
    }

    @Test
    <T extends MediaContentUploadable> void ABRUFEN_TEST() throws InterruptedException {
        this.abrufen = new Abrufen(this.crud);

        // upload 3 objekts
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(0)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(1)));
        assertTrue(this.crud.Hochladen((T)sim.getMediaList().get(2)));

        assertTrue(((T)this.sim.getMediaList().get(0)).getAC() == 0);
        assertTrue(((T)this.sim.getMediaList().get(1)).getAC() == 0);
        assertTrue(((T)this.sim.getMediaList().get(2)).getAC() == 0);

        abrufen.abrufen();

        int counter = 0;
        for (int i=0; i<3; i++){
            if(((T)this.crud.auflisten().get(i)).getAC() > 0){
                counter = 1;
                break;
            }
        }
        assertTrue(counter == 1);
    }
}
