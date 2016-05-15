package klep.wehere.auth;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.Bind;
import butterknife.OnClick;
import klep.wehere.R;
import klep.wehere.chooseLogin.ActivityChooseLogin;
import klep.wehere.common.BaseViewStateFragment;
import klep.wehere.utils.ErrorCode;

/**
 * Created by klep.io on 31.01.16.
 */
public class AuthLoginFragment extends BaseViewStateFragment<AuthView, AuthPresenter>
        implements AuthView, View.OnClickListener {

    @Bind(R.id.edit_auth_login)
    EditText authLogin;

    @Bind(R.id.edit_auth_password)
    EditText authPassword;

    @Bind(R.id.btn_auth)
    ActionProcessButton authButton;

    @Bind(R.id.errorView)
    TextView errorView;

    @Bind(R.id.authForm)
    ViewGroup authForm;


    @Bind(R.id.toolbar_auth)
    Toolbar toolbar;


    private AuthOk authOk;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        authOk = (AuthOk) activity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        toolbar.setTitle("назад");

        Drawable exitNews = new IconicsDrawable(getActivity())
                .icon(CommunityMaterial.Icon.cmd_close)
                .color(ContextCompat.getColor(getActivity(), R.color.md_white_1000))
                .sizeDp(16);

        toolbar.setNavigationIcon(exitNews);
        toolbar.setNavigationOnClickListener(this);

        authButton.setMode(ActionProcessButton.Mode.ENDLESS);

        authButton.setOnClickNormalState(v -> onAuthClicked());

        int startDelay = getResources().getInteger(android.R.integer.config_mediumAnimTime)
                + 100;

        LayoutTransition transition = new LayoutTransition();
        transition.enableTransitionType(LayoutTransition.CHANGING);
        transition.setStartDelay(LayoutTransition.APPEARING, startDelay);
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, startDelay);
        authForm.setLayoutTransition(transition);

    }

    @NonNull
    @Override
    public ViewState createViewState() {
        return new AuthViewSate();
    }


    @NonNull
    @Override
    public AuthPresenter createPresenter() {
        return new AuthPresenter(getActivity());
    }

    @OnClick(R.id.btn_auth)
    public void onAuthClicked() {
        String uname = authLogin.getText().toString();
        String upass = authPassword.getText().toString();

        authForm.clearAnimation();

        if (TextUtils.isEmpty(uname)) {
            authLogin.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            authLogin.startAnimation(shake);
            return;
        }

        if (TextUtils.isEmpty(upass)) {
            authPassword.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            authPassword.startAnimation(shake);
            return;
        }

        authButton.setMode(ActionProcessButton.Mode.PROGRESS);
        presenter.doLogin(uname, upass);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setRetainInstance(true);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_auth;
    }


    @Override
    public void onNewViewStateInstance() {
        showAuthForm();
    }


    @Override
    public void showAuthForm() {
        AuthViewSate authViewSate = (AuthViewSate) viewState;
        authViewSate.setStateShowAuthForm();

        errorView.setVisibility(View.GONE);
        setFormEnabled(true);

        authButton.setProgress(0);
    }


    @Override
    public void showError(int error) {
        AuthViewSate vs = (AuthViewSate) viewState;
        vs.setStateShowError();

        Snackbar.make(getView(), ErrorCode.getCodeError(getActivity(), error), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void authSuccessful() {

        authOk.ok();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), ActivityChooseLogin.class));
        getActivity().finish();

    }

    public interface AuthOk {
        void ok();
    }

    private void setFormEnabled(boolean enabled) {
        authLogin.setEnabled(enabled);
        authPassword.setEnabled(enabled);
        authButton.setEnabled(enabled);
    }

}
