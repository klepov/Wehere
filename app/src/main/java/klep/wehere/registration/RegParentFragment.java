package klep.wehere.registration;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import klep.wehere.common.RegFragment;
import klep.wehere.common.RegPresenter;
import klep.wehere.common.RegViewState;

/**
 * Created by klep.io on 28.02.16.
 */
public class RegParentFragment extends RegFragment {

    @Override
    public RegPresenter createPresenter() {
        return new RegParentPresenter();
    }


}
