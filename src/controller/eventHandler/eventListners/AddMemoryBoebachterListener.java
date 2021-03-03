package controller.eventHandler.eventListners;

import Observers.MemoryObserver;
import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

import java.math.BigDecimal;

public class AddMemoryBoebachterListener implements InputEventListener {

    private MediaVerwalter crud;

    public AddMemoryBoebachterListener(MediaVerwalter crud) {
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        MemoryObserver memoryObserver = (MemoryObserver) event.getObjekt();
        memoryObserver.setSubject(this.crud);
        memoryObserver.setRestSizePercent(new BigDecimal(100));
        memoryObserver.setMemorySize(this.crud.getGesamtSizeOfMemory());
        memoryObserver.setMaxTillWarning(new BigDecimal(90));
        this.crud.addPropertyChangeListener(memoryObserver);
    }
}
