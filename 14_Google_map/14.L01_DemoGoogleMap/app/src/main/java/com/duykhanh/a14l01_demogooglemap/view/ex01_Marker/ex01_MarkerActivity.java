package com.duykhanh.a14l01_demogooglemap.view.ex01_Marker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.duykhanh.a14l01_demogooglemap.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ex01_MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex01__marker);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ex01_MarkerActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // When map load done
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                List<LatLng> locations = new ArrayList<>();
                locations.add(new LatLng(16.080288, 108.220364));
                locations.add(new LatLng(16.080492, 108.219768));
                locations.add(new LatLng(16.080660, 108.220782));
                locations.add(new LatLng(16.081082, 108.219275));
                locations.add(new LatLng(16.082464, 108.221303));
                locations.add(new LatLng(16.079227, 108.220037));
                for (LatLng latLng : locations) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("ALL"));
                }

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(locations.get(0));
                builder.include(locations.get(locations.size() - 1));
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,200);
                mMap.moveCamera(cu);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14),2000,null);

            }
        });

    }
}
