package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import persistence.dataToSave.MediaProduzentenData;
import persistence.SaveLoadBeanJBP;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class LoadJbpListener implements InputEventListener {

    private MediaVerwalter crud;

    public LoadJbpListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException {
        this.crud.loadJBP("./Glogik.xml");
    }
}
