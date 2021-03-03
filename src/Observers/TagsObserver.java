package Observers;

import geschäftslogik.MediaVerwalter;
import data.mediaDB.Tag;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TagsObserver implements PropertyChangeListener {

    private MediaVerwalter crud;

    public void setCRUD(MediaVerwalter CRUD) {
        this.crud = CRUD;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Map map = this.crud.getVorhandeneTags();
        List list = new LinkedList<>();
        System.out.println("TagsObserver");
        for (Object entry : map.entrySet()){
            if (((Map.Entry<Tag, Integer>)entry).getValue() > 0) {
                list.add(((Map.Entry<Tag, Integer>) entry).getKey());
            }
        }
        System.out.println("vorhandene Tags -> " + list);
    }
}
