package controller.eventHandler.event;

public class InputEvent extends EventObject {
    private Object obj;

    public InputEvent(Object source, Object obj) {
        super(source);
        this.obj = obj;
    }

    public Object getObjekt() {
        return this.obj;
    }
}