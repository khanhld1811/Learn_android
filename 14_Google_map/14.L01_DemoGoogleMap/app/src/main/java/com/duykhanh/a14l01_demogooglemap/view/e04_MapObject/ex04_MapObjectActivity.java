package com.duykhanh.a14l01_demogooglemap.view.e04_MapObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.duykhanh.a14l01_demogooglemap.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class ex04_MapObjectActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex04__map_object);
        MapFragment mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
