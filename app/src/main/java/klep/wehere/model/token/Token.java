
package klep.wehere.model.token;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.orm.SugarRecord;

@ParcelablePlease
public class Token extends SugarRecord implements Parcelable{


    @SerializedName("token")
    @Expose
    public String token;

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    public Token(){}
    public Token(String token) {
        this.token = token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TokenParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        public Token createFromParcel(Parcel source) {
            Token target = new Token();
            TokenParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
