package klep.wehere.engine;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import klep.wehere.auth.AuthView;

/**
 * Created by klep.io on 14.02.16.
 */
public class EngineViewState implements ViewState<EngineView> {

    final int STATE_SHOW_LOADING= 1;
    final int STATE_SHOW_ERROR= 2;

    int state = STATE_SHOW_LOADING;

    @Override
    public void apply(EngineView view, boolean retained) {
        switch (state){
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;
            case STATE_SHOW_ERROR:
                view.showError(0);
                break;
        }
    }

    public void setShowLoading(){
        state = STATE_SHOW_LOADING;
    }
    public void setStateShowError(){
        state = STATE_SHOW_ERROR;
    }

}
