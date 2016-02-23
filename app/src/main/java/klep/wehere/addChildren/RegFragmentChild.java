package klep.wehere.addChildren;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.hkm.ui.processbutton.iml.ActionProcessButton;

import butterknife.Bind;
import butterknife.OnClick;
import klep.wehere.R;
import klep.wehere.common.BaseViewStateFragment;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.utils.ErrorCode;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegFragmentChild extends BaseViewStateFragment<RegViewChild,RegPresenterChild> implements RegViewChild {

    @Bind(R.id.loginRegChild)
    EditText loginEdit;
    @Bind(R.id.password1Child)
    EditText password1Edit;
    @Bind(R.id.password2Child)
    EditText password2Edit;
    @Bind(R.id.nameChild)
    EditText nameRegEdit;
    @Bind(R.id.btn_Reg_child)
    ActionProcessButton btnReg;


    private AuthOk authOk;

    public interface AuthOk{
        void ok();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        authOk = (AuthOk)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnReg.setMode(ActionProcessButton.Mode.ENDLESS);

        btnReg.setOnClickNormalState(v -> onRegClicked());

    }

    @OnClick(R.id.btn_Reg_child)public void onRegClicked(){
        String login = loginEdit.getText().toString();
        String password1 = password1Edit.getText().toString();
        String password2 = password2Edit.getText().toString();
        String nameReg = nameRegEdit.getText().toString();

        if (TextUtils.isEmpty(login)){
            loginEdit.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
            loginEdit.startAnimation(shake);
            return;
        }
        else if (TextUtils.isEmpty(password1)){
            password1Edit.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
            password1Edit.startAnimation(shake);
            return;
        }
        else if (TextUtils.isEmpty(password2)){
            password2Edit.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
            password2Edit.startAnimation(shake);
            return;
        }

        else if (TextUtils.isEmpty(nameReg)){
            nameRegEdit.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake);
            nameRegEdit.startAnimation(shake);
            return;
        }

        else if (!password1.equals(password2)){
            showRegError(3);
            return;
        }

        presenter.doReg(new RegistrationCredentials(login,password1,password2,nameReg));

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_reg_child;
    }

    @Override
    public ViewState createViewState() {
        return new RegViewStateChild();
    }

    @Override
    public void onNewViewStateInstance() {
        showRegForm();
    }

    @Override
    public RegPresenterChild createPresenter() {
        return new RegPresenterChild();
    }

    @Override
    public void showRegForm() {
        RegViewStateChild vs = (RegViewStateChild) viewState;
        vs.setStateShowRegForm();

        setFormEnabled(true);

        btnReg.setProgress(0);
    }

    private void setFormEnabled(boolean enabled){
        loginEdit.setEnabled(enabled);
        password1Edit.setEnabled(enabled);
        password2Edit.setEnabled(enabled);
    }
    @Override
    public void showRegError(int error) {
        RegViewStateChild vs = (RegViewStateChild) viewState;
        vs.setStateShowError();

        Snackbar.make(getView(), ErrorCode.getCodeError(getActivity(),error),Snackbar.LENGTH_LONG)

                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        authOk.ok();
                    }
                })
                .show();


    }

    @Override
    public void showRegComplete() {
        authOk.ok();
    }

    @Override
    public void showRegLoading() {
        RegViewStateChild vs = (RegViewStateChild) viewState;
        vs.setStateShowRegForm();
    }
}
