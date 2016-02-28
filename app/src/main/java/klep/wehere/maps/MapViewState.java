package klep.wehere.maps;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by klep.io on 16.02.16.
 */
public class MapViewState implements ViewState<MapView> {

    final int STATE_SHOW_MAP = 0;
    final int STATE_UPDATE = 1;
    final int STATE_SHOW_ERROR = 2;
    final int STATE_SHOW_LOADING = 3;

    int state = STATE_SHOW_MAP;

    @Override
    public void apply(MapView view, boolean retained) {

        switch (state){
            case STATE_SHOW_ERROR:
                view.showError();
                break;
            case STATE_SHOW_MAP:
                view.showMap();
                break;
            case STATE_UPDATE:
                view.showUpdate(null);
                break;
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;
        }

    }

    public void setStateShowMap(){
        state = STATE_SHOW_MAP;
    }
    public void setStateUpdate(){
        state = STATE_UPDATE;
    }

    public void setStateError(){
        state = STATE_SHOW_ERROR;
    }

    public void setStateLoading(){
        state = STATE_SHOW_LOADING;
    }


}
