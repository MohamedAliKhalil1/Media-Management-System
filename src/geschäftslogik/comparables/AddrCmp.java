package geschäftslogik.comparables;

import data.mediaDB.MediaContent;
import data.mediaDB.Uploadable;

import java.util.Comparator;

public class AddrCmp <T extends Uploadable & MediaContent> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return o1.getAddr().compareTo(o2.getAddr());
    }
}
