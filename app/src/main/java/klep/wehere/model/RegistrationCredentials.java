package klep.wehere.model;

import com.squareup.okhttp.RequestBody;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegistrationCredentials {
    private String login,password1,password2,name;
    private RequestBody requestBody;


    public RegistrationCredentials(String login, String password1, String password2, String name,RequestBody requestBody) {
        this.login = login;
        this.password1 = password1;
        this.password2 = password2;
        this.name = name;
        this.requestBody = requestBody;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
