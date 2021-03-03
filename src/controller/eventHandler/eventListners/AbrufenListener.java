package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class AbrufenListener implements InputEventListener {

    private MediaVerwalter crud;

    public AbrufenListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.crud.update(((String[]) event.getObjekt())[0]);
    }
}
