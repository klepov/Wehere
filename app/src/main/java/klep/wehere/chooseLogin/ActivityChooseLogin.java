package klep.wehere.chooseLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.google.android.gms.auth.api.Auth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import klep.wehere.R;
import klep.wehere.auth.AuthActivity;
import klep.wehere.registration.RegActivity;
import klep.wehere.services.HandlerSocket;
import klep.wehere.utils.ErrorCode;

/**
 * Created by klep.io on 31.01.16.
 */
public class ActivityChooseLogin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        ButterKnife.bind(this);

        startService(new Intent(this, HandlerSocket.class));
    }


    @OnClick(R.id.login)
    public void login(){
        startActivity(new Intent(this, AuthActivity.class));
    }
    @OnClick(R.id.reg)
    public void reg(){
        startActivity(new Intent(this, RegActivity.class));
    }

}
