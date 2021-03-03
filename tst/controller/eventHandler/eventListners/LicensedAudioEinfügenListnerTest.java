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

public class LicensedAudioEinfügenListnerTest {
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
    <T extends MediaContentUploadable> void LICENSED_AUDIO_EINFUEGEN_TEST() {
        assertTrue(this.model.auflisten().size() == 0);
        this.controller.setCurrentMode(":c");
        this.controller.start("LicensedAudio Ali , 8000 600 DCT 44100 EdBangerRecord");
        assertTrue(this.model.auflisten().size() == 1);
    }
}
