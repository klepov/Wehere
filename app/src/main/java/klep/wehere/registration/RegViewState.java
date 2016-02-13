package klep.wehere.registration;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegViewState implements ViewState<RegView>{
    final int STATE_SHOW_REG_FORM = 0;
    final int STATE_SHOW_LOADING= 1;
    final int STATE_SHOW_ERROR= 2;

    int state = STATE_SHOW_REG_FORM;

    @Override
    public void apply(RegView view, boolean retained) {
        switch (state){
            case STATE_SHOW_LOADING:
                view.showRegLoading();
                break;
            case STATE_SHOW_REG_FORM:
                view.showRegForm();
                break;
            case STATE_SHOW_ERROR:
                view.showRegError(0);
                break;
        }
    }

    public void setStateShowRegForm(){
        state = STATE_SHOW_REG_FORM;
    }

    public void setShowLoading(){
        state = STATE_SHOW_LOADING;
    }
    public void setStateShowError(){
        state = STATE_SHOW_ERROR;
    }
}
