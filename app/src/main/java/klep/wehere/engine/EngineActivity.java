package klep.wehere.engine;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.Bind;
import butterknife.ButterKnife;
import klep.wehere.R;
import klep.wehere.adapter.TabsPagerFragmentAdapter;
import klep.wehere.common.BaseActivity;
import klep.wehere.common.BaseViewStateActivity;
import klep.wehere.common.LoadingDialogFragment;
import klep.wehere.services.HandlerSocket;
import klep.wehere.utils.ErrorCode;

/**
 * Created by klep.io on 14.02.16.
 */
public class EngineActivity extends BaseViewStateActivity<EngineView,EnginePresenter>
    implements EngineView{
    LoadingDialogFragment loading;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.viewPager)
    ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        startService(new Intent(this, HandlerSocket.class));

        presenter.doLogin("chil");
    }

    @NonNull
    @Override
    public EnginePresenter createPresenter() {
        return new EnginePresenter(this);
    }


    private void initTabs() {
        TabsPagerFragmentAdapter adapter =
                new TabsPagerFragmentAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public ViewState createViewState() {
        return new EngineViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showLoading();
    }

    @Override
    public void showLoading() {

//        loading = new LoadingDialogFragment();
//
//
////        loading
//        loading.show(getFragmentManager(),"loading");

    }

    @Override
    public void loadingSuccessful() {
//        loading.dismiss();
        initTabs();
    }

    @Override
    public void showError(int error) {
        Snackbar.make(getWindow().findViewById(R.id.CoordinatorLayout),
                ErrorCode.getCodeError(this,error),Snackbar.LENGTH_LONG)
                .show();
    }
}
