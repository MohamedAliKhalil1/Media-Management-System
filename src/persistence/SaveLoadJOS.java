package persistence;

import persistence.dataToSave.MediaProduzentenData;

import java.io.*;

public class SaveLoadJOS implements ISaveLoad {
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private String filePath;

    public SaveLoadJOS(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void initSerialze(String filePath) throws IOException {
        this.filePath = filePath;
        this.fileOutputStream = new FileOutputStream(filePath);
        if (this.objectOutputStream == null)
            this.objectOutputStream = new ObjectOutputStream(this.fileOutputStream);
    }

    private void initDeserialize(String filePath) throws IOException {
        this.filePath = filePath;
        this.fileInputStream = new FileInputStream(filePath);
        this.objectInputStream = new ObjectInputStream(this.fileInputStream);
    }

    @Override
    public boolean save(MediaProduzentenData mediaProduzentenData) throws IOException, NullPointerException {
        if (null == mediaProduzentenData || this.filePath == null)
            throw new NullPointerException();
        this.initSerialze(this.filePath);
        this.serialize(this.objectOutputStream, mediaProduzentenData);
        this.objectOutputStream.close();
        this.fileOutputStream.close();
        return true;
    }

    @Override
    public boolean save(MediaProduzentenData mediaProduzentenData, String filePath) throws IOException, NullPointerException {
        this.filePath = filePath;
        this.save(mediaProduzentenData);
        return true;
    }

    @Override
    public MediaProduzentenData load(String filePath) throws NullPointerException, IOException, ClassNotFoundException {
        this.filePath = filePath;
        return this.load();
    }

    @Override
    public MediaProduzentenData load() throws NullPointerException, IOException, ClassNotFoundException {
        if (this.filePath == null)
            throw new NullPointerException();
        this.initDeserialize(this.filePath);
        return this.deserialize(this.objectInputStream);
    }

    public void serialize(ObjectOutput objectOutput, MediaProduzentenData items) throws IOException {
        objectOutput.writeObject(items);
    }

    public MediaProduzentenData deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (MediaProduzentenData) objectInput.readObject();
    }

}
