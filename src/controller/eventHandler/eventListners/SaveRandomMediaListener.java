package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import persistence.RandomAccess;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class SaveRandomMediaListener implements InputEventListener {

    private MediaVerwalter crud;

    public SaveRandomMediaListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException {
        this.crud.saveRandomMedia(((String[]) event.getObjekt()) [1], "./RandomData");
    }
}
