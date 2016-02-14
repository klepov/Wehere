package klep.wehere.common;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;

import butterknife.ButterKnife;

/**
 * Created by klep.io on 14.02.16.
 */
public abstract class BaseViewStateActivity<V extends MvpView,P extends MvpPresenter<V>>
        extends MvpViewStateActivity<V, P>{

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
