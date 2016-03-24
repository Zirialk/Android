
package api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain implements Parcelable {

    @SerializedName("3h")
    @Expose
    private float _3h;

    /**
     * 
     * @return
     *     The _3h
     */
    public float get3h() {
        return _3h;
    }

    /**
     * 
     * @param _3h
     *     The 3h
     */
    public void set3h(float _3h) {
        this._3h = _3h;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this._3h);
    }

    public Rain() {
    }

    protected Rain(Parcel in) {
        this._3h = in.readFloat();
    }

    public static final Parcelable.Creator<Rain> CREATOR = new Parcelable.Creator<Rain>() {
        public Rain createFromParcel(Parcel source) {
            return new Rain(source);
        }

        public Rain[] newArray(int size) {
            return new Rain[size];
        }
    };
}
