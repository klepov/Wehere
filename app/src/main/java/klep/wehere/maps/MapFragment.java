
package klep.wehere.maps;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import klep.wehere.R;
import klep.wehere.addChildren.RegActivityChild;
import klep.wehere.common.BaseViewStateFragment;
import klep.wehere.common.LoadingDialogFragment;
import klep.wehere.model.token.Token;
import klep.wehere.model.users.Data;
import klep.wehere.utils.Const;

/**
 * Created by klep.io on 07.01.16.
 */
public class MapFragment extends BaseViewStateFragment<MapView,MapPresenter>
        implements MapView {

    public static final int ZOOM_FOR_USER = 16;
    public static final int LATITUDE_DEFAULT = 49;
    public static final int LONGITUDE_DEFAULTH = 22;
    private List<Data> users;

    private GoogleMap map;

    private LatLng filter;
    private String nameNeedFound;




    @Bind(R.id.map_photo_scroll)LinearLayout scrollView;



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
        ButterKnife.bind(this, view);

        setUpMapIfNeeded();
        if (users == null){
            users = new ArrayList<>();
        }
        return view;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1))
                    .getMap();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
        Log.d("onResume", "onResume");
        if (users.size() == 0) {
            String token = Token.find(Token.class, null).get(0).getToken();
            presenter.getRelation(token);
        }
    }


    @NonNull
    @Override
    public MapPresenter createPresenter() {
        return new MapPresenter(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onNewViewStateInstance() {
        showMap();
    }

    @Override
    public void updateRelation(List<Data> users) {
        this.users.addAll(users);
        putMarker();
        putImage();
    }

    private void putMarker() {
        map.clear();
        for (int i = 0; i<users.size(); i++){
            try {
                double latitude = users.get(i).getLatitude();
                double longitude = users.get(i).getLongitude();
                String name = users.get(i).getName();

                LatLng latLng = new LatLng(latitude,longitude);

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(name)).showInfoWindow();

            }
            catch (NullPointerException ignored){
            }
        }
    }

    private void putImage(){
        for (int i = 0; i<users.size(); i++) {
            String username = users.get(i).getUser();
            String link = users.get(i).getLinkToImage();
            inflateImageLayout(username, link);
        }
    }


    @Override
    public void updateUser(Data user) {
        for (int i = 0; i < users.size(); i++){
             if (users.get(i).getUser().equals(user.getUser())){
                 double latitude = user.getLatitude();
                 double longitude= user.getLongitude();
                 users.get(i).setLatitude(latitude);
                 users.get(i).setLongitude(longitude);
                 putMarker();

             }
        }

        if (nameNeedFound!=null){
            showCameraOnPerson(nameNeedFound);
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

    @OnClick(R.id.FAB_start_reg)
    public void StartChildReg(){
        getContext().startActivity(new Intent(getActivity(), RegActivityChild.class));
    }

    private void inflateImageLayout(String user,String link) {

            CircleImageView imageView = new CircleImageView(getActivity());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(10, 8, 10, 8);
            imageView.setLayoutParams(layoutParams);
            Picasso.with(getActivity())
                    .load(Const.IMAGE_URL + link)
                    .into(imageView);

        scrollView.addView(imageView);
        imageView.setOnClickListener(
                v -> findUser(user)
        );
        }


    private void setColorImage(int position,int border) {
        ((CircleImageView)scrollView.getChildAt(position)).setBorderColor(getResources().getColor(R.color.chosen));
        ((CircleImageView)scrollView.getChildAt(position)).setBorderWidth(border);
    }

    private void showPersonAlways() {
        if (filter != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(filter)
                    .zoom(ZOOM_FOR_USER)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }else {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(LATITUDE_DEFAULT, LONGITUDE_DEFAULTH))
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }


    private void findUser(String name) {
        for (int i = 0; i < users.size();i++){
            if (users.get(i).getUser().equals(name)) {
                Data userFound = users.get(i);
                nameNeedFound = userFound.getUser();


                setColorImage(i, 2);
                try {
                    createLatLng(userFound.getLatitude(),userFound.getLongitude(),i);
                }catch (NullPointerException e){
                    Snackbar.make(getView(),"За этого пользователя никто не входил",Snackbar.LENGTH_LONG).show();
                    setColorImage(i,0);
                }
            }else {
                ((CircleImageView)scrollView.getChildAt(i)).setBorderWidth(0);
            }
        }
    }

    private void createLatLng(Double latitude, Double longitude,int position) {
        LatLng oldFilter = filter;
        filter = new LatLng(latitude,longitude);
        if (oldFilter != null && oldFilter.equals(filter)){
            filter = null;
            nameNeedFound = null;
            setColorImage(position,0);
        }
        showPersonAlways();

    }

    private void showCameraOnPerson(String nameNeedFound) {
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUser().equals(nameNeedFound) && users.get(i).getLatitude() != null){
                moveCamera(users.get(i));
            }
        }
    }

    private void moveCamera(Data data) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(data.getLatitude(),data.getLongitude()))
                .zoom(ZOOM_FOR_USER)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
