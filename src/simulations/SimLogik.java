package simulations;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.MediaContentUploadable;
import data.producerManager.ProducersManager;

import java.math.BigDecimal;
import java.util.*;


public class SimLogik implements IEinfuegen {

    private LinkedList zufallMediaList;
    private MediaVerwalter CRUD;
    private BigDecimal gesamtSize;
    private int nullZaeler;

    public SimLogik(LinkedList mediaList, ProducersManager producersManager, MediaVerwalter crud) {
        this.CRUD = crud;
        this.zufallMediaList = mediaList;
        this.gesamtSize = new BigDecimal(200);
        this.nullZaeler = 0;
    }

    @Override
    public <T extends MediaContentUploadable> void addMedia() throws InterruptedException {

        synchronized (this.CRUD) {
            while (this.CRUD.getRestSizeOfMemory().equals(BigDecimal.valueOf(0))) {  // must wait memory is full
                System.out.println(Thread.currentThread().getId() + "Einfuegende: waiting zzzzzzzzzzzzzzzz");
                this.CRUD.notifyAll();
                this.CRUD.wait();
            }


            System.out.println(Thread.currentThread().getName() + " Einfuegende Thread is now Active");
            int rand = (int) ((Math.random() * ((this.zufallMediaList.size() - 1) - 0)) + 0);
            System.out.println("Index zu einfuegende Media : " + rand);
            System.out.println("Hochladen erfolgereich: " + this.CRUD.Hochladen((T) (this.zufallMediaList.get(rand))));
            this.CRUD.Hochladen((T) (this.zufallMediaList.get(rand)));
            System.out.println("");
            if (this.CRUD.getRestSizeOfMemory().intValue() < this.gesamtSize.intValue())
                this.CRUD.notifyAll(); // memory is not empty

        }
    }
}
