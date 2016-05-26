package klep.wehere.listPeople;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import klep.wehere.R;

/**
 * Created by klep on 20.05.16 with love.
 */
public class ActivityListUser extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new FragmentListPeople())
                    .commit();
        }
    }
}
