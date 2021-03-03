package controller;

import controller.eventHandler.event.InputEvent;


public class Command {
    private InputEvent event;
    private String handlerKey;

    public Command(InputEvent event, String handlerKey) {
        this.event = event;
        this.handlerKey = handlerKey;
    }

    public InputEvent getEvent() {
        return event;
    }

    public String getHandlerKey() {
        return handlerKey;
    }
}
