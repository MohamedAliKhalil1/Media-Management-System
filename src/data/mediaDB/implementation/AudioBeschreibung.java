package data.mediaDB.implementation;

import java.io.Serializable;

public class AudioBeschreibung implements Serializable {
    private int samplingRate;
    private String encoding;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;

    public AudioBeschreibung() {
    }

    public AudioBeschreibung(int samplingRate, String encoding, MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung) {
        this.samplingRate = samplingRate;
        this.encoding = encoding;
        this.mediaContentUploadableBeschreibung = mediaContentUploadableBeschreibung;
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
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
                "SamplingRate" + this.samplingRate;
    }
}
