
package klep.wehere.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import klep.wehere.model.users.Data;

@ParcelablePlease
public class User implements Parcelable {

    @SerializedName("method")
    @Expose
    public String method;
    @SerializedName("data")
    @Expose
    public Data data;


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

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        UserParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            User target = new User();
            UserParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
