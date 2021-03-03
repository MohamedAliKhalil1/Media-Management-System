package controller.eventHandler.eventListners;

import Observers.TagsObserver;
import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class AddTagBeobachterListener implements InputEventListener {
    private MediaVerwalter crud;

    public AddTagBeobachterListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        TagsObserver crudObserver = (TagsObserver) event.getObjekt();
        crudObserver.setCRUD(this.crud);
        this.crud.addPropertyChangeListener(crudObserver);
    }
}
