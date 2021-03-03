package controller.eventHandler.eventListners;

import Observers.MemoryObserver;
import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class RemoveMemoryBoebachterListener implements InputEventListener {

    private MediaVerwalter crud;

    public RemoveMemoryBoebachterListener(MediaVerwalter crud) {
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.crud.removePropertyChangeListener((MemoryObserver) event.getObjekt());
    }
}
