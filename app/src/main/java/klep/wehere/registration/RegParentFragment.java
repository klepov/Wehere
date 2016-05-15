package klep.wehere.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import klep.wehere.R;
import klep.wehere.common.RegFragment;
import klep.wehere.common.RegPresenter;
import klep.wehere.common.RegViewState;

/**
 * Created by klep.io on 28.02.16.
 */
public class RegParentFragment extends RegFragment {


    @Override
    public RegPresenter createPresenter() {
        return new RegParentPresenter(getActivity());
    }

}
