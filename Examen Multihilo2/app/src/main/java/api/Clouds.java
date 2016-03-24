
package api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds implements Parcelable {

    @SerializedName("all")
    @Expose
    private float all;

    /**
     * 
     * @return
     *     The all
     */
    public float getAll() {
        return all;
    }

    /**
     * 
     * @param all
     *     The all
     */
    public void setAll(float all) {
        this.all = all;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.all);
    }

    public Clouds() {
    }

    protected Clouds(Parcel in) {
        this.all = in.readFloat();
    }

    public static final Parcelable.Creator<Clouds> CREATOR = new Parcelable.Creator<Clouds>() {
        public Clouds createFromParcel(Parcel source) {
            return new Clouds(source);
        }

        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };
}
