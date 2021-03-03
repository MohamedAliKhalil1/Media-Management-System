package controller.eventHandler;

import controller.eventHandler.event.InputEvent;
import controller.eventHandler.eventListners.InputEventListener;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InputEventHandler {

    private List<InputEventListener> listenerList;

    public InputEventHandler() {
        this.listenerList = new LinkedList<>();
    }

    public void add(InputEventListener listener) {
        this.listenerList.add(listener);
    }

    public void removeLast() {
        if (this.listenerList.size() > 0)
            this.listenerList.remove(this.listenerList.size()-1);
    }

    public void handle(InputEvent event) throws IOException, ClassNotFoundException {
        for (InputEventListener listener : this.listenerList)
            listener.onInputEvent(event);
    }

}
