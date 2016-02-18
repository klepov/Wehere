
package klep.wehere.maps;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import klep.wehere.R;
import klep.wehere.common.BaseViewStateFragment;
import klep.wehere.common.LoadingDialogFragment;
import klep.wehere.model.user.Datum;
import klep.wehere.model.user.Users;

/**
 * Created by klep.io on 07.01.16.
 */
public class MapFragment extends BaseViewStateFragment<MapView,MapPresenter>
        implements MapView {


    private GoogleMap map;
    Polygon polygon;

    public static MapFragment newInstance(){
        MapFragment fragmentMap = new MapFragment();
        Bundle args = new Bundle();
        return fragmentMap;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_maps;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
//        mapView = (com.google.android.gms.maps.MapView) view.findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
        setUpMapIfNeeded();
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
        presenter.getRelation("chil");
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

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
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }


    @Override
    public void onNewViewStateInstance() {
        showMap();
    }

    @Override
    public void showUpdate(Users users) {
        int size = users.getData().size();

        for (int i = 0; i<size; i++){
            double latitude = users.getData().get(i).getLatitude();
            double longitude = users.getData().get(i).getLongitude();
            map.addPolygon(new PolygonOptions().add(new LatLng(latitude,longitude)));
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

    @Override
    public ViewState createViewState() {
        return new MapViewState();
    }
}
