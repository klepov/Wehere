package klep.wehere.event;


import klep.wehere.model.users.Data;

/**
 * Created by klep on 27.05.16 with love.
 */

public class UserClick {
    Data data;


    public UserClick(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
