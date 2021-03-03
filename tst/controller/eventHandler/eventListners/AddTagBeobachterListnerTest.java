package controller.eventHandler.eventListners;

import data.mediaDB.MediaContentUploadable;
import Observers.TagsObserver;
import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.producerManager.ProducersManager;
import controller.eventHandler.event.InputEvent;
import controller.MVcontroller;
import userInterface.cliView.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulations.ZufaelligErzeuger;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class AddTagBeobachterListnerTest {

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
    <T extends MediaContentUploadable>void ADD_MEMORY_BEOBACHTER_TEST() {
        TagsObserver tagsObserver = mock(TagsObserver.class);
        AddTagBeobachterListener tagBeobachterListener = new AddTagBeobachterListener(this.model);
        doCallRealMethod().when(tagsObserver).setCRUD(any());
        doCallRealMethod().when(tagsObserver).propertyChange(any());
        tagBeobachterListener.onInputEvent(new InputEvent(this,tagsObserver));
        this.model.Hochladen(((T)this.zufaelligErzeuger.getMediaList().get(0)));
        verify(tagsObserver).propertyChange(any());
    }
}
