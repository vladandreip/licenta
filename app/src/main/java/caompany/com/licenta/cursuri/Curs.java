package caompany.com.licenta.cursuri;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Curs implements Serializable, Parcelable
{

    @SerializedName("curses")
    @Expose
    private List<Curse> curses = null;
    public final static Parcelable.Creator<Curs> CREATOR = new Creator<Curs>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Curs createFromParcel(Parcel in) {
            return new Curs(in);
        }

        public Curs[] newArray(int size) {
            return (new Curs[size]);
        }

    }
            ;
    private final static long serialVersionUID = 8326452275427498418L;

    protected Curs(Parcel in) {
        in.readList(this.curses, (caompany.com.licenta.cursuri.Curse.class.getClassLoader()));
    }

    public Curs() {
    }

    public List<Curse> getCurses() {
        return curses;
    }

    public void setCurses(List<Curse> curses) {
        this.curses = curses;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(curses);
    }

    public int describeContents() {
        return 0;
    }

}