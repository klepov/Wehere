package klep.wehere.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Created by klep.io on 31.01.16.
 */
public abstract class BaseLceFragment <CV extends View,M,V extends MvpLceView<M>,P extends MvpPresenter<V>>
    extends MvpLceViewStateFragment<CV,M,V,P> {

    @LayoutRes protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);
        return inflater.inflate(getLayoutRes(),container,false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this,outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
