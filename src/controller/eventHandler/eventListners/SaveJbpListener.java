package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import persistence.SaveLoadBeanJBP;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class SaveJbpListener implements InputEventListener {

    private MediaVerwalter crud;

    public SaveJbpListener(MediaVerwalter crud) {
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException {
        this.crud.saveJBP("./Glogik.xml");
    }
}
