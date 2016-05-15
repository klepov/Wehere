package klep.wehere.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import klep.wehere.R;
import klep.wehere.maps.HandleActivity;

/**
 * Created by klep.io on 31.01.16.
 */
public class AuthActivity extends AppCompatActivity implements AuthLoginFragment.AuthOk {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_auth);
//        toolbar.setTitle("назад");
//        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new AuthLoginFragment())
                    .commit();
        }
    }

    @Override
    public void ok() {
        startActivity(new Intent(this, HandleActivity.class));
        finish();
    }
}
