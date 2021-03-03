package data.mediaDB.implementation;

import java.io.Serializable;
import java.math.BigDecimal;

public class MediaContentBeschreibung implements Serializable {
    private long bitrate;
    private double lengthInMinutes;
    private BigDecimal sizeInMbs;
    private ContentBeschreibung contentBeschreibung;

    public MediaContentBeschreibung() {
    }

    public MediaContentBeschreibung(long bitrate, double lengthInMinutes, BigDecimal sizeInMbs, ContentBeschreibung contentBeschreibung) {
        this.bitrate = bitrate;
        this.lengthInMinutes = lengthInMinutes;
        this.sizeInMbs = sizeInMbs;
        this.contentBeschreibung = contentBeschreibung;
    }

    public long getBitrate() {
        return bitrate;
    }

    public void setBitrate(long bitrate) {
        this.bitrate = bitrate;
    }

    public double getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(double lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public BigDecimal getSizeInMbs() {
        return sizeInMbs;
    }

    public void setSizeInMbs(BigDecimal sizeInMbs) {
        this.sizeInMbs = sizeInMbs;
    }

    public ContentBeschreibung getContentBeschreibung() {
        return contentBeschreibung;
    }

    public void setContentBeschreibung(ContentBeschreibung contentBeschreibung) {
        this.contentBeschreibung = contentBeschreibung;
    }

    @Override
    public String toString() {
        return this.contentBeschreibung.toString() + " " +
                " Bitrate:" + this.bitrate + " " +
                "Length:" + this.lengthInMinutes + " " +
                "Size" + this.sizeInMbs;
    }
}
