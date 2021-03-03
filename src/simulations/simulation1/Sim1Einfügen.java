package simulations.simulation1;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.MediaContentUploadable;
import data.producerManager.ProducersManager;
import simulations.IEinfuegen;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Sim1Einfügen implements IEinfuegen {

    private LinkedList zufallMediaList;
    private MediaVerwalter CRUD;
    private BigDecimal gesamtSize;
    private int nullZaeler;

    public Sim1Einfügen(LinkedList mediaList, ProducersManager producersManager, MediaVerwalter crud){
        this.CRUD = crud;
        this.zufallMediaList = mediaList;
        this.gesamtSize = this.CRUD.getGesamtSizeOfMemory();
        this.nullZaeler = 0;
    }

    @Override
    public <T extends MediaContentUploadable> void addMedia() throws InterruptedException {
        synchronized (this.CRUD) {
            if (this.CRUD.getRestSizeOfMemory().equals(BigDecimal.valueOf(0)) == false) {  // must wait memory is not full
                System.out.println(Thread.currentThread().getId() + " Einfuegende Thread is now Active");
                int rand = (int) ((Math.random() * ((this.zufallMediaList.size() - 1) - 0)) + 0);
                System.out.println("Index zu einfuegende Media : " + rand);
                System.out.println("Hochladen erfolgereich: " + this.CRUD.Hochladen((T) (this.zufallMediaList.get(rand))));
                System.out.println("");
            }
        }
        //wait(100);
    }
}
