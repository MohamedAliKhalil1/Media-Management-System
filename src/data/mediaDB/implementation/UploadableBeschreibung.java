package data.mediaDB.implementation;

import java.io.Serializable;
import java.util.Date;

public class UploadableBeschreibung implements Serializable {
    private UploaderBeschreibung uploaderBeschreibung;
    private Date uploadDate;

    public UploadableBeschreibung() {
    }

    public UploadableBeschreibung(UploaderBeschreibung uploaderBeschreibung, Date uploadDate) {
        this.uploaderBeschreibung = uploaderBeschreibung;
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public UploaderBeschreibung getUploaderBeschreibung() {
        return uploaderBeschreibung;
    }

    public void setUploaderBeschreibung(UploaderBeschreibung uploaderBeschreibung) {
        this.uploaderBeschreibung = uploaderBeschreibung;
    }

    @Override
    public String toString() {
        return "Uploader:" + this.getUploaderBeschreibung().getName() + " " +
                "UploadDate:" + this.uploadDate;
    }
}
