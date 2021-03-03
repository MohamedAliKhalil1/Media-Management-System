package data.mediaDB.implementation;

import java.io.Serializable;

public class LicensedBeschreibung implements Serializable {
    private String holder;
    private ContentBeschreibung contentBeschreibung;

    public LicensedBeschreibung() {
    }

    public LicensedBeschreibung(String holder, ContentBeschreibung contentBeschreibung) {
        this.holder = holder;
        this.contentBeschreibung = contentBeschreibung;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public ContentBeschreibung getContentBeschreibung() {
        return contentBeschreibung;
    }

    public void setContentBeschreibung(ContentBeschreibung contentBeschreibung) {
        this.contentBeschreibung = contentBeschreibung;
    }

    @Override
    public String toString() {
        return "Holder:" + this.holder;
    }
}
