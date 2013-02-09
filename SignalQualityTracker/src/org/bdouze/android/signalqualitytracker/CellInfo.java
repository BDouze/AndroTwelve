package org.bdouze.android.signalqualitytracker;

public class CellInfo {
    private int    mobileCountryCode = 0;
    private int    mobileNetworkCode = 0;
    private int    localAreaCode     = 0;
    private int    cellId            = 0;
    private int    networkType       = 0;
    private int    signalStrength    = 0;
    private double latitude          = 0;
    private double longitude         = 0;
    private double range             = 0;
    private int    sampleCount       = 0;

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

    public int getNetworkType() {
        return networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
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

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public CellInfo(int mobileCountryCode, int mobileNetworkCode,
            int localAreaCode, int cellId) {
        super();
        this.mobileCountryCode = mobileCountryCode;
        this.mobileNetworkCode = mobileNetworkCode;
        this.localAreaCode = localAreaCode;
        this.cellId = cellId;
    }

    public CellInfo() {
        super();
    }
}
