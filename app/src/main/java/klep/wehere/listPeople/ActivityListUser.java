package klep.wehere.listPeople;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import klep.wehere.R;

/**
 * Created by klep on 20.05.16 with love.
 */
public class ActivityListUser extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);


//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.fragmentContainer, new FragmentListPeople())
//                    .commit();
//        }
    }
}
