package data.mediaDB.implementation;

import java.io.Serializable;

public class VideoBeschreibung implements Serializable {
    private int width;
    private int height;
    private String encoding;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;

    public VideoBeschreibung() {
    }

    public VideoBeschreibung(int width, int height, String encoding, MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung) {
        this.width = width;
        this.height = height;
        this.encoding = encoding;
        this.mediaContentUploadableBeschreibung = mediaContentUploadableBeschreibung;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public MediaContentUploadableBeschreibung getMediaContentUploadableBeschreibung() {
        return mediaContentUploadableBeschreibung;
    }

    public void setMediaContentUploadableBeschreibung(MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung) {
        this.mediaContentUploadableBeschreibung = mediaContentUploadableBeschreibung;
    }

    @Override
    public String toString() {
        return this.mediaContentUploadableBeschreibung.toString() + " " +
                "Encoding:" + this.encoding + " " +
                "Height:" + this.height + " " +
                "Width:" + this.width;
    }
}
