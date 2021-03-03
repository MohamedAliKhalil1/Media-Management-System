package controller;

import controller.eventHandler.InputEventHandler;
import userInterface.cliView.display.Display;
import controller.eventHandler.eventListners.*;
import geschäftslogik.MediaVerwalter;

import userInterface.cliView.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class MVcontroller extends AbstractController{
    private View view;
    private MediaVerwalter model;
    private PropertyChangeSupport propertyChangeSupport;
    private HashMap<String, InputEventHandler> handlerHashMap;
    Display display;

    private boolean viewAnzeigen;

    public MVcontroller(View view, MediaVerwalter model){
        this.view = view;
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.handlerHashMap = new HashMap<>();
        this.display = new Display();
        this.init();
    }

    private void init(){
        this.viewAnzeigen = true;
        this.addHandlerWithKey("InteractiveVideoEinfuegenListner", new InteractiveVideoEinfuegenListner(this.model));
        this.addHandlerWithKey("LicensedVideoEinfügenListner", new LicensedVideoEinfügenListner(this.model));
        this.addHandlerWithKey("AudioVideoEinfuegenListner", new AudioVideoEinfuegenListner(this.model));
        this.addHandlerWithKey("LicensedAudioEinfügenListner", new LicensedAudioEinfügenListner(this.model));
        this.addHandlerWithKey("ProduzentEinfügenListener", new ProduzentEinfügenListener(this.model));
        this.addHandlerWithKey("AuflistenTypListener", new AuflistenTypListener(this.model));
        this.addHandlerWithKey("ProduzentenAnzeigListener", new ProduzentenAnzeigListener(this.model));
        this.addHandlerWithKey("AbrufenListener", new AbrufenListener(this.model));
        this.addHandlerWithKey("LöschenListener", new LöschenListener(this.model));
        this.addHandlerWithKey("SaveJosListener", new SaveJosListener(this.model));
        this.addHandlerWithKey("LoadJosListener", new LoadJosListener(this.model));
        this.addHandlerWithKey("SaveJbpListener", new SaveJbpListener(this.model));
        this.addHandlerWithKey("LoadJbpListener", new LoadJbpListener(this.model));
        this.addHandlerWithKey("SaveRandomMediaListener", new SaveRandomMediaListener(this.model));
        this.addHandlerWithKey("LoadRandomMediaListener", new LoadRandomMediaListener(this.model));
        this.addHandlerWithKey("AddTagBeobachterListener", new AddTagBeobachterListener(this.model));
        this.addHandlerWithKey("AddMemoryBoebachterListener", new AddMemoryBoebachterListener(this.model));
        this.addHandlerWithKey("RemoveTagBeobachterListener", new RemoveTagBeobachterListener(this.model));
        this.addHandlerWithKey("RemoveMemoryBoebachterListener", new RemoveMemoryBoebachterListener(this.model));
        this.addHandlerWithKey("VorhandeneTagsListener", new VorhandeneTagsListener(this.model));
        this.addHandlerWithKey("NichtVorhandenTagsListner", new NichtVorhandenTagsListner(this.model));
    }

    public void addHandlerWithKey(String key, InputEventListener inputEventListener) {
        InputEventHandler handler = new InputEventHandler();
        handler.add(inputEventListener);
        this.handlerHashMap.put(key, handler);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) { this.propertyChangeSupport.addPropertyChangeListener(pcl); }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {this.propertyChangeSupport.removePropertyChangeListener(pcl);}

    public void setCurrentMode(String mode) {this.view.setModus(mode);}
    public String getCurrentMode() {return this.view.getModus();}
    public BigDecimal getGesamtSizeOfMemory(){
        return this.model.getGesamtSizeOfMemory();
    }
    public BigDecimal getRestSizeOfMemory(){
        return this.model.getRestSizeOfMemory();
    }
    public void setViewAnzeigen(boolean viewAnzeigen) {
        this.viewAnzeigen = viewAnzeigen;
    }


    public void start(){
        do {
            String input = this.view.readInputFromUser();
            this.start(input);
        } while(true);
    }
    public void start(String input){
        Command command;
        if (this.view.isModus(input)) {
            this.view.setModus(input);
            checkViewAnzeigen();
            this.propertyChangeSupport.firePropertyChange("current mode:"+ this.view.getModus(), Thread.currentThread().getName(), null);
        } else if (this.view.hasModus() && input != null){
            command = this.view.excuteCommand(input);
            if (command != null){
                if (!this.checkAddRemoveListner(command)) {
                    try {
                        this.handlerHashMap.get(command.getHandlerKey()).handle(command.getEvent());
                    } catch (IOException | ClassNotFoundException e) {
                        this.propertyChangeSupport.firePropertyChange("Failed" + e.getMessage(), Thread.currentThread().getName(), null);
                    }
                    if (this.view.getModus().equals(":config")) {
                        this.propertyChangeSupport.firePropertyChange("success", Thread.currentThread().getName(), null);
                    }
                } else {
                    this.view.show(command.getHandlerKey() + " -> ok");
                }
            } else {
                this.view.showErrorInput();
                this.checkViewAnzeigen();
                this.propertyChangeSupport.firePropertyChange("Failed input", Thread.currentThread().getName(), null);
            }
        } else {
            this.view.showErrorInput();
            this.checkViewAnzeigen();
            this.propertyChangeSupport.firePropertyChange("Failed input", Thread.currentThread().getName(), null);
        }

    }

    public boolean isMode(String mode){return this.view.isModus(mode);}
    public boolean hasMode(String mode) {return this.view.hasModus(mode);}
    public void nachAbrufSortieren(){
        this.model.nachAccessCountSortieren();
    }
    public void nachAdressSortieren(){
        this.model.nachAddressSortieren();
    }
    public void nachProduzentenSortieren(){
        this.model.nachProduzentSortieren();
    }
    public List getProducers() {
        return this.model.getProducersManager().getProducersList();
    }
    public void update(String addr){
        this.model.update(addr);
    }
    private boolean checkAddRemoveListner(Command command) {
        if (command.getHandlerKey().equalsIgnoreCase("add")){
            try {
                Class<?> clazz = Class.forName("controller.eventHandler.eventListners.listeners."+ command.getEvent().getObjekt());
                Constructor<?> ctor = clazz.getConstructor(MediaVerwalter.class);
                InputEventListener listener = (InputEventListener) ctor.newInstance(new Object[] { this.model });
                this.handlerHashMap.get(command.getEvent().getObjekt()).add(listener);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                this.view.show("the class name is wrong");
            }
            return true;
        } else if (command.getHandlerKey().equalsIgnoreCase("remove")) {
            this.handlerHashMap.get(command.getEvent().getObjekt()).removeLast();
            return true;
        } else
            return false;
    }
    private void checkViewAnzeigen() {
        if (this.viewAnzeigen)
            this.view.show(display.toString());
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.view.show(evt.getPropertyName());
        if (this.viewAnzeigen) {
            this.view.show((List) evt.getNewValue());
            this.view.show(display.toString());
        }
        this.propertyChangeSupport.firePropertyChange(evt.getPropertyName(), Thread.currentThread().getName(), evt.getNewValue());
    }
}
