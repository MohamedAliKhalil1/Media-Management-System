package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import persistence.SaveLoadJOS;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class LoadJosListener implements InputEventListener {

    private MediaVerwalter crud;

    public LoadJosListener(MediaVerwalter crud) {
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException, ClassNotFoundException {
        this.crud.loadJOS("./Glogik.ser");
    }
}
