package simulations;

import data.mediaDB.implementation.*;
import data.mediaDB.implementation.UploaderBeschreibung;
import data.mediaDB.implementation.konkreteMedien.InteractiveVideoImp;
import data.mediaDB.implementation.konkreteMedien.LicensedVideoImp;
import data.producerManager.ProducersManager;
import data.mediaDB.*;

import java.math.BigDecimal;
import java.util.LinkedList;

public class ZufaelligErzeuger {

    private LinkedList mediaList;
    private ProducersManager producersManager;
    private ContentBeschreibung contentBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;
    private InteractiveBeschreibung interactiveBeschreibung;
    private UploadableBeschreibung uploadableBeschreibung;
    private MediaContentBeschreibung mediaContentBeschreibung;
    private VideoBeschreibung videoBeschreibung;
    private MediaContentUploadableBeschreibung mediaContentUploadableBeschreibung;

    public ZufaelligErzeuger(){

    }

    private void makeProducerList(){
        this.producersManager = new ProducersManager();
        this.producersManager.addProducer(new UploaderBeschreibung("Mohamed"));
        this.producersManager.addProducer(new UploaderBeschreibung("Ali"));
        this.producersManager.addProducer(new UploaderBeschreibung("Produzent1"));
    }

    private<T extends MediaContentUploadable> void makeMediaList(){
        this.mediaList = new LinkedList<T>();
       // InteractiveVideo interactiveVideoImp = new InteractiveVideoImp(videoBeschreibung, interactiveBeschreibung);
        //this.mediaList.add(interactiveVideoImp);
        this.mediaList.add(this.LicensedVideoFactory());
        this.mediaList.add(this.LicensedVideoFactory());
        this.mediaList.add(this.LicensedVideoFactory());
        this.mediaList.add(this.InteractiveVideoFactory());
        this.mediaList.add(this.InteractiveVideoFactory());
        this.mediaList.add(this.InteractiveVideoFactory());
    }

    private LicensedVideoImp LicensedVideoFactory(){
        this.contentBeschreibung = new ContentBeschreibung(null, new LinkedList<>(), 0);
        String holder = "musterName";
        this.licensedBeschreibung = new LicensedBeschreibung(holder, this.contentBeschreibung);
        this.uploadableBeschreibung = new UploadableBeschreibung(new UploaderBeschreibung("Ali"), null);
        this.mediaContentBeschreibung = new MediaContentBeschreibung(1024, 1000, BigDecimal.valueOf(100), this.contentBeschreibung);
        this.mediaContentUploadableBeschreibung = new MediaContentUploadableBeschreibung(this.mediaContentBeschreibung, this.uploadableBeschreibung);
        this.videoBeschreibung = new VideoBeschreibung(100, 200, "xxx", this.mediaContentUploadableBeschreibung);

        return new LicensedVideoImp(videoBeschreibung, licensedBeschreibung);
    }

    private InteractiveVideoImp InteractiveVideoFactory(){
        this.contentBeschreibung = new ContentBeschreibung(null, new LinkedList<>(), 0);
        this.interactiveBeschreibung = new InteractiveBeschreibung("Abstimmung", this.contentBeschreibung);
        this.uploadableBeschreibung = new UploadableBeschreibung(new UploaderBeschreibung("Ali"), null);
        this.mediaContentBeschreibung = new MediaContentBeschreibung(1024, 1000, BigDecimal.valueOf(100), this.contentBeschreibung);
        this.mediaContentUploadableBeschreibung = new MediaContentUploadableBeschreibung(this.mediaContentBeschreibung, this.uploadableBeschreibung);
        this.videoBeschreibung = new VideoBeschreibung(100, 200, "xxx", this.mediaContentUploadableBeschreibung);

        return new InteractiveVideoImp(videoBeschreibung, interactiveBeschreibung);
    }

    public ProducersManager getProducers(){
        if (this.producersManager == null)
            this.makeProducerList();

        return this.producersManager;
    }

    public LinkedList getMediaList(){
        if (this.mediaList == null)
            this.makeMediaList();

        return this.mediaList;
    }
    public static void main(String[] args) {
        ZufaelligErzeuger zufaelligErzeuger = new ZufaelligErzeuger();
        LinkedList linkedList = zufaelligErzeuger.getMediaList();
        System.out.println(((LicensedVideo)linkedList.get(0)).toString());
        System.out.println(((Interactive)linkedList.get(3)).getAddr());
    }
}
