package klep.wehere.chooseLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

//import com.google.android.gms.auth.api.Auth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import klep.wehere.R;
import klep.wehere.auth.AuthActivity;
import klep.wehere.registration.RegActivity;
import klep.wehere.services.HandlerSocketService;

/**
 * Created by klep.io on 31.01.16.
 */
public class ActivityChooseLogin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        ButterKnife.bind(this);

        startService(new Intent(this, HandlerSocketService.class));
    }


    @OnClick(R.id.login)
    public void login() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
    @OnClick(R.id.reg)
    public void reg(){
        startActivity(new Intent(this, RegActivity.class));
        finish();

    }

}
