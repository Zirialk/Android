package com.example.usuario.pr049;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 08/02/2016.
 */
public class DatosZonaPOJO implements Parcelable {

    private String status;

    private String message;

    private String countryCode;

    private String zoneName;

    private String abbreviation;

    private String gmtOffset;

    private String dst;

    private Integer timestamp;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     *
     * @param countryCode
     * The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     *
     * @return
     * The zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     *
     * @param zoneName
     * The zoneName
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     *
     * @return
     * The abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     *
     * @param abbreviation
     * The abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     *
     * @return
     * The gmtOffset
     */
    public String getGmtOffset() {
        return gmtOffset;
    }

    /**
     *
     * @param gmtOffset
     * The gmtOffset
     */
    public void setGmtOffset(String gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    /**
     *
     * @return
     * The dst
     */
    public String getDst() {
        return dst;
    }

    /**
     *
     * @param dst
     * The dst
     */
    public void setDst(String dst) {
        this.dst = dst;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public Integer getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeString(this.countryCode);
        dest.writeString(this.zoneName);
        dest.writeString(this.abbreviation);
        dest.writeString(this.gmtOffset);
        dest.writeString(this.dst);
        dest.writeValue(this.timestamp);
    }

    public DatosZonaPOJO() {
    }

    protected DatosZonaPOJO(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.countryCode = in.readString();
        this.zoneName = in.readString();
        this.abbreviation = in.readString();
        this.gmtOffset = in.readString();
        this.dst = in.readString();
        this.timestamp = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<DatosZonaPOJO> CREATOR = new Parcelable.Creator<DatosZonaPOJO>() {
        public DatosZonaPOJO createFromParcel(Parcel source) {
            return new DatosZonaPOJO(source);
        }

        public DatosZonaPOJO[] newArray(int size) {
            return new DatosZonaPOJO[size];
        }
    };
}
