package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import persistence.RandomAccess;
import data.mediaDB.MediaContentUploadable;
import data.mediaDB.implementation.UploaderBeschreibung;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class LoadRandomMediaListener implements InputEventListener {

    MediaVerwalter crud;

    public LoadRandomMediaListener(MediaVerwalter crud){
        this.crud = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException, ClassNotFoundException {
        this.crud.loadRandomMedia(((String[]) event.getObjekt()) [1], "./RandomData");
    }
}
