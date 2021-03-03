package persistence.dataToSave;

import data.memorymanagement.MemoryManager;
import data.producerManager.ProducersManager;

import java.io.Serializable;

public class MediaProduzentenData implements Serializable {
    private int adressCounter;
    private MemoryManager memoryManager;
    private ProducersManager producersManager;

    public MediaProduzentenData(){}

    public MediaProduzentenData(int adressCounter, ProducersManager producersManager, MemoryManager memoryManager) {
        this.adressCounter = adressCounter;
        this.memoryManager = memoryManager;
        this.producersManager = producersManager;
    }

    public int getAdressCounter() {
        return adressCounter;
    }

    public void setAdressCounter(int adressCounter) {
        this.adressCounter = adressCounter;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public void setMemoryManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    public ProducersManager getProducersManager() {
        return producersManager;
    }

    public void setProducersManager(ProducersManager producersManager) {
        this.producersManager = producersManager;
    }
}
