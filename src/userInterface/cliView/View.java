package userInterface.cliView;

import Observers.TagsObserver;
import Observers.MemoryObserver;
import controller.eventHandler.event.InputEvent;
import controller.Command;

import java.util.*;

public class View {
    private String modus;
    private String input;
    private Scanner s;
    private LinkedList<TagsObserver> crudObservers;
    private LinkedList<MemoryObserver> memoryObservers;
    private List result;


    public View() {
        this.modus = null;
        this.input = null;
        this.crudObservers = new LinkedList<>();
        this.memoryObservers = new LinkedList<>();
        this.s = new Scanner(System.in);
    }

    public void setModus(String modus) {
        this.modus = modus;
    }
    private Command parseCommand() {
        String commandArray[] = this.input.split(" ");
        Command commandObject = null;
        int commandSize = commandArray.length;
        switch (this.modus) {
            case ":c" :
                if (commandSize == 1)
                    commandObject = new Command(new InputEvent(this, commandArray), "ProduzentEinfügenListener");
                else if (commandSize == 9){
                    if (commandArray[0].equalsIgnoreCase("InteractiveVideo"))
                        commandObject = new Command(new InputEvent(this, commandArray), "InteractiveVideoEinfuegenListner");
                    else if (commandArray[0].equalsIgnoreCase("LicensedVideo"))
                        commandObject = new Command(new InputEvent(this, commandArray), "LicensedVideoEinfügenListner");
                } else if (commandSize == 10) {
                    if (commandArray[0].equalsIgnoreCase("LicensedAudioVideo"))
                        commandObject = new Command(new InputEvent(this, commandArray),"AudioVideoEinfuegenListner");
                } else if (commandSize == 8) {
                    if (commandArray[0].equalsIgnoreCase("LicensedAudio"))
                        commandObject = new Command(new InputEvent(this, commandArray),"LicensedAudioEinfügenListner");
                }
                //TODO
                break;

            case ":r" : //Anzeigen
                if (commandSize == 1 && commandArray[0].equalsIgnoreCase("uploader"))
                    commandObject = new Command(new InputEvent(this, commandArray), "ProduzentenAnzeigListener");
                else if (commandSize == 2) {
                    if (commandArray[0].equalsIgnoreCase("content"))
                        commandObject = new Command(new InputEvent(this, commandArray), "AuflistenTypListener");
                    else if (commandArray[0].equalsIgnoreCase("tag")){
                        if (commandArray[1].equalsIgnoreCase("i"))
                            commandObject = new Command(new InputEvent(this, commandArray), "VorhandeneTagsListener");
                        else if (commandArray[1].equalsIgnoreCase("e"))
                            commandObject = new Command(new InputEvent(this, commandArray), "NichtVorhandenTagsListner");
                    }
                }
                break;

            case ":u" : //Access count
                if (commandSize == 1)
                    commandObject = new Command(new InputEvent(this, commandArray), "AbrufenListener");
                break;

            case ":d" : //delete
                if (commandSize == 1)
                    commandObject = new Command(new InputEvent(this, commandArray), "LöschenListener");
                break;

            case ":p" : //presistance
                if (commandSize == 1){
                    if (commandArray[0].equalsIgnoreCase("saveJOS"))
                        commandObject = new Command(new InputEvent(this, commandArray), "SaveJosListener");
                    else if (commandArray[0].equalsIgnoreCase("loadJOS"))
                        commandObject = new Command(new InputEvent(this, commandArray), "LoadJosListener");
                    else if (commandArray[0].equalsIgnoreCase("saveJBP"))
                        commandObject = new Command(new InputEvent(this, commandArray), "SaveJbpListener");
                    else if (commandArray[0].equalsIgnoreCase("loadJBP"))
                        commandObject = new Command(new InputEvent(this, commandArray), "LoadJbpListener");
                } else if (commandSize == 2){
                    if (commandArray[0].equalsIgnoreCase("save"))
                        commandObject = new Command(new InputEvent(this, commandArray), "SaveRandomMediaListener");
                    else if (commandArray[0].equalsIgnoreCase("load"))
                        commandObject = new Command(new InputEvent(this, commandArray), "LoadRandomMediaListener");
                }
                break;

            case ":config" : //add remove listner and observer
                if (commandSize == 2) {
                    if (this.isTagsObserver(commandArray[1])) {
                        if (commandArray[0].equalsIgnoreCase("add")) {
                            TagsObserver tagsObserver = new TagsObserver();
                            this.crudObservers.add(tagsObserver);
                            commandObject = new Command(new InputEvent(this, tagsObserver), "AddTagBeobachterListener");
                        } else if (commandArray[0].equalsIgnoreCase("remove") && this.crudObservers.size() > 0){
                            commandObject = new Command(new InputEvent(this, this.crudObservers.get(0)), "RemoveTagBeobachterListener");
                            this.crudObservers.remove(0);
                        } else
                            return null;
                    } else if (this.isMemoryObserver(commandArray[1])) {
                        if (commandArray[0].equalsIgnoreCase("add")) {
                            MemoryObserver memoryObserver = new MemoryObserver();
                            this.memoryObservers.add(memoryObserver);
                            commandObject = new Command(new InputEvent(this, memoryObserver), "AddMemoryBoebachterListener");
                        } else if (commandArray[0].equals("remove") && this.memoryObservers.size() > 0){
                            commandObject = new Command(new InputEvent(this, this.memoryObservers.get(0)), "RemoveMemoryBoebachterListener");
                            this.memoryObservers.remove(0);
                        }
                    } else if (commandArray[0].equalsIgnoreCase("add")) {
                        commandObject = new Command(new InputEvent(this, commandArray[1]), "add");
                    } else if (commandArray[0].equalsIgnoreCase("remove")) {
                        commandObject = new Command(new InputEvent(this, commandArray[1]), "remove");
                    }
                }
                break;

            default:
                commandObject = null;
                break;
        }
        return commandObject;
    }
    public String readInputFromUser(){
        System.out.println("current mode : " + this.modus);
        System.out.println("Enter text:");
        return this.s.nextLine();
    }

    public Command excuteCommand(String input){
        this.input = input;
        return this.parseCommand();
    }
    public boolean isModus(String modus){
        if (modus == null)
            return false;
        String s = new String("crdup");
        if(modus.split(" ").length > 1 || !(modus.charAt(0) ==':'))
            return false;
        else{
            for (char c : s.toCharArray()){
                if (c == modus.charAt(1))
                    return true;
            }
            return false;
        }
    }
    public boolean hasModus(String modus){
        if (this.modus == null)
            return false;
        return this.modus.equals(modus);
    }
    public boolean hasModus(){
        return this.modus != null;
    }
    public void showErrorInput(){
        System.out.println("Please enter a valid input or the correct mode !!");
    }

    private boolean isTagsObserver(String klassenname){
        return klassenname.equalsIgnoreCase("tagsObserver");
    }
    private boolean isMemoryObserver(String klassenname){
        return klassenname.equalsIgnoreCase("memoryObserver");
    }

    public String getModus() {
        return modus;
    }

    public List getResult() {
        return result;
    }

    public List show(List toShow){
        this.result = toShow;
       for (Object obj : result)
           System.out.println(obj.toString());
       return this.result;
    }

    public void show(String toShow){
        System.out.println(toShow);
    }
}
