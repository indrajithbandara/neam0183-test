package com.xyz.nmea;

import com.google.common.base.MoreObjects;

/**
 * Created by wuf2 on 2/21/2015.
 */
public class GllNmeaObject extends AbstractNmeaObject {
    private String latitude;
    private String directionOfLatitude;
    private String longitude;
    private String directionOfLongitude;
    private String utcTime;
    private String dataValid;
    private String modeIndicator;

    public GllNmeaObject() {
        super(NmeaConst.MSG_TYPE_GLL);
    }

    public GllNmeaObject(String objType) {
        super(objType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("latitude", latitude)
                .add("directionOfLatitude", directionOfLatitude)
                .add("longitude", longitude)
                .add("directionOfLongitude", directionOfLongitude)
                .add("utcTime", utcTime)
                .add("dataValid", dataValid)
                .add("modeIndicator", modeIndicator)
                .toString();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDirectionOfLatitude() {
        return directionOfLatitude;
    }

    public void setDirectionOfLatitude(String directionOfLatitude) {
        this.directionOfLatitude = directionOfLatitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDirectionOfLongitude() {
        return directionOfLongitude;
    }

    public void setDirectionOfLongitude(String directionOfLongitude) {
        this.directionOfLongitude = directionOfLongitude;
    }

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }

    public String getDataValid() {
        return dataValid;
    }

    public void setDataValid(String dataValid) {
        this.dataValid = dataValid;
    }

    public String getModeIndicator() {
        return modeIndicator;
    }

    public void setModeIndicator(String modeIndicator) {
        this.modeIndicator = modeIndicator;
    }
}
