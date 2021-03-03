package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.LicensedAudioVideo;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;
import data.mediaDB.implementation.konkreteMedien.LicensedAudioVideoImp;
import controller.eventHandler.event.InputEvent;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AudioVideoEinfuegenListner implements InputEventListener {

    private MediaVerwalter model;
    private String[] command;
    private String[] tags;

    private List tagsList;
    private long bitrate;
    private double laenge;
    private BigDecimal size;
    private String uploaderName;
    private int width;
    private int hoehe;
    private String encoding;
    private String holder;
    private int samplingrate;
    private ContentBeschreibung contentBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;
    private UploadableBeschreibung uploadableBeschreibung;
    private MediaContentBeschreibung mediaContentBeschreibung;
    private VideoBeschreibung videoBeschreibung;
    private AudioBeschreibung audioBeschreibung;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;
    private AudioVideoBeschreibung audioVideoBeschreibung;

    public AudioVideoEinfuegenListner(MediaVerwalter model) {
        this.model = model;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.command = (String[]) event.getObjekt();
        if (!this.command[2].equals(",")){ this.tags = this.command[2].split(","); }
        this.init();
        this.model.Hochladen(this.licensedAudioVideoFactory());
    }

    private LicensedAudioVideo licensedAudioVideoFactory(){

        this.contentBeschreibung = new ContentBeschreibung(null, this.tagsList, 0);
        this.licensedBeschreibung = new LicensedBeschreibung(this.holder, this.contentBeschreibung);
        this.uploadableBeschreibung = new UploadableBeschreibung(new UploaderBeschreibung(this.uploaderName), null);
        this.mediaContentBeschreibung = new MediaContentBeschreibung(this.bitrate, this.laenge, this.size, this.contentBeschreibung);
        this.mediaContentUploadableBeschreibung = new MediaContentUploadableBeschreibung(this.mediaContentBeschreibung, this.uploadableBeschreibung);
        this.videoBeschreibung = new VideoBeschreibung(this.width, this.hoehe, this.encoding, this.mediaContentUploadableBeschreibung);
        this.audioBeschreibung = new AudioBeschreibung(this.samplingrate, this.encoding, this.mediaContentUploadableBeschreibung);
        this.audioVideoBeschreibung = new AudioVideoBeschreibung(this.audioBeschreibung, this.videoBeschreibung);
        return new LicensedAudioVideoImp(this.audioVideoBeschreibung, this.licensedBeschreibung);
    }

    private void init(){
        /*
        LicensedAudioVideo Mohamed , 8000 600 DCT 1400 900 44100 EdBangerRecords
         */
        this.tagsList = Arrays.asList(this.command[2].split(","))
                .stream()
                .map(Tag::valueOf)
                .collect(Collectors.toList());
        this.bitrate = Long.parseLong(this.command[3]);
        this.laenge = Double.parseDouble(this.command[4]);
        this.size = BigDecimal.valueOf(100);
        this.uploaderName = this.command[1];
        this.width = Integer.parseInt(this.command[7]);
        this.hoehe = Integer.parseInt(this.command[6]);
        this.encoding = this.command[5];
        this.samplingrate = Integer.parseInt(this.command[8]);
        this.holder = this.command[9];
    }
}
