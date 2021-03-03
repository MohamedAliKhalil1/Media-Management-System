package simulations.simulation3;

import geschäftslogik.MediaVerwalter;
import geschäftslogik.comparables.AccessCntCmp;
import data.mediaDB.MediaContentUploadable;
import data.mediaDB.Video;
import simulations.ILoeschen;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Sim3Löschen implements ILoeschen {

    private MediaVerwalter CRUD;
    private BigDecimal gesamtSize;
    private int nullZaeler;

    public Sim3Löschen(MediaVerwalter crud){
        this.CRUD = crud;
        this.gesamtSize = this.CRUD.getGesamtSizeOfMemory();
        this.nullZaeler = 0;
    }

    @Override
    public <T extends MediaContentUploadable> void deleteMedia() throws InterruptedException {

        synchronized (this.CRUD) {
            while (this.CRUD.getRestSizeOfMemory().compareTo(this.gesamtSize) == 0) { // memory is empty
                System.out.println(Thread.currentThread().getId() + " Loeschende: waiting zzzzzzzzzzzzzzzzz");
                this.CRUD.wait();
            }
            System.out.println(Thread.currentThread().getId() + " Loeschende Thread is now Active");

            List storedMediaList = this.CRUD.auflisten();

            int rand = (int) ((Math.random() * ((storedMediaList.size()-1)-0))+0);
            if (rand == 0)
                rand = 1;
            if (rand == 0) {
                this.nullZaeler += 1;
                return;
            }
            else
                this.nullZaeler = 0;
            if(this.nullZaeler > 2) {
                rand = 1; // set rand = 1 to delete one item if rand = 0 more than 2 times continuously
                nullZaeler = 0;
            }

            System.out.println("Anzahl der zu loeschenden Media: " + rand);
            System.out.println("vonhandene unsortierte AccessCounts:");
            Iterator iterator = storedMediaList.iterator();
            while (iterator.hasNext()){
                T obj = (T) iterator.next();
                System.out.println(obj.getAC());
            }

            Collections.sort(storedMediaList, new AccessCntCmp<Video>());// sort the media to remove the first rand obj in the list

            System.out.println("vonhandene sortierte AccessCounts:");
            iterator = storedMediaList.iterator();
            while (iterator.hasNext()){
                T obj = (T) iterator.next();
                System.out.println(obj.getAC());
            }
            System.out.println("Löschen erfolgereich: ");
            if (rand == 0)
                System.out.println("true aber null");
            while (rand-- > 0) {
                System.out.println(this.CRUD.löschen((T) storedMediaList.get(0)));
            }

            System.out.println("Rest AccessCounts:");
            iterator = storedMediaList.iterator();
            while (iterator.hasNext()){
                T obj = (T) iterator.next();
                System.out.println(obj.getAC());
            }
            System.out.println("");

            if (this.CRUD.getRestSizeOfMemory().intValue() != 0) this.CRUD.notify(); // memory not full
        }
    }
}
