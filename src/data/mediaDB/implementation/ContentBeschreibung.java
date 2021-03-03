package data.mediaDB.implementation;

import data.mediaDB.Tag;

import java.io.Serializable;
import java.util.Collection;

public class ContentBeschreibung implements Serializable {
    public String Adress;
    public long AccessCount;
    protected Collection<Tag> Tags;

    public ContentBeschreibung() {
    }

    public ContentBeschreibung(String adress, Collection<Tag> tags, long accessCount) {
        Adress = adress;
        Tags = tags;
        AccessCount = accessCount;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public Collection<Tag> getTags() {
        return Tags;
    }

    public void setTags(Collection<Tag> tags) {
        Tags = tags;
    }

    public long getAccessCount() {
        return AccessCount;
    }

    public void setAccessCount(long accessCount) {
        AccessCount = accessCount;
    }

    @Override
    public String toString() {
        return "Adress:" + this.Adress + " " +
                "AccessCount:" + this.AccessCount + " " +
                "Tags:" + this.Tags;
    }
}
