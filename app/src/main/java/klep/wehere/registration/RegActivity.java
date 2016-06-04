package klep.wehere.registration;

import android.content.Intent;
import android.os.Bundle;

import klep.wehere.R;
import klep.wehere.common.BaseActivity;
import klep.wehere.common.RegFragment;
import klep.wehere.maps.HandleActivity;
import klep.wehere.utils.Const;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegActivity extends BaseActivity implements RegFragment.RegOk {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.ID_CHANGE, getIntent().getParcelableExtra(Const.ID_CHANGE));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerReg, RegParentFragment.newInstance(bundle))
                    .commit();
        }
    }


    @Override
    public void reg() {
        startActivity(new Intent(this, HandleActivity.class));
        finish();
    }


}
