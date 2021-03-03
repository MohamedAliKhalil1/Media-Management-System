package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class AuflistenTypListener implements InputEventListener {

    private MediaVerwalter CRUD;

    public AuflistenTypListener(MediaVerwalter crud){
        this.CRUD = crud;
    }
    @Override
    public void onInputEvent(InputEvent event) {
        this.CRUD.auflisten(((String [])(event.getObjekt()))[1]);
    }

}
