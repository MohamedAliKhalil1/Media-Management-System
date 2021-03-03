package controller.eventHandler.eventListners;

import Observers.TagsObserver;
import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class RemoveTagBeobachterListener implements InputEventListener {

    private MediaVerwalter crud;

    public RemoveTagBeobachterListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.crud.removePropertyChangeListener((TagsObserver) event.getObjekt());
    }
}
