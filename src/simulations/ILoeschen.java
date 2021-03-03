package simulations;

import data.mediaDB.MediaContentUploadable;

public interface ILoeschen {
    <T extends MediaContentUploadable> void deleteMedia() throws InterruptedException;
}
