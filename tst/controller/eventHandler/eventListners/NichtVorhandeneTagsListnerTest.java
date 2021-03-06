package controller.eventHandler.eventListners;

import controller.MVcontroller;
import data.mediaDB.MediaContentUploadable;
import data.memorymanagement.MemoryManager;
import data.producerManager.ProducersManager;
import geschäftslogik.MediaVerwalter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulations.ZufaelligErzeuger;
import userInterface.cliView.View;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NichtVorhandeneTagsListnerTest {

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
        this.controller.setViewAnzeigen(true);
    }

    @Test
    <T extends MediaContentUploadable> void NICHT_VORHANDENE_TAGS_LISTNER_TEST() {
        assertTrue(this.model.auflisten().size() == 0);
        this.controller.setCurrentMode(":c");
        this.controller.start("LicensedAudioVideo Mohamed News 8000 600 DCT 1400 900 44100 EdBangerRecords");
        assertTrue(this.model.auflisten().size() == 1);
        this.controller.setCurrentMode(":r");
        this.controller.start("tag e");
        assertTrue(this.view.getResult().size() == 3);
    }
}
