package klep.wehere.auth;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by klep.io on 31.01.16.
 */
public class AuthViewSate implements ViewState<AuthView> {
    final int STATE_SHOW_AUTH_FORM = 0;
    final int STATE_SHOW_LOADING= 1;
    final int STATE_SHOW_ERROR= 2;

    int state = STATE_SHOW_AUTH_FORM;

    @Override
    public void apply(AuthView view, boolean retained) {
        switch (state){
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;
            case STATE_SHOW_AUTH_FORM:
                view.showAuthForm();
                break;
            case STATE_SHOW_ERROR:
                view.showError(0);
                break;
        }
    }

    public void setStateShowAuthForm(){
        state = STATE_SHOW_AUTH_FORM;
    }

    public void setShowLoading(){
        state = STATE_SHOW_LOADING;
    }
    public void setStateShowError(){
        state = STATE_SHOW_ERROR;
    }
}
