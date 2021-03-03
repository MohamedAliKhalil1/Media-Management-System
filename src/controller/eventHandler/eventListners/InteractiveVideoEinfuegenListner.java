package controller.eventHandler.eventListners;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.*;
import data.mediaDB.implementation.*;
import data.mediaDB.implementation.UploaderBeschreibung;
import data.mediaDB.implementation.konkreteMedien.InteractiveVideoImp;
import controller.eventHandler.event.InputEvent;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InteractiveVideoEinfuegenListner implements InputEventListener {
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
    private String type_holder;
    private ContentBeschreibung contentBeschreibung;
    private InteractiveBeschreibung interactiveBeschreibung;
    private UploadableBeschreibung uploadableBeschreibung;
    private MediaContentBeschreibung mediaContentBeschreibung;
    private VideoBeschreibung videoBeschreibung;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;

    public InteractiveVideoEinfuegenListner(MediaVerwalter model){
        this.model = model;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        this.command = (String[]) event.getObjekt();
        this.tagsList = Arrays.asList(this.command[2].split(","))
                .stream()
                .map(Tag::valueOf)
                .collect(Collectors.toList());
        this.init();
        this.model.Hochladen(this.InteractiveVideoFactory());
    }

    private InteractiveVideo InteractiveVideoFactory(){

        this.contentBeschreibung = new ContentBeschreibung(null, this.tagsList, 0);
        this.interactiveBeschreibung = new InteractiveBeschreibung(this.type_holder, this.contentBeschreibung);
        this.uploadableBeschreibung = new UploadableBeschreibung(new UploaderBeschreibung(this.uploaderName), null);
        this.mediaContentBeschreibung = new MediaContentBeschreibung(this.bitrate, this.laenge, this.size, this.contentBeschreibung);
        this.mediaContentUploadableBeschreibung = new MediaContentUploadableBeschreibung(this.mediaContentBeschreibung, this.uploadableBeschreibung);
        this.videoBeschreibung = new VideoBeschreibung(this.width, this.hoehe, this.encoding, this.mediaContentUploadableBeschreibung);

        return new InteractiveVideoImp(this.videoBeschreibung, this.interactiveBeschreibung);
     }

    private void init(){

        this.bitrate = Long.parseLong(this.command[3]);
        this.laenge = Double.parseDouble(this.command[4]);
        this.size = BigDecimal.valueOf(100); //TODO fest denn es ist nicht in Muster Angaben eingegeben
        this.uploaderName = this.command[1];
        this.width = Integer.parseInt(this.command[7]);
        this.hoehe = Integer.parseInt(this.command[6]);
        this.encoding = this.command[5];
        this.type_holder = this.command[8];
    }
}
