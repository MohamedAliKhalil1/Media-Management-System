package persistence;

import persistence.dataToSave.MediaProduzentenData;

import java.beans.*;
import java.io.*;

public class SaveLoadBeanJBP implements ISaveLoad {
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;
    private XMLEncoder xmlEncoder;
    private XMLDecoder xmlDecoder;
    private String filePath;

    public SaveLoadBeanJBP(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
    }

    private void initEncoder(String filePath) throws FileNotFoundException {
            this.fileOutputStream = new FileOutputStream(filePath);
            this.bufferedOutputStream = new BufferedOutputStream(this.fileOutputStream);
            this.xmlEncoder = new XMLEncoder(this.bufferedOutputStream);
            this.xmlEncoder.setPersistenceDelegate(java.math.BigDecimal.class,
                    xmlEncoder.getPersistenceDelegate(Integer.class));
    }
    private void initDecoder(String filePath) throws FileNotFoundException {
        this.fileInputStream = new FileInputStream(filePath);
        this.bufferedInputStream = new BufferedInputStream(this.fileInputStream);
        this.xmlDecoder = new XMLDecoder(this.bufferedInputStream);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public boolean save(MediaProduzentenData mediaProduzentenData) throws NullPointerException, IOException {
        if (null == mediaProduzentenData || this.filePath == null)
            throw new NullPointerException();
        this.initEncoder(this.filePath);
        this.xmlEncoder.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(Exception exception) {
                System.out.println("XMLStore.save()");
                exception.printStackTrace();
            }
        });
        this.save(this.xmlEncoder, mediaProduzentenData);
        this.xmlEncoder.close();
        this.bufferedOutputStream.close();
        this.fileOutputStream.close();

        return true;
    }
    @Override
    public boolean save(MediaProduzentenData mediaProduzentenData, String filePath) throws IOException {
        this.filePath = filePath;
        return this.save(mediaProduzentenData);
    }

    @Override
    public MediaProduzentenData load(String filePath) throws NullPointerException, IOException {
        this.filePath = filePath;
        return this.load();
    }
    @Override
    public MediaProduzentenData load() throws NullPointerException, IOException {
        if (null == filePath)
            throw new NullPointerException();
        this.initDecoder(this.filePath);
        return this.load(this.xmlDecoder);
    }

    public void save(XMLEncoder xmlEncoder,MediaProduzentenData mediaProduzentenData)  {
        xmlEncoder.writeObject(mediaProduzentenData);
    }
    public MediaProduzentenData load(XMLDecoder xmlDecoder)  {
        return (MediaProduzentenData) xmlDecoder.readObject();
    }
}
