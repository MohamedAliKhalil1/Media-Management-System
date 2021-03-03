package controller.eventHandler.eventListners;

import controller.eventHandler.event.InputEvent;

public class InputEventListenerExit implements InputEventListener {

    @Override
    public void onInputEvent(InputEvent event) {
        if (null != event.getObjekt() && ((String)event.getObjekt()).equalsIgnoreCase("exit")) System.exit(0);
    }
}
