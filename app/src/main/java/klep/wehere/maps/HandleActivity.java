package klep.wehere.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import klep.wehere.R;
import klep.wehere.common.BaseActivity;
import klep.wehere.listPeople.ProfileFragment;
import klep.wehere.services.UpdateLocationService;

public class HandleActivity extends BaseActivity {
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        startService(new Intent(this, UpdateLocationService.class));

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bottomMap:
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentPlace, MapsFragment.newInstance()).commit();
                        }
                        break;
                    case R.id.bottomUser:
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentPlace, new ProfileFragment()).commit();
                        }
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });


    }
}
