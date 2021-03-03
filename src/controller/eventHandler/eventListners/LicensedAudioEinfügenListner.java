package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.LicensedAudio;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;
import data.mediaDB.implementation.konkreteMedien.LicensedAudioImp;
import controller.eventHandler.event.InputEvent;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LicensedAudioEinfügenListner implements InputEventListener {

    private MediaVerwalter CRUD;
    private String[] command;
    private String[] tags;

    private List tagsList;
    private long bitrate;
    private double laenge;
    private BigDecimal size;
    private String uploaderName;
    private int samplingrate;
    private String encoding;
    private String holder;
    private ContentBeschreibung contentBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;
    private UploadableBeschreibung uploadableBeschreibung;
    private MediaContentBeschreibung mediaContentBeschreibung;
    private AudioBeschreibung audioBeschreibung;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;

    public LicensedAudioEinfügenListner(MediaVerwalter CRUD) {
        this.CRUD = CRUD;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.command = (String[]) event.getObjekt();
        if (!this.command[2].equals(",")){ this.tags = this.command[2].split(","); }
        this.init();
        System.out.println("Hochladen Status : " + this.CRUD.Hochladen(this.LicensedAudioFactory()) + " -> LicensedAudio");

    }

    private LicensedAudio LicensedAudioFactory(){

        this.contentBeschreibung = new ContentBeschreibung(null, this.tagsList, 0);
        this.licensedBeschreibung = new LicensedBeschreibung(this.holder, this.contentBeschreibung);
        this.uploadableBeschreibung = new UploadableBeschreibung(new UploaderBeschreibung(this.uploaderName), null);
        this.mediaContentBeschreibung = new MediaContentBeschreibung(this.bitrate, this.laenge, this.size, contentBeschreibung);
        this.mediaContentUploadableBeschreibung = new MediaContentUploadableBeschreibung(this.mediaContentBeschreibung, this.uploadableBeschreibung);
        this.audioBeschreibung = new AudioBeschreibung(this.samplingrate, this.encoding, this.mediaContentUploadableBeschreibung);
        return new LicensedAudioImp(this.audioBeschreibung, this.licensedBeschreibung);
    }

    private void init(){
        /*
        LicensedAudio Ali , 8000 600 DCT 44100 EdBangerRecords
        [Media-Typ] [Produzentenname] [kommaseparierte Tags, einzelnes Komma für keine] [Bitrate] [Länge]
        [[Encoding] [Samplingrate] [Lizenzsgeber]
         */
        this.tagsList = Arrays.asList(this.command[2].split(","))
                .stream()
                .map(Tag::valueOf)
                .collect(Collectors.toList());
        this.bitrate = Long.parseLong(this.command[3]);
        this.laenge = Double.parseDouble(this.command[4]);
        this.size = BigDecimal.valueOf(100);
        this.uploaderName = this.command[1];
        this.samplingrate = Integer.parseInt(this.command[6]);
        this.encoding = this.command[5];
        this.holder = this.command[7];
    }
}
