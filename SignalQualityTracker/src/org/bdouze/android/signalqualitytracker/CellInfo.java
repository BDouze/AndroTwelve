package org.bdouze.android.signalqualitytracker;

public class CellInfo {
    private int    mobileCountryCode;
    private int    mobileNetworkCode;
    private int    localAreaCode;
    private int    cellId;
    private double latitude;
    private double longitude;
    private double range;
    private int    sampleCount;

    public int getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(int mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public int getMobileNetworkCode() {
        return mobileNetworkCode;
    }

    public void setMobileNetworkCode(int mobileNetworkCode) {
        this.mobileNetworkCode = mobileNetworkCode;
    }

    public int getLocalAreaCode() {
        return localAreaCode;
    }

    public void setLocalAreaCode(int localAreaCode) {
        this.localAreaCode = localAreaCode;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public CellInfo(int mobileCountryCode, int mobileNetworkCode,
            int localAreaCode, int cellId) {
        super();
        this.mobileCountryCode = mobileCountryCode;
        this.mobileNetworkCode = mobileNetworkCode;
        this.localAreaCode = localAreaCode;
        this.cellId = cellId;
    }    
}
