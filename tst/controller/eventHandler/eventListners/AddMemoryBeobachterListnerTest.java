package controller.eventHandler.eventListners;

import data.mediaDB.MediaContentUploadable;
import Observers.MemoryObserver;
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

import static org.mockito.Mockito.*;

public class AddMemoryBeobachterListnerTest {

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
        MemoryObserver memoryObserverMock = mock(MemoryObserver.class);
        AddMemoryBoebachterListener memoryBoebachterListener = new AddMemoryBoebachterListener(this.model);
        doCallRealMethod().when(memoryObserverMock).setMemorySize(any());
        doCallRealMethod().when(memoryObserverMock).setSubject(any());
        doCallRealMethod().when(memoryObserverMock).setMaxTillWarning(any());
        doCallRealMethod().when(memoryObserverMock).setRestSizePercent(any());
        doCallRealMethod().when(memoryObserverMock).propertyChange(any());
        memoryBoebachterListener.onInputEvent(new InputEvent(this,memoryObserverMock));
        this.model.Hochladen(((T)this.zufaelligErzeuger.getMediaList().get(0)));
        verify(memoryObserverMock).propertyChange(any());
    }
}
