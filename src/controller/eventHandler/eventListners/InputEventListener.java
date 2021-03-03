package controller.eventHandler.eventListners;

import controller.eventHandler.event.InputEvent;

import java.io.IOException;
import java.util.EventListener;

public interface InputEventListener extends EventListener {

    void onInputEvent(InputEvent event) throws IOException, ClassNotFoundException;
}
