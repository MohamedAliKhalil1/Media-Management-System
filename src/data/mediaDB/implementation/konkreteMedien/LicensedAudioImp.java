package data.mediaDB.implementation.konkreteMedien;


import data.mediaDB.LicensedAudio;
import data.mediaDB.Tag;
import data.mediaDB.implementation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class LicensedAudioImp implements LicensedAudio, Serializable {
    private AudioBeschreibung audioBeschreibung;
    private LicensedBeschreibung licensedBeschreibung;

    public LicensedAudioImp() {
    }

    public LicensedAudioImp(AudioBeschreibung audioBeschreibung, LicensedBeschreibung licensedBeschreibung) {
        this.audioBeschreibung = audioBeschreibung;
        this.licensedBeschreibung = licensedBeschreibung;
    }

    @Override
    public int getSamplingRate() {
        return audioBeschreibung.getSamplingRate();
    }

    public void setSamplingRate(int samplingRate) {
        audioBeschreibung.setSamplingRate(samplingRate);
    }

    @Override
    public String getEncodng() {
        return audioBeschreibung.getEncoding();
    }

    public void setEncoding(String encoding) {
        audioBeschreibung.setEncoding(encoding);
    }

    @Override
    public long getBitrat() {
        return audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getBitrate();
    }

    public void setBitrate(long bitrate) {
        audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setBitrate(bitrate);
    }

    @Override
    public double getLength() {
        return audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().getLengthInMinutes();
    }

    public void setLengthInMinutes(double lengthInMinutes) {
        audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().setLengthInMinutes(lengthInMinutes);
    }

    @Override
    public BigDecimal getSize() {
        return audioBeschreibung.getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().getSizeInMbs();
    }

    public void setSizeInMbs(BigDecimal sizeInMbs) {
        audioBeschreibung.getMediaContentUploadableBeschreibung().
                getMediaContentBeschreibung().setSizeInMbs(sizeInMbs);
    }

    @Override
    public String getAddr() {
        return audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAdress();
    }

    public void setAdress(String adress) {
        audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAdress(adress);
    }

    @Override
    public Collection<Tag> getTag() {
        return audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getTags();
    }

    public void setTags(Collection<Tag> tags) {
        audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setTags(tags);
    }

    @Override
    public long getAC() {

        return audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().getAccessCount();
    }

    public void setAccessCount(long accessCount) {

        audioBeschreibung.getMediaContentUploadableBeschreibung().getMediaContentBeschreibung().
                getContentBeschreibung().setAccessCount(accessCount);
    }

    @Override
    public Date getDate() {

        return audioBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().getUploadDate();
    }

    public void setUploadDate(Date uploadDate) {

        audioBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().setUploadDate(uploadDate);
    }

    @Override
    public String getProducerName() {

        return audioBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().getName();
    }

    public void setName(String name) {

        audioBeschreibung.getMediaContentUploadableBeschreibung().getUploadableBeschreibung().
                getUploaderBeschreibung().setName(name);
    }

    @Override
    public String getHoldr() {
        return licensedBeschreibung.getHolder();
    }

    public void setHolder(String holder) {
        licensedBeschreibung.setHolder(holder);
    }

    public AudioBeschreibung getAudioBeschreibung() {
        return audioBeschreibung;
    }

    public void setAudioBeschreibung(AudioBeschreibung audioBeschreibung) {
        this.audioBeschreibung = audioBeschreibung;
    }

    public LicensedBeschreibung getLicensedBeschreibung() {
        return licensedBeschreibung;
    }

    public void setLicensedBeschreibung(LicensedBeschreibung licensedBeschreibung) {
        this.licensedBeschreibung = licensedBeschreibung;
    }

    @Override
    public String toString() {
        return this.audioBeschreibung.toString() + " " + this.licensedBeschreibung.toString();
    }
}
