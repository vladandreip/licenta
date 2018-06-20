package caompany.com.licenta.cursuri;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Curse implements Serializable, Parcelable
{

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("_creator")
    @Expose
    private String _creator;
    @SerializedName("__v")
    @Expose
    private Integer __v;
    public final static Parcelable.Creator<Curse> CREATOR = new Creator<Curse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Curse createFromParcel(Parcel in) {
            return new Curse(in);
        }

        public Curse[] newArray(int size) {
            return (new Curse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -2110794163527498847L;

    protected Curse(Parcel in) {
        this._id = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this._creator = ((String) in.readValue((String.class.getClassLoader())));
        this.__v = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Curse() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String get_creator() {
        return _creator;
    }

    public void set_creator(String _creator) {
        this._creator = _creator;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_id);
        dest.writeValue(text);
        dest.writeValue(_creator);
        dest.writeValue(__v);
    }

    public int describeContents() {
        return 0;
    }

}