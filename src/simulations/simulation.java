package simulations;

import data.producerManager.ProducersManager;

import java.util.LinkedList;

public class simulation {
    private LinkedList mediaList;
    private ProducersManager producersManager;
    private ZufaelligErzeuger erzeuger;

    public simulation(){
        this.erzeuger = new ZufaelligErzeuger();
        this.mediaList = this.erzeuger.getMediaList();
        this.producersManager = this.erzeuger.getProducers();
    }

    public LinkedList getMediaList() {
        return this.mediaList;
    }

    public ProducersManager getProducerList() {
        return this.producersManager;
    }
}
