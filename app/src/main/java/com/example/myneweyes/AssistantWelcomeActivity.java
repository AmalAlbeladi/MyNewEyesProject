package com.example.myneweyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AssistantWelcomeActivity extends AppCompatActivity {

    Button Schedule_button,Profile_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_welcome);

        Schedule_button = findViewById(R.id.Schedule_button);
        Profile_button = findViewById(R.id.Profile_button);

        Schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistantWelcomeActivity.this,AssistantScheduleActivity.class);
                startActivity(intent);
            }
        });


       Button tracking = (Button) findViewById(R.id.tracking_button);

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistantWelcomeActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        Profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistantWelcomeActivity.this,Profile.class);
                startActivity(intent);
            }
        });
    }

    public void onClickBack(){
        Intent intent = new Intent(AssistantWelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

}

/*
private GoogleMap map;
    DatabaseReference database = null;


    LatLng latLng;
    private Circle circle;
    SharedPreferences pref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        methodRequiresTwoPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mainmap);
        mapFragment.getMapAsync(this);
        pref = getSharedPreferences("pref", MODE_PRIVATE);

    }

    private void drawMarker(LatLng location, String name) {
        Marker marker = map.addMarker(
                new MarkerOptions().position(location).title(name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        marker.showInfoWindow();
    }

    private void zoomocation(LatLng latLng) {
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 12f), 2000, null
        );
    }


    void requestsBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                    this,
                    "Background location permission is essential to this application. Without it we will not be able to provide you with our service.",
                    2
                    , Manifest.permission.ACCESS_BACKGROUND_LOCATION
            );
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //   map.isMyLocationEnabled();
        start_tracking();
    }


    private void start_tracking() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        //  map.getUiSettings().setCompassEnabled(true);
        //  map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);


        database = FirebaseDatabase.getInstance().getReference()
                .child("Tracking_info");


        String related = pref.getString("related", "");
        String email = pref.getString("email", "");

        Query query = database.orderByChild("assist_email").equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot getdata : dataSnapshot.getChildren()) {

                    String _email = getdata.child("blind_email").getValue(String.class );


                    if (_email.equals(related)) {
                        Log.e("tesssst",_email);
                        String lat = getdata.child("lat").getValue(String.class );
                        String lang = getdata.child("lang").getValue(String.class );

                        if (!Objects.equals(lang, "")) {
                            latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lang));
                            drawMarker(latLng, "Blind student is here");
                            // drawCircle(latLng,3000f)
                            zoomocation(latLng);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new SettingsDialog.Builder(this).build().show();
        } else {
            requestsBackgroundLocationPermission();
        }
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        // onGeofenceReady()
        Toast.makeText(
                this,
                "Permission Granted! Long Press on the Map to add a Geofence.",
                Toast.LENGTH_SHORT
        ).show();
        start_tracking();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this
        );
    }

    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //mapready();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "android.permission.ACCESS_FINE_LOCATION",
                    2, perms);
        }
    }
}

 */