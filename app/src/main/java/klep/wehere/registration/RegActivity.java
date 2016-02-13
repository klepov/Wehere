package klep.wehere.registration;

import android.os.Bundle;

import klep.wehere.R;
import klep.wehere.auth.AuthLoginFragment;
import klep.wehere.common.BaseActivity;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerReg, new RegFragment())
                    .commit();
        }
    }
}
