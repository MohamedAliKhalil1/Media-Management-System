package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import persistence.SaveLoadJOS;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class SaveJosListener implements InputEventListener {

    private MediaVerwalter crud;

    public SaveJosListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException {
        this.crud.saveJOS("./Glogik.ser");
    }
}
