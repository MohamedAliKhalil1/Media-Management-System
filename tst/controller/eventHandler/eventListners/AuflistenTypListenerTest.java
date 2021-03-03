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

public class AuflistenTypListenerTest {

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
        this.memoryManager = new MemoryManager(new BigDecimal(400));
        this.model = new MediaVerwalter(producersManager, memoryManager);
        this.view = new View();
        this.controller = new MVcontroller(view, model);
        this.controller.setViewAnzeigen(true);
    }

    @Test
    <T extends MediaContentUploadable> void NACH_TYP_LISTEN_TEST() {
        assertTrue(this.model.auflisten().size() == 0);
        this.controller.setCurrentMode(":c");
        this.controller.start("LicensedAudioVideo Mohamed , 8000 600 DCT 1400 900 44100 EdBangerRecords");
        this.controller.start("LicensedAudioVideo Ali , 8000 600 DCT 1400 900 44100 EdBangerRecords");
        this.controller.start("LicensedAudio Ali , 8000 600 DCT 44100 EdBangerRecord");
        assertTrue(this.model.auflisten().size() == 3);

        this.controller.setCurrentMode(":r");
        this.controller.start("content LicensedAudioVideo");
        assertTrue(this.view.getResult().size() == 2);

        this.controller.start("content LicensedAudio");
        assertTrue(this.view.getResult().size() == 1);
    }
}
