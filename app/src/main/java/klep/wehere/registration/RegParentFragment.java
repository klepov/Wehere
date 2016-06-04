package klep.wehere.registration;

import android.os.Bundle;

import klep.wehere.common.RegFragment;
import klep.wehere.common.RegPresenter;

/**
 * Created by klep.io on 28.02.16.
 */
public class RegParentFragment extends RegFragment {

    public static RegParentFragment newInstance(Bundle bundle) {
        RegParentFragment fragment = new RegParentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public RegPresenter createPresenter() {
        return new RegParentPresenter(getActivity());
    }

}
