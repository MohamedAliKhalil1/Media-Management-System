package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class ProduzentenAnzeigListener implements InputEventListener {

    private MediaVerwalter CRUD;

    public ProduzentenAnzeigListener(MediaVerwalter CRUD) {
        this.CRUD = CRUD;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.CRUD.getProduzenten();
    }
}
