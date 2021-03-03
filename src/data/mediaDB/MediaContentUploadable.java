package data.mediaDB;

import java.util.Date;

public interface MediaContentUploadable extends MediaContent, Uploadable {
    void setAdress(String adress);
    void setAccessCount(long accessCount);
    void setUploadDate(Date date);
}
