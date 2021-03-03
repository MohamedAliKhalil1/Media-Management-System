package simulations;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.MediaContentUploadable;

import java.math.BigDecimal;
import java.util.List;

public class Abrufen {
    private MediaVerwalter CRUD;
    private BigDecimal gesamtSize;

    public Abrufen(MediaVerwalter crud){
        this.CRUD = crud;
        this.gesamtSize = this.CRUD.getGesamtSizeOfMemory();
    }

    public <T extends MediaContentUploadable> void abrufen() throws InterruptedException {
        synchronized (this.CRUD){
            while (this.CRUD.getRestSizeOfMemory().compareTo(this.gesamtSize) == 0){ // memory is empty
                System.out.println("Abrufende: waiting zzzzzzzzzzzzzzzzz");
                this.CRUD.wait();
            }
            System.out.println("Abrufende Thread is now Active");
            List storedMediaList = this.CRUD.auflisten();
            int rand = (int) ((Math.random() * ((storedMediaList.size()-1)-0))+0);
            System.out.println("Index zu abrufende Media " + rand);
            System.out.println("Abrufen erfolgereich: " + this.CRUD.update((T)(storedMediaList.get(rand))));
            System.out.println("");
        }
    }
}
