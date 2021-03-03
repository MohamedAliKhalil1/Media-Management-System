package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;

public class VorhandeneTagsListener implements InputEventListener {

    private MediaVerwalter model;

    public VorhandeneTagsListener(MediaVerwalter model) {
        this.model = model;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException, ClassNotFoundException {
        this.model.vorhandeneTags();
    }
}
