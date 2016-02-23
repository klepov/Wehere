
package klep.wehere.maps;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import klep.wehere.R;
import klep.wehere.addChildren.RegActivityChild;
import klep.wehere.common.BaseViewStateFragment;
import klep.wehere.common.LoadingDialogFragment;
import klep.wehere.model.token.Token;
import klep.wehere.model.users.Data;
import klep.wehere.registration.RegActivity;

/**
 * Created by klep.io on 07.01.16.
 */
public class MapFragment extends BaseViewStateFragment<MapView,MapPresenter>
        implements MapView {

    private List<Data> users;

    private GoogleMap map;



    public static MapFragment newInstance(){
        MapFragment fragmentMap = new MapFragment();
        Bundle args = new Bundle();
        return fragmentMap;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_maps;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        setUpMapIfNeeded();
        users = new ArrayList<>();
        ButterKnife.bind(this,view);
        return view;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1))
                    .getMap();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
        Log.d("onResume","onResume");
        String token = Token.find(Token.class,null).get(0).getToken();
        presenter.getRelation(token);
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @NonNull
    @Override
    public MapPresenter createPresenter() {
        return new MapPresenter(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }


    @Override
    public void onNewViewStateInstance() {
        showMap();
    }

    @Override
    public void showUpdate(List<Data> usersList) {

//        int size = users.getData().size();
//

        for (int newUser = 0;newUser<usersList.size();newUser++){

            for (int oldUser = 0; oldUser < users.size();oldUser++){

                if (users.get(oldUser).getDeviceID().equals(usersList.get(newUser).getDeviceID())){
                    users.remove(oldUser);
                }
            }
        }

        users.addAll(usersList);



        map.clear();

        for (int i = 0; i<users.size(); i++){

            try {
                double latitude = users.get(i).getLatitude();
                double longitude = users.get(i).getLongitude();
                String name = users.get(i).getName();

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude,longitude))
                .title(name)).showInfoWindow();


            }
            catch (NullPointerException ignored){

            }


        }
    }

    @Override
    public void showMap() {
        MapViewState viewState = new MapViewState();
        viewState.setStateShowMap();
    }

    @Override
    public void showLoading() {
        DialogFragment fragment = new LoadingDialogFragment();

        fragment.show(getActivity().getFragmentManager(), "dlg1");
    }

    @Override
    public void showError() {

    }

    @NonNull
    @Override
    public ViewState createViewState() {
        return new MapViewState();
    }

    @OnClick(R.id.FAB_start_reg) public void StartChildReg(){
        getContext().startActivity(new Intent(getActivity(), RegActivityChild.class));
    }
}
