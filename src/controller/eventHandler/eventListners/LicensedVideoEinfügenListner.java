package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.LicensedVideo;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;
import data.mediaDB.implementation.konkreteMedien.LicensedVideoImp;
import controller.eventHandler.event.InputEvent;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LicensedVideoEinfügenListner implements InputEventListener {
    private MediaVerwalter model;
    private String[] command;
    /*
        Muster Input:
        [Media-Typ] [Produzentenname] [kommaseparierteTags, einzelnes Komma für keine]
        [Bitrate] [Länge][[Video-Encoding] [Höhe] [Breite] [Interaktionstyp]]
    */
    private List<Tag> tagsList;
    private long bitrate;
    private double laenge;
    private BigDecimal size;
    private String uploaderName;
    private int width;
    private int hoehe;
    private String encoding;
    private String holder;
    private ContentBeschreibung contentBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;
    private UploadableBeschreibung uploadableBeschreibung;
    private MediaContentBeschreibung mediaContentBeschreibung;
    private VideoBeschreibung videoBeschreibung;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;

    public LicensedVideoEinfügenListner(MediaVerwalter model) {
        this.model = model;
    }

    @Override
    public void onInputEvent(InputEvent event) throws IOException, ClassNotFoundException {
        this.command = (String[]) event.getObjekt();
        this.tagsList = Arrays.asList(this.command[2].split(","))
                .stream()
                .map(Tag::valueOf)
                .collect(Collectors.toList());
        this.init();
        this.model.Hochladen(this.LicensedVideoFactory());
    }
    private void init(){
        this.bitrate = Long.parseLong(this.command[3]);
        this.laenge = Double.parseDouble(this.command[4]);
        this.size = BigDecimal.valueOf(100); //TODO fest denn es ist nicht in Muster Angaben eingegeben
        this.uploaderName = this.command[1];
        this.width = Integer.parseInt(this.command[7]);
        this.hoehe = Integer.parseInt(this.command[6]);
        this.encoding = this.command[5];
        this.holder = this.command[8];
    }

    private LicensedVideo LicensedVideoFactory(){
        this.contentBeschreibung = new ContentBeschreibung(null, this.tagsList, 0);
        this.licensedBeschreibung = new LicensedBeschreibung(this.holder, this.contentBeschreibung);
        this.uploadableBeschreibung = new UploadableBeschreibung(new UploaderBeschreibung(this.uploaderName), null);
        this.mediaContentBeschreibung = new MediaContentBeschreibung(this.bitrate, this.laenge, this.size, contentBeschreibung);
        this.mediaContentUploadableBeschreibung = new MediaContentUploadableBeschreibung(this.mediaContentBeschreibung, this.uploadableBeschreibung);
        this.videoBeschreibung = new VideoBeschreibung(this.width, this.hoehe, this.encoding, this.mediaContentUploadableBeschreibung);
        return new LicensedVideoImp(this.videoBeschreibung, this.licensedBeschreibung);
    }
}
