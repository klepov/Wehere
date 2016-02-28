package klep.wehere.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;

import klep.wehere.R;
import klep.wehere.auth.AuthLoginFragment;
import klep.wehere.common.BaseActivity;
import klep.wehere.maps.HandleActivity;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegActivity extends BaseActivity implements RegFragment.RegOk{

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


    @Override
    public void reg() {
        startActivity(new Intent(this, HandleActivity.class));
        finish();
    }


}
