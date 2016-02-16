//package klep.wehere.maps;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.android.gms.maps.MapView;
//
//import klep.wehere.R;

/**
 * Created by klep.io on 07.01.16.
 */
//public class MapFragment extends Fragment{
//
//
//    MapView mapView;
//
//    public static MapFragment newInstance(){
//        MapFragment fragmentMap = new MapFragment();
//        Bundle args = new Bundle();
//        return fragmentMap;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_maps, container, false);
//        mapView = (MapView) view.findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//
//        return view;
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//
//}

package klep.wehere.maps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import klep.wehere.R;
import klep.wehere.common.BaseViewStateFragment;

/**
 * Created by klep.io on 07.01.16.
 */
public class MapFragment extends BaseViewStateFragment<MapView,MapPresenter>
        implements MapView {


    com.google.android.gms.maps.MapView mapView;

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
        mapView = (com.google.android.gms.maps.MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public MapPresenter createPresenter() {
        return new MapPresenter(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    public void showUpdate() {

    }

    @Override
    public void showMap() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public ViewState createViewState() {
        return new MapViewState();
    }
}
