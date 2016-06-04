
package klep.wehere.model.users;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class Data implements Parcelable {

    public Long _id;

    @SerializedName("longitude")
    @Expose
    public Double longitude;
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("device_ID")
    @Expose
    public String deviceID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("link_to_image")
    @Expose
    public String linkToImage;
    @SerializedName("id")
    @Expose
    public int id;

    public Data() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkToImage() {
        return linkToImage;
    }

    public void setLinkToImage(String link_to_image) {
        this.linkToImage = link_to_image;
    }


    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The deviceID
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * @param deviceID The device_ID
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        DataParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        public Data createFromParcel(Parcel source) {
            Data target = new Data();
            DataParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
