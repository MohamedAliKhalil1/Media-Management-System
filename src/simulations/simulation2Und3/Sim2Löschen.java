package simulations.simulation2Und3;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.MediaContentUploadable;
import simulations.ILoeschen;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class Sim2Löschen implements ILoeschen {

    private MediaVerwalter CRUD;
    private BigDecimal gesamtSize;

    public Sim2Löschen(MediaVerwalter crud){
        this.CRUD = crud;
        this.gesamtSize = this.CRUD.getGesamtSizeOfMemory();
    }

    @Override
    public <T extends MediaContentUploadable> void deleteMedia() throws InterruptedException {

        synchronized (this.CRUD){
            //this.CRUD.wait();
           while (this.CRUD.getRestSizeOfMemory().compareTo(this.gesamtSize) == 0){ // memory is empty
                System.out.println(Thread.currentThread().getId() + " Loeschende: waiting zzzzzzzzzzzzzzzzz");
                this.CRUD.wait();
            }

            List storedMediaList = this.CRUD.auflisten();
            Iterator iterator = storedMediaList.iterator();
            T minAbrufMedia = (T) storedMediaList.get(0);
            System.out.println("Current Access counts:");
            while (iterator.hasNext()){
                T mediaObj = (T)iterator.next();
                System.out.println(mediaObj.getAC());
                if (mediaObj.getAC() < minAbrufMedia.getAC()){
                    minAbrufMedia = mediaObj;
                }
            }
            System.out.println(Thread.currentThread().getName() + " Loeschende Thread is now Active");
            System.out.println("least Access count -> " + minAbrufMedia.getAC());
            System.out.println("Löschen erfolgereich: " + this.CRUD.löschen(minAbrufMedia));
            System.out.println("");
            if (this.CRUD.getRestSizeOfMemory().intValue() != 0) this.CRUD.notify(); // memory not full
        }
    }
}
