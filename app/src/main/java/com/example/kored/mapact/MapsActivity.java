package com.example.kored.mapact;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
    OnMapLongClickListener {

  private GoogleMap mMap;
  LocationManager locationManager;
  LocationListener listener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // Kullanıcıdan Konum İzini İstemiştik onun cevabını onRequestPermissionResult kontrol ediyor

    if (permissions.length > 0 && requestCode == 2) { // İzin verisi var ise
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          == PackageManager.PERMISSION_GRANTED) { //onaylandıysa
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
      }
    }
  }

  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    mMap.setOnMapLongClickListener(this);
    listener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        mMap.clear();
        LatLng i = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(i).title("konumun Burada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(i, 15));
        Toast.makeText(MapsActivity.this, "Konum Değişti", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onStatusChanged(String s, int i, Bundle bundle) {

      }

      @Override
      public void onProviderEnabled(String s) {

      }

      @Override
      public void onProviderDisabled(String s) {

      }
    };


  }

  @Override
  public void onMapLongClick(LatLng latLng) {
    Toast.makeText(this, "Tıklandı", Toast.LENGTH_SHORT).show();
  }
}
