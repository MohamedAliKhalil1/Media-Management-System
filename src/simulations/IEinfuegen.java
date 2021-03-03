package simulations;

import data.mediaDB.MediaContentUploadable;

public interface IEinfuegen {
    <T extends MediaContentUploadable> void addMedia() throws InterruptedException;
}
