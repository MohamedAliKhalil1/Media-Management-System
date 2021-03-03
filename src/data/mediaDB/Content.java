package data.mediaDB;

import java.util.Collection;

public interface Content {
    String getAddr();

    Collection<Tag> getTag();

    long getAC();
}
