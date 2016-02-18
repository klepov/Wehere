package klep.wehere.model.user;

import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

/**
 * Created by klep.io on 19.02.16.
 */
public class DatumBagger implements ParcelBagger<Datum> {
    @Override
    public void write(Datum value, Parcel out, int flags) {

    }

    @Override
    public Datum read(Parcel in) {
        return null;
    }
//    @Override
//    public void write(Datum value, Parcel out, int flags) {
//        out.writeString(value.getIMEI());
//        out.writeDouble(value.getLatitude());
//        out.writeDouble(value.getLongitude());
//        out.writeString(value.getUser());
//        out.writeString(value.getDeviceID());
//    }
//
//    @Override
//    public Datum read(Parcel in) {
//        String IMEI = in.readString();
//        double latitude = in.readDouble();
//        double longitude = in.readDouble();
//        String user = in.readString();
//        String deviceID = in.readString();
//        return new Datum(IMEI,longitude,user,latitude,deviceID);
//    }
}
