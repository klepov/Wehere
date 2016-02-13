package klep.wehere.auth;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import klep.wehere.R;
import klep.wehere.common.BaseActivity;

/**
 * Created by klep.io on 31.01.16.
 */
public class AuthActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new AuthLoginFragment())
                    .commit();
        }
    }
}
