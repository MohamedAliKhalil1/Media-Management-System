package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.mediaDB.MediaContentUploadable;
import data.mediaDB.implementation.UploaderBeschreibung;
import controller.MVcontroller;
import userInterface.cliView.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.producerManager.ProducersManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProduzentEinfügenListnerTeas {

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
        this.controller.setViewAnzeigen(false);
    }

    @Test
    <T extends MediaContentUploadable> void PRODUZENT_EINFUEGEN_LESTNER_TEST() {
        assertTrue(this.model.getProduzenten().size() == 0);
        this.controller.setCurrentMode(":c");
        this.controller.start("Produzent1");
        assertTrue(this.model.getProduzenten().size() == 1);
        assertTrue(((UploaderBeschreibung)(this.model.getProduzenten().get(0))).getName().equals("Produzent1"));
    }
}
