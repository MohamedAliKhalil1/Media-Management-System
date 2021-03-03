package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.mediaDB.MediaContentUploadable;
import controller.MVcontroller;
import userInterface.cliView.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.producerManager.ProducersManager;
import simulations.ZufaelligErzeuger;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JOSListnersTest {

    private ProducersManager producersManager;
    private MemoryManager memoryManager;
    private MediaVerwalter model;
    private View view;
    private MVcontroller controller;
    private ZufaelligErzeuger zufaelligErzeuger;

    @BeforeEach
    void init() {
        this.zufaelligErzeuger = new ZufaelligErzeuger();
        this.producersManager = this.zufaelligErzeuger.getProducers();
        this.memoryManager = new MemoryManager(new BigDecimal(300));
        this.model = new MediaVerwalter(producersManager, memoryManager);
        this.view = new View();
        this.controller = new MVcontroller(view, model);
        this.controller.setViewAnzeigen(false);
    }

    @Test
    <T extends MediaContentUploadable> void SAVE_JOS_LISTNER_TEST() {
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(0));
        assertTrue(this.model.auflisten().size() == 1);
        this.controller.setCurrentMode(":p");
        this.controller.start("saveJOS");
        this.model.Hochladen((T)this.zufaelligErzeuger.getMediaList().get(1));
        assertTrue(this.model.auflisten().size() == 2);
        this.controller.setCurrentMode(":p");
        this.controller.start("loadJOS");
        assertTrue(this.model.auflisten().size() == 1);
    }
}
