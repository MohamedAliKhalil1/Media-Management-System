package geschäftslogik.comparables;

import data.mediaDB.MediaContent;
import data.mediaDB.Uploadable;

import java.util.Comparator;

public class AccessCntCmp<T extends Uploadable & MediaContent> implements Comparator<T> {
    @Override
    public  int compare(T o1, T o2) {
        return o1.getAC() > o2.getAC() ? 1
                : o1.getAC() < o2.getAC() ? -1
                : 0;
    }

}
