package data.mediaDB.implementation;

import java.io.Serializable;

public class AudioVideoBeschreibung implements Serializable {
    private AudioBeschreibung audioBeschreibung;
    private VideoBeschreibung videoBeschreibung;

    public AudioVideoBeschreibung() {
    }

    public AudioVideoBeschreibung(AudioBeschreibung audioBeschreibung, VideoBeschreibung videoBeschreibung) {
        this.audioBeschreibung = audioBeschreibung;
        this.videoBeschreibung = videoBeschreibung;
    }

    public AudioBeschreibung getAudioBeschreibung() {
        return audioBeschreibung;
    }

    public void setAudioBeschreibung(AudioBeschreibung audioBeschreibung) {
        this.audioBeschreibung = audioBeschreibung;
    }

    public VideoBeschreibung getVideoBeschreibung() {
        return videoBeschreibung;
    }

    public void setVideoBeschreibung(VideoBeschreibung videoBeschreibung) {
        this.videoBeschreibung = videoBeschreibung;
    }

    @Override
    public String toString() {
        return this.videoBeschreibung.toString() + " " + "samplingrate:" + this.audioBeschreibung.getSamplingRate();
    }

}
