package klep.wehere.registration;

import android.content.Intent;
import android.os.Bundle;

import klep.wehere.R;
import klep.wehere.common.BaseActivity;
import klep.wehere.common.RegFragment;
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
                    .replace(R.id.fragmentContainerReg, new RegParentFragment())
                    .commit();
        }
    }


    @Override
    public void reg() {
        startActivity(new Intent(this, HandleActivity.class));
        finish();
    }


}
