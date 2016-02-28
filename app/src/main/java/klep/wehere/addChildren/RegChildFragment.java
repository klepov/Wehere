package klep.wehere.addChildren;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import klep.wehere.common.RegFragment;
import klep.wehere.common.RegPresenter;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegChildFragment extends RegFragment{

    @Override
    public RegPresenter createPresenter() {
        return new RegPresenterChild();
    }

}
