package data.mediaDB.implementation;

import java.io.Serializable;

public class InteractiveBeschreibung implements Serializable {
    private String type;
    private ContentBeschreibung contentBeschreibung;

    public InteractiveBeschreibung() {
    }

    public InteractiveBeschreibung(String type, ContentBeschreibung contentBeschreibung) {
        this.type = type;
        this.contentBeschreibung = contentBeschreibung;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ContentBeschreibung getContentBeschreibung() {
        return contentBeschreibung;
    }

    public void setContentBeschreibung(ContentBeschreibung contentBeschreibung) {
        this.contentBeschreibung = contentBeschreibung;
    }

    @Override
    public String toString() {
        return "Type:" + this.type;
    }
}
