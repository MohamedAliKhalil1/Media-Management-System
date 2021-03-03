package userInterface.cliView.display;

public class Display {
    private String einleitung;
    private String config;
    private String observers;

    public Display() {
        this.einleitung = "\n:c Wechsel in den Einfügemodus\n" +
                ":d Wechsel in den Löschmodus\n" +
                ":r Wechsel in den Anzeigemodus\n" +
                ":u Wechsel in den Änderungsmodus\n" +
                ":p Wechsel in den Persistenzmodus\n";
        this.config = ":config Wechsel in den Konfigurationsmodus\n";
        this.observers = "BeobachterNamen = (MemoryObserver) , (TagsObserver)\n";
    }

    public String getEinleitung() {
        return einleitung;
    }

    public String getConfig() {
        return config;
    }

    public String getObservers() {
        return observers;
    }

    @Override
    public String toString() {
        return this.einleitung + this.config + this.observers;
    }
}
