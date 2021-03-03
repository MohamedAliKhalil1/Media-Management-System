package data.mediaDB.implementation;

import java.io.Serializable;

public class MediaContentUploadableBeschreibung implements Serializable {
    MediaContentBeschreibung mediaContentBeschreibung;
    UploadableBeschreibung uploadableBeschreibung;

    public MediaContentUploadableBeschreibung() {
    }

    public MediaContentUploadableBeschreibung(MediaContentBeschreibung mediaContentBeschreibung, UploadableBeschreibung uploadableBeschreibung) {
        this.mediaContentBeschreibung = mediaContentBeschreibung;
        this.uploadableBeschreibung = uploadableBeschreibung;
    }

    public MediaContentBeschreibung getMediaContentBeschreibung() {
        return mediaContentBeschreibung;
    }

    public void setMediaContentBeschreibung(MediaContentBeschreibung mediaContentBeschreibung) {
        this.mediaContentBeschreibung = mediaContentBeschreibung;
    }

    public UploadableBeschreibung getUploadableBeschreibung() {
        return uploadableBeschreibung;
    }

    public void setUploadableBeschreibung(UploadableBeschreibung uploadableBeschreibung) {
        this.uploadableBeschreibung = uploadableBeschreibung;
    }

    @Override
    public String toString() {
        return this.uploadableBeschreibung + " " + this.mediaContentBeschreibung;
    }
}
