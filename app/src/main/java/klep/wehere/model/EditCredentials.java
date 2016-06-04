package klep.wehere.model;

import com.squareup.okhttp.RequestBody;

/**
 * Created by klep.io on 14.02.16.
 */
public class EditCredentials {
    private String name, user;
    private int id;
    private RequestBody requestBody;

    public EditCredentials(String name, String user, int id, RequestBody requestBody) {
        this.name = name;
        this.user = user;
        this.id = id;
        this.requestBody = requestBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
