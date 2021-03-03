package geschäftslogik;

import data.memorymanagement.MemoryManager;
import persistence.RandomAccess;
import persistence.SaveLoadBeanJBP;
import persistence.SaveLoadJOS;
import persistence.dataToSave.MediaProduzentenData;
import data.mediaDB.implementation.UploaderBeschreibung;
import data.producerManager.ProducersManager;
import geschäftslogik.comparables.AccessCntCmp;
import geschäftslogik.comparables.AddrCmp;
import data.mediaDB.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class MediaVerwalter {

    private MemoryManager memory;
    private ProducersManager producersManager;
    private PropertyChangeSupport propertyChangeSupport;
    private HashMap<Tag, Integer> vorhandeneTags;
    private StringBuilder AddressBuilder;
    private int AddressCounter;
    private List filteredData;
    private MediaProduzentenData mediaProduzentenData;

    public MediaVerwalter(ProducersManager producersManager, MemoryManager memoryManager) {
        this.memory = memoryManager;
        this.producersManager = producersManager;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.AddressCounter = 1;
        this.filteredData = new LinkedList();
        this.vorhandeneTags = new HashMap<>();
        this.vorhandeneTags.put(Tag.Animal, 0);
        this.vorhandeneTags.put(Tag.Lifestyle, 0);
        this.vorhandeneTags.put(Tag.News, 0);
        this.vorhandeneTags.put(Tag.Tutorial, 0);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener pcl) { this.propertyChangeSupport.addPropertyChangeListener(pcl); }
    public synchronized void removePropertyChangeListener(PropertyChangeListener pcl) {this.propertyChangeSupport.removePropertyChangeListener(pcl);}

    /*
    getters and setters
     */
    public synchronized BigDecimal getRestSizeOfMemory(){
        return this.memory.getRestSize();
    }
    public synchronized BigDecimal getGesamtSizeOfMemory(){
        return this.memory.getGesamteSize();
    }
    public synchronized MemoryManager getMemory() {
        return memory;
    }
    public synchronized ProducersManager getProducersManager() {
        return producersManager;
    }
    public synchronized HashMap<Tag, Integer> getVorhandeneTags() {
        return vorhandeneTags;
    }

    /*
    generating a unique adress
    @return String Adress
    */
    private synchronized String AdressVergeben(){
        this.AddressBuilder = new StringBuilder("Addr");;
        this.AddressBuilder.append(this.AddressCounter++);
        return this.AddressBuilder.toString();
    }


    /*
    upload a media object to the system
    @param T media object
    @return boolean
     */
    public synchronized <T extends MediaContentUploadable>  boolean Hochladen(T mediaObj) {
        // the object cannot be null
        if(mediaObj == null) {
            this.propertyChangeSupport.firePropertyChange("Failed Hochladen", null, null);
            return false;
        }

        // return false if Media object already exists in the memory or the producer name is not in the list or very big
        if(this.memory.contains(mediaObj) ||
                !(this.producersManager.contains(mediaObj.getProducerName())) ||
                mediaObj.getSize().longValue() > this.memory.getRestSize().longValue() ||
                this.memory.contains(mediaObj.getAddr()) != null) {
            this.propertyChangeSupport.firePropertyChange("Failed to Upload", null, this.auflisten());
            return false;
        } else {
            // give the media an Address if it has not one
            if (mediaObj.getAddr() == null)
                mediaObj.setAdress(this.AdressVergeben());

            // pass the object to the memory manager to store it
            this.memory.add(mediaObj);

            // updating the hash map in the Producer manager producer -> media
            this.producersManager.addMediaToProducer(mediaObj.getProducerName(), mediaObj);

            for (Tag en : mediaObj.getTag()){
                this.vorhandeneTags.put(en, this.vorhandeneTags.get(en)+1);
            }
            UploaderBeschreibung producer = this.producersManager.getProducer(mediaObj.getProducerName());
            producer.setNumberOfMedia(producer.getNumberOfMedia()+1);

            // notify the observers with changes
            this.propertyChangeSupport.firePropertyChange("HochLaden -> success", null, this.auflisten());
            return true;
            }
    }

    /*
    update the access count +1 of the passed media object
    @param T media object
    @return boolean
     */
    public synchronized <T extends MediaContentUploadable> boolean update(T mediaObj) {
        // the media object cannot be null
        if (mediaObj == null) {
            this.propertyChangeSupport.firePropertyChange("Failed update", null, this.auflisten());
            return false;
        }

        // check if the passed object is already exist in the memory
        if (this.memory.contains(mediaObj)){
            // updating the access count
            mediaObj.setAccessCount(mediaObj.getAC()+1);

            // notify all the listners with the changes
            this.propertyChangeSupport.firePropertyChange("update", null, this.auflisten());
            return true;
        }

        // notify also if the updating has failed
        this.propertyChangeSupport.firePropertyChange("Failed update", null, this.auflisten());
        return false;
    }

    /*
    update the access count +1 of the media object which has the passed address
    @param String Address
    @return boolean
     */
    public synchronized <T extends MediaContentUploadable> boolean update(String Adder) throws NullPointerException {
        return this.update((T) this.memory.contains(Adder));
    }

    /*
    delete a media object from the system
    @param T media object
    @return boolean
     */
    public synchronized <T extends MediaContentUploadable> boolean löschen(T mediaObj) {
        // the passed object cannot be null
        if (mediaObj == null) {
            propertyChangeSupport.firePropertyChange("Failed delete", null, this.auflisten());
            return false;
        }

        // check if the passed media exists in the memory
        if (this.memory.contains(mediaObj)){

            // update the hash map of the producers in the producer manager (producer -> (list of his media))
            this.producersManager.removeMediaFromProducer(mediaObj.getProducerName(), mediaObj);

            //remove the media from the memory
            this.memory.delete(mediaObj);

            for (Tag en : mediaObj.getTag()){
                this.vorhandeneTags.put(en, this.vorhandeneTags.get(en)-1);
            }

            UploaderBeschreibung producer = this.producersManager.getProducer(mediaObj.getProducerName());
            producer.setNumberOfMedia(producer.getNumberOfMedia()-1);

            // notify all the observers with the changes
            propertyChangeSupport.firePropertyChange("delete", null, this.auflisten());

            return true;
        }

        //notify when the media does not exist and failed to delete
        this.propertyChangeSupport.firePropertyChange("Failed delete", null, this.auflisten());
        return false;
    }

    /*
    delete a media object from the system with address
    @param String address
    @return boolean
     */
    public synchronized <T extends MediaContentUploadable> boolean löschen(String address) throws NullPointerException {
        return this.löschen((T) this.memory.contains(address));
    }

    /*
    get all the current uploaded media objects with a specific typ
    @param String className
    @return list of media objects
     */
    public synchronized List auflisten(String className) {
        StringBuilder fullClassName = new StringBuilder("data.mediaDB.implementation.konkreteMedien.");
        fullClassName.append(className);
        fullClassName.append("Imp");

        try {
            this.filteredData =  (List) this.memory.getMemory().stream().filter(Class.forName(fullClassName.toString()) :: isInstance).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            this.propertyChangeSupport.firePropertyChange("Failed nachTypListen", null, this.auflisten());
        }

        this.propertyChangeSupport.firePropertyChange("nachTypListen", null, this.filteredData);
        return this.filteredData;
    }

    /*
    get all the current uploaded media objects
    @return list of media objects
     */
    public synchronized List auflisten() {
        return this.memory.getMemory();
    }

    /*
    sorting the list of objects by their Addresses and notify the controller
     */
    public synchronized <T extends MediaContentUploadable> void  nachAddressSortieren(){
        List list = this.memory.getMemory();
        Collections.sort(list, new AddrCmp<T>());
        this.propertyChangeSupport.firePropertyChange("nachAdressSortieren", null, list);
    }

    /*
    sorting the list of objects by their Access counts and notify the controller
     */
    public synchronized <T extends  MediaContentUploadable> void nachAccessCountSortieren(){
        List list = this.memory.getMemory();
        Collections.sort(list, new AccessCntCmp<T>());
        this.propertyChangeSupport.firePropertyChange("nachAcSortieren", null, list);
    }

    /*
    sorting the list of objects by their producers and notify the controller
     */
    public synchronized void nachProduzentSortieren(){
        this.propertyChangeSupport.firePropertyChange("nachProduzentSortieren",
                null, this.producersManager.getAllProducersWithMedia());
    }

    /*
    add a new producer to the producer manager and notify the controller
    @param String producer name
     */
    public synchronized void addProducer(String producerName){
        this.producersManager.addProducer(new UploaderBeschreibung(producerName));
        this.propertyChangeSupport.firePropertyChange("Producer added", null, this.auflisten());
    }

    /*
    remove a poducer from the system and notify the controller with changes
    @param Strong producer name
     */
    public synchronized void removeProducer(String producerName){
        this.removeAllMediaForProducerFromMemory(producerName);
        this.producersManager.deleteProducer(producerName);
        this.propertyChangeSupport.firePropertyChange("deleteProducer", null, this.auflisten());
    }

    /*
    check if the system contains the passed producer
    @param String producer name
     */
    public synchronized boolean containProducer(String producerName){
        return this.producersManager.contains(producerName);
    }

    /*
    remove all the media of a specific producer
    @param String producer name
     */
    private synchronized <T extends MediaContentUploadable>void removeAllMediaForProducerFromMemory(String producerName){
        List list = this.memory.getMemory();

        for (Object media : list) {
            if (((T)media).getProducerName().equalsIgnoreCase(producerName))
                this.löschen((T)media);
        }
    }

    /*
    get a list of the current producers
    @return list of producers
     */
    public synchronized List getProduzenten(){
        this.propertyChangeSupport.firePropertyChange("uploader", null, this.producersManager.getProducersList());
        return this.producersManager.getProducerList();
    }

    /*
    collects the data to store the current state in a file
    @return MediaProduzentenData
     */
    public synchronized MediaProduzentenData collectData(){
        this.mediaProduzentenData = new MediaProduzentenData(
                this.AddressCounter,
                this.producersManager,
                this.memory
        );
        return this.mediaProduzentenData;
    }

    /*
    return the data again the was read from the file
     */
    public synchronized void setDataAgain(MediaProduzentenData mediaProduzentenData){
        this.mediaProduzentenData = mediaProduzentenData;
        this.AddressCounter = this.mediaProduzentenData.getAdressCounter();
        this.memory = this.mediaProduzentenData.getMemoryManager();
        this.producersManager = this.mediaProduzentenData.getProducersManager();
    }

    /*
    save the data in the form of Java Bean Persistence (JBP)
    @param String filePath
     */
    public synchronized void saveJBP(String filePath) throws IOException {
        SaveLoadBeanJBP saveLoadBeanJBP = new SaveLoadBeanJBP(filePath);
        saveLoadBeanJBP.save(this.collectData());
        this.propertyChangeSupport.firePropertyChange("JBP saved", null, this.auflisten());
    }

    /*
    load data from an xml JBP format
    @param String filePath
     */
    public synchronized void loadJBP(String filePath) throws IOException {
        SaveLoadBeanJBP saveLoadBeanJBP = new SaveLoadBeanJBP(filePath);
        MediaProduzentenData mediaProduzentenData = saveLoadBeanJBP.load();
        this.setDataAgain(mediaProduzentenData);
        this.propertyChangeSupport.firePropertyChange("JBP loaded", null, this.auflisten());
    }

    /*
    save the data in the form of JOS serialization
    @param String filePath
     */
    public synchronized void saveJOS(String filePath) throws IOException {
        SaveLoadJOS saveLoadJOS = new SaveLoadJOS(filePath);
        saveLoadJOS.save(this.collectData());
        this.propertyChangeSupport.firePropertyChange("JOS saved", null, this.auflisten());
    }

    /*
    load data from JOS
    @param String filePath
     */
    public synchronized void loadJOS(String filePath) throws IOException, ClassNotFoundException {
        SaveLoadJOS saveLoadJOS = new SaveLoadJOS(filePath);
        this.setDataAgain(saveLoadJOS.load());
        this.propertyChangeSupport.firePropertyChange("JOS loaded", null, this.auflisten());
    }

    /*
    save a specific object with its address
    @param String address
    @param String file path
     */
    public synchronized void saveRandomMedia(String adress, String filePath) throws IOException {
        persistence.RandomAccess randomAccess = new RandomAccess(filePath);
        randomAccess.saveMedia(this.getMemory().contains(adress));
        this.propertyChangeSupport.firePropertyChange("saveRandom", null, this.auflisten());
    }

    /*
   load a specific object with its address
   @param String address
   @param String file path
    */
    public synchronized <T extends MediaContentUploadable> void loadRandomMedia(String adress, String filePath) throws IOException, ClassNotFoundException {
        RandomAccess randomAccess = new RandomAccess(filePath);
        T mediaObj;
        mediaObj =  randomAccess.semiRandomAccessLoad(adress);
        if (mediaObj != null) {
            if(!this.getProducersManager().contains(mediaObj.getProducerName()))
                this.getProducersManager().addProducer(new UploaderBeschreibung(mediaObj.getProducerName()));
            this.Hochladen(mediaObj);
            this.propertyChangeSupport.firePropertyChange("loadRandom", null, this.auflisten());
        }
        else
            this.propertyChangeSupport.firePropertyChange("Not Found", null, this.auflisten());
    }

    public synchronized void vorhandeneTags() {
        List list = new LinkedList<>();
        for (Object entry : this.vorhandeneTags.entrySet()){
            if (((Map.Entry<Tag, Integer>)entry).getValue() > 0) {
                list.add(((Map.Entry<Tag, Integer>) entry).getKey());
            }
        }
        this.propertyChangeSupport.firePropertyChange("vorhandene Tags: ", null, list);
    }

    public synchronized void nichtVorhandeneTags() {
        List list = new LinkedList<>();
        for (Object entry : this.vorhandeneTags.entrySet()){
            if (((Map.Entry<Tag, Integer>)entry).getValue() == 0) {
                list.add(((Map.Entry<Tag, Integer>) entry).getKey());
            }
        }
        this.propertyChangeSupport.firePropertyChange("nicht Vorhandene Tags: ", null, list);
    }
}
