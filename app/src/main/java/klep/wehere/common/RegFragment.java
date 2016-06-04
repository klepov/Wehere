package klep.wehere.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import klep.wehere.R;
import klep.wehere.model.EditCredentials;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.image.CreateImage;
import klep.wehere.model.users.Data;
import klep.wehere.utils.Const;
import klep.wehere.utils.ErrorCode;


/**
 * Created by klep.io on 14.02.16.
 */
public abstract class RegFragment extends BaseViewStateFragment<RegView, RegPresenter> implements RegView, View.OnClickListener {
    @Bind(R.id.loginReg)
    EditText loginEdit;
    @Bind(R.id.password1)
    EditText password1Edit;
    @Bind(R.id.password2)
    EditText password2Edit;
    @Bind(R.id.nameReg)
    EditText nameRegEdit;
    @Bind(R.id.btn_Reg)
    Button btnReg;
    @Bind(R.id.btn_photo_parent)
    CircleImageView photoParentButton;
    @Bind(R.id.toolbar_reg)
    Toolbar toolbar;
    public RegOk regOk;
    String path;
    private Data data;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("назад");
        Drawable exitNews = new IconicsDrawable(getActivity())
                .icon(CommunityMaterial.Icon.cmd_close)
                .color(ContextCompat.getColor(getActivity(), R.color.md_white_1000))
                .sizeDp(16);
        toolbar.setNavigationIcon(exitNews);
        toolbar.setNavigationOnClickListener(this);
        btnReg.setOnClickListener(v -> onRegClicked());
        if (savedInstanceState != null) {
            path = savedInstanceState.getString("path");
            presenter.resizeImage(path);
        }
        if (getArguments().getParcelable(Const.ID_CHANGE) != null) {
            data = getArguments().getParcelable(Const.ID_CHANGE);
            loginEdit.setVisibility(View.GONE);
            password1Edit.setVisibility(View.GONE);
            password2Edit.setVisibility(View.GONE);
            nameRegEdit.setText(data.name);
            Glide.with(this)
                    .load(Const.IMAGE_URL + data.linkToImage)
                    .into(photoParentButton);
            btnReg.setText("Редактировать");
        } else {
            btnReg.setText("Регистрация");
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_reg;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        regOk = (RegOk) activity;
    }

    @OnClick(R.id.btn_Reg)
    public void onRegClicked() {
        if (data == null) {
            String login = loginEdit.getText().toString();
            String password1 = password1Edit.getText().toString();
            String password2 = password2Edit.getText().toString();
            String nameReg = nameRegEdit.getText().toString();
            if (TextUtils.isEmpty(login)) {
                loginEdit.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                loginEdit.startAnimation(shake);
                return;
            } else if (TextUtils.isEmpty(password1)) {
                password1Edit.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                password1Edit.startAnimation(shake);
                return;
            } else if (TextUtils.isEmpty(password2)) {
                password2Edit.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                password2Edit.startAnimation(shake);
                return;
            } else if (TextUtils.isEmpty(nameReg)) {
                nameRegEdit.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                nameRegEdit.startAnimation(shake);
                return;
            } else if (!password1.equals(password2)) {
                showRegError(3);
                return;
            }
            presenter.doReg(new RegistrationCredentials(login,
                    password1,
                    password2,
                    nameReg,
                    CreateImage.makeImage(path)));
        } else {
            String nameReg = nameRegEdit.getText().toString();
            if (TextUtils.isEmpty(nameReg)) {
                nameRegEdit.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                nameRegEdit.startAnimation(shake);
                return;
            }
            presenter.doEdit(new EditCredentials(nameReg,
                    data.getUser(),
                    data.getId(),
                    CreateImage.makeImage(path)));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (path != null) outState.putString("path", path);
    }

    @OnClick(R.id.btn_photo_parent)
    public void ChangePhoto() {
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
    public abstract RegPresenter createPresenter();

    @Override
    public void showRegForm() {
        RegViewState vs = (RegViewState) viewState;
        vs.setStateShowRegForm();
        setFormEnabled(true);
    }

    private void setFormEnabled(boolean enabled) {
        loginEdit.setEnabled(enabled);
        password1Edit.setEnabled(enabled);
        password2Edit.setEnabled(enabled);
    }

    @Override
    public void showRegError(int error) {
        RegViewState vs = (RegViewState) viewState;
        vs.setStateShowError();
        Snackbar.make(getView(), ErrorCode.getCodeError(getActivity(), error), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showRegComplete() {
        regOk.reg();
    }

    @Override
    public void showRegLoading() {
    }

    @Override
    public void showImage(Bitmap bitmap) {
        photoParentButton.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }

    public interface RegOk {
        void reg();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                try {
                    ArrayList<String> pathFromResult = data.
                            getStringArrayListExtra(Define.INTENT_PATH);
                    path = pathFromResult.get(0);
                    presenter.resizeImage(path);

                } catch (NullPointerException ignored) {

                }
                break;
        }
    }
}
