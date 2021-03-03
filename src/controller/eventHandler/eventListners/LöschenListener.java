package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import controller.eventHandler.event.InputEvent;

public class LöschenListener implements InputEventListener {
    private MediaVerwalter CRUD;
    private String[] command;

    public LöschenListener(MediaVerwalter crud){
        this.CRUD = crud;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.command = (String[]) event.getObjekt();
        if (this.CRUD.containProducer(this.command[0])){
            this.CRUD.removeProducer(this.command[0]);
            System.out.println("The producer : " + this.command[0] + " had been deleted successfully");
        }else {
            if (this.CRUD.löschen(this.command[0])){
                System.out.println("The Media has been deleted successfully");
            }else{
                System.out.println("the is no media nor Producer with this name to delete");
            }
        }

    }
}
