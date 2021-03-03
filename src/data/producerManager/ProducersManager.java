package data.producerManager;

import data.mediaDB.MediaContent;
import data.mediaDB.Uploadable;
import data.mediaDB.implementation.UploaderBeschreibung;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class ProducersManager implements Serializable {

    private List<UploaderBeschreibung> producersList;
    private Map<String, List> producersMedia;

    public ProducersManager(List<UploaderBeschreibung> producersList) {
        this.setProducersList(producersList);
        this.setProducersMedia(new HashMap<>());
    }
    public ProducersManager() {
        this.setProducersList(new LinkedList<>());
        this.setProducersMedia(new HashMap<>());
    }

    public List<UploaderBeschreibung> getProducersList() {
        return producersList;
    }

    public void setProducersList(List<UploaderBeschreibung> producersList) {
        this.producersList = producersList;
    }

    public Map<String, List> getProducersMedia() {
        return producersMedia;
    }

    public void setProducersMedia(Map<String, List> producersMedia) {
        this.producersMedia = producersMedia;
    }

    public List<UploaderBeschreibung> getProducerList() {
        return new LinkedList<>(this.producersList);
    }

    public boolean addProducer(UploaderBeschreibung producer) throws NullPointerException{
        if (producer == null)
            throw new NullPointerException();
        if(this.contains(producer))
            return false;
        else {
            this.producersList.add(producer);
            this.getProducersMedia().put(producer.getName(), new LinkedList());
            return true;
        }
    }

    public boolean deleteProducer(String producer) throws NullPointerException {
        if (producer == null)
            throw new NullPointerException();
        if(this.contains(producer)){
            for(UploaderBeschreibung curr : this.producersList) {
                if (curr.getName().equals(producer)) {
                    this.producersList.remove(curr);
                    this.producersMedia.remove(curr.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(UploaderBeschreibung producer) throws NullPointerException{
        if (producer == null)
            throw new NullPointerException();
        for (UploaderBeschreibung curr : this.producersList){
            if (curr.getName().equals(producer.getName()))
                return true;
        }
        return false;
    }

    public boolean contains(String producerName) {
        if (producerName == null)
            return false;
        for(UploaderBeschreibung curr : this.producersList) {
            if (curr.getName().equals(producerName))
                return true;
        }
        return false;
    }

    public UploaderBeschreibung getProducer(String name) {
        if (name == null)
            return null;
        for(UploaderBeschreibung curr : this.producersList) {
            if (curr.getName().equals(name))
                return curr;
        }
        return null;
    }

    public <T extends MediaContent & Uploadable> void addMediaToProducer(String producerName, T media){
        List list = null;
        if (this.producersMedia.containsKey(producerName)) {
            list = this.producersMedia.get(producerName);
            list.add(media);
            this.producersMedia.put(producerName, list);
        }
    }

    public <T extends MediaContent & Uploadable> void removeMediaFromProducer(String producerName, T media){
        if (this.producersMedia.containsKey(producerName) && this.producersMedia.get(producerName).contains(media)) {
            this.producersMedia.get(producerName).remove(media);
        }
    }

    public List getAllProducersWithMedia(){
        List<String> result = new ArrayList<>();
        Map<String, List> treeMap = new TreeMap<String, List>(this.producersMedia);
        for (Map.Entry<String, List> curr : treeMap.entrySet()){
            Stream.of(curr.getValue()).forEach(result::addAll);
        }
        return result;
    }

    public int getSize() {
        return this.producersList.size();
    }
}
