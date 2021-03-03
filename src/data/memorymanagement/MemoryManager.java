package data.memorymanagement;

import data.mediaDB.MediaContentUploadable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class MemoryManager implements Serializable {

    private LinkedList memory;
    private BigDecimal gesamteSize;
    private BigDecimal restSize;

    /*
    constructor
    @param: BigDecimal size
     */
    public MemoryManager() {}
    public MemoryManager(BigDecimal size) {
        this.memory = new LinkedList();
        this.setRestSize(size);
        this.setGesamteSize(size);
    }

    /*
    getters and setters
     */
    public LinkedList getMemory() {
        return this.memory;
    }
    public void setMemory(LinkedList memory) {
        this.memory = memory;
    }
    public BigDecimal getRestSize() {
        return restSize;
    }
    public void setRestSize(BigDecimal restSize) {
        this.restSize = restSize;
    }
    public BigDecimal getGesamteSize() {
        return gesamteSize;
    }
    public void setGesamteSize(BigDecimal gesamteSize) {
        this.gesamteSize = gesamteSize;
    }

    /*
    function to add
    @param: T media object
     */
    public <T extends MediaContentUploadable> boolean add(T mediaObj) {
        // set the current Date of Uploading
        mediaObj.setUploadDate(new Date());

        // adding the media object to my LinkedList
        this.memory.add(mediaObj);

        // updating the rest free space int the memory
        this.restSize = this.restSize.subtract(mediaObj.getSize());

        return true;
    }

    /*
    delete media object from the memory and ignore if it does not exist
    @param T media object
    @return boolean
     */
    public <T extends MediaContentUploadable> boolean delete(T mediaObj) {
            this.restSize = this.restSize.add(mediaObj.getSize()); // update size after delete
            this.memory.remove(mediaObj);
            return true;
    }

    /*
    delete media object by address
    @param String Address
    @return boolean
     */
    public <T extends MediaContentUploadable> boolean delete(String Adder) {
        if (Adder == null)
            return false;
        return this.delete((T)contains(Adder));
    }

    /*
    check if the memory contains the passed media object
    @param T media object
    @return boolean
     */
    public <T extends MediaContentUploadable> boolean contains(T mediaObj) {
        if (mediaObj == null)
            return false;
        return this.memory.contains(mediaObj);
    }

    /*
    check if the memory contains a media object, which has the passed address
    @param String addres
    @return null -> if there is no media with this address
    @return T media object that has the passed address
     */
    public <T extends MediaContentUploadable> T contains(String Adder) {
        if (Adder == null)
            return null;

        for (Object media : this.memory){
            if ((((T)media).getAddr()).equals(Adder))
                return (T) media;
        }
        return null;
    }
}
