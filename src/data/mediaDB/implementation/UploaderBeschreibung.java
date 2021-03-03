package data.mediaDB.implementation;

import java.io.Serializable;

public class UploaderBeschreibung implements Serializable {
    private String name;
    private int numberOfMedia;

    public UploaderBeschreibung() {
    }

    public UploaderBeschreibung(String name) {
        this.name = name;
        this.numberOfMedia = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfMedia() {
        return numberOfMedia;
    }

    public void setNumberOfMedia(int numberOfMedia) {
        this.numberOfMedia = numberOfMedia;
    }

    public String toString() {
        return this.getName() + " uploaded: " + this.getNumberOfMedia();
    }
}
