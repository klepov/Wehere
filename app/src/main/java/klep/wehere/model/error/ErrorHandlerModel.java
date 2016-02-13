
package klep.wehere.model.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorHandlerModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private String status;

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

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
