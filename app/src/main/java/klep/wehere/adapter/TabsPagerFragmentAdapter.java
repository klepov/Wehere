package klep.wehere.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import klep.wehere.maps.MapFragment;

/**
 * Created by klep.io on 14.02.16.
 */
public class TabsPagerFragmentAdapter extends FragmentPagerAdapter{

    private String[] tabs;
    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs = new String[]{
                "1","2","3"
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("ok","asd");

        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return MapFragment.newInstance();

            case 2:
                return MapFragment.newInstance();



        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
