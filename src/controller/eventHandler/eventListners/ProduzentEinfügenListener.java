package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class ProduzentEinfügenListener implements InputEventListener {
    private MediaVerwalter CRUD;

    public ProduzentEinfügenListener(MediaVerwalter crud){
        this.CRUD = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.CRUD.addProducer(((String[]) event.getObjekt())[0]);
    }
}
