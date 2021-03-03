package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.mediaDB.MediaContentUploadable;
import controller.MVcontroller;
import userInterface.cliView.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.producerManager.ProducersManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProduzentenAnzeigListnerTest {

    private ProducersManager producersManager;
    private MemoryManager memoryManager;
    private MediaVerwalter model;
    private View view;
    private MVcontroller controller;;

    @BeforeEach
    void init() {
        this.producersManager = new ProducersManager();
        this.memoryManager = new MemoryManager(new BigDecimal(300));
        this.model = new MediaVerwalter(producersManager, memoryManager);
        this.view = new View();
        this.controller = new MVcontroller(view, model);
        this.controller.setViewAnzeigen(true);
    }

    @Test
    <T extends MediaContentUploadable> void PRODUZENT_ANZEIGEN_LISTNER_TEST() {
        assertTrue(this.model.getProduzenten().size() == 0);
        this.controller.setCurrentMode(":c");
        this.controller.start("Produzent1");
        this.controller.start("Produzent2");
        this.controller.start("Produzent3");
        assertTrue(this.producersManager.getProducerList().size() == 3);

        this.controller.setCurrentMode(":r");
        this.controller.start("uploader");
        assertTrue(this.view.getResult().size() == 3);
    }
}
