package simulations.simulation1;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.MediaContentUploadable;
import simulations.ILoeschen;

import java.math.BigDecimal;
import java.util.List;

public class Sim1Loeschen implements ILoeschen {

    private MediaVerwalter CRUD;
    private BigDecimal gesamtSize;

    public Sim1Loeschen(MediaVerwalter crud){
        this.CRUD = crud;
        this.gesamtSize = this.CRUD.getGesamtSizeOfMemory();
    }

    @Override
    public <T extends MediaContentUploadable> void deleteMedia() throws InterruptedException {
        synchronized (this.CRUD) {
            if (this.CRUD.getRestSizeOfMemory().compareTo(this.gesamtSize) != 0) { // memory is not empty
                System.out.println("Loeschende Thread is now Active");
                List storedMediaList = this.CRUD.auflisten();
                int rand = (int) ((Math.random() * ((storedMediaList.size() - 1) - 0)) + 0);

                System.out.println("Index zu loeschende Media: " + rand);
                System.out.println("Löschen erfolgereich: " + this.CRUD.löschen((T) (storedMediaList.get(rand))));
                System.out.println("");
            }

        }
    }
}
