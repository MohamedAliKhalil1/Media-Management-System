package persistence;

import persistence.dataToSave.MediaProduzentenData;

import java.io.IOException;

public interface ISaveLoad {
    boolean save(MediaProduzentenData mediaProduzentenData) throws IOException, NullPointerException;
    boolean save(MediaProduzentenData mediaProduzentenData, String filePath) throws IOException, NullPointerException;
    MediaProduzentenData load(String filePath) throws NullPointerException, IOException, ClassNotFoundException;
    MediaProduzentenData load() throws NullPointerException, IOException, ClassNotFoundException;
}
