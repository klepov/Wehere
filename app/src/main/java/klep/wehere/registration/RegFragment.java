package klep.wehere.registration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;


import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import klep.wehere.R;
import klep.wehere.common.BaseViewStateFragment;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.image.CreateImage;
import klep.wehere.utils.ErrorCode;


/**
 * Created by klep.io on 14.02.16.
 */
public class RegFragment extends BaseViewStateFragment<RegView,RegPresenter> implements RegView {

    @Bind(R.id.loginReg)
    EditText loginEdit;
    @Bind(R.id.password1)
    EditText password1Edit;
    @Bind(R.id.password2)
    EditText password2Edit;
    @Bind(R.id.nameReg)
    EditText nameRegEdit;
    @Bind(R.id.btn_Reg)
    ActionProcessButton btnReg;
    @Bind(R.id.btn_photo_parent)
    CircleImageView photoParentButton;




    private RegOk regOk;
    String path;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnReg.setMode(ActionProcessButton.Mode.ENDLESS);

        btnReg.setOnClickNormalState(v -> onRegClicked());

        if (savedInstanceState != null){
            path = savedInstanceState.getString("path");
            presenter.resizeImage(path);
        }

    }



    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_reg;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        regOk = (RegOk)activity;
    }

    @OnClick(R.id.btn_Reg)public void onRegClicked(){
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


        presenter.doReg(new RegistrationCredentials(login,password1,password2,nameReg,CreateImage.makeImage(path)));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (path != null) outState.putString("path",path);
    }

    @OnClick(R.id.btn_photo_parent)public void ChangePhoto(){
        FishBun.with(RegFragment.this)
                .setAlbumThumnaliSize(150)//you can resize album thumnail size
                .setPickerCount(1)//you can restrict photo count
                .setPickerSpanCount(5)
                .setCamera(true)//you can use camera
                .startAlbum();

    }



    @Override
    public ViewState createViewState() {
        return new RegViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showRegForm();
    }

    @Override
    public RegPresenter createPresenter() {
        return new RegPresenter();
    }

    @Override
    public void showRegForm() {
        RegViewState vs = (RegViewState) viewState;
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
        RegViewState vs = (RegViewState) viewState;
        vs.setStateShowError();

        Snackbar.make(getView(), ErrorCode.getCodeError(getActivity(),error),Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showRegComplete() {
        regOk.reg();
    }

    @Override
    public void showRegLoading() {
        RegViewState vs = (RegViewState) viewState;
        vs.setStateShowRegForm();
    }

    @Override
    public void showImage(Bitmap bitmap) {
        photoParentButton.setImageBitmap(bitmap);
    }

    public interface RegOk{
        void reg();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Define.ALBUM_REQUEST_CODE:
                try {
                    ArrayList<String> pathFromResult = data.
                            getStringArrayListExtra(Define.INTENT_PATH);
                    path = pathFromResult.get(0);
                    presenter.resizeImage(path);

                }catch (NullPointerException ignored){

                }

                break;
        }
    }
}
