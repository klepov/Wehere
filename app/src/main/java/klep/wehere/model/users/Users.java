
package klep.wehere.model.users;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class Users implements Parcelable {


    @SerializedName("data")
    @Expose

    public List<Data> data = new ArrayList<Data>();

    @SerializedName("method")
    @Expose
    public String method;

    /**
     * 
     * @return
     *     The data
     */
    public List<Data> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Data> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The method
     */
    public String getMethod() {
        return method;
    }

    /**
     * 
     * @param method
     *     The method
     */
    public void setMethod(String method) {
        this.method = method;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        UsersParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        public Users createFromParcel(Parcel source) {
            Users target = new Users();
            UsersParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
