package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class NichtVorhandenTagsListner implements InputEventListener {
    private MediaVerwalter model;

    public NichtVorhandenTagsListner(MediaVerwalter model) {
        this.model = model;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException, ClassNotFoundException {
        this.model.nichtVorhandeneTags();
    }
}
