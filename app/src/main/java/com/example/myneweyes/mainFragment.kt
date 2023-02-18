package com.example.myneweyes
// IF YOU ARE USING REAL MOBILE , THIS CODE WORKING WITH THAT



/*
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myneweyes.databinding.FragmentMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class mainFragment : Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private lateinit var map: GoogleMap
    var database: DatabaseReference? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var latLng: LatLng
    private var admin = ""
    private lateinit var circle: Circle
    private var lang = 1
    lateinit var sharpreferences: SharedPreferences
    var pref: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)


        if (arguments?.containsKey("lat") == true) {
            val lat = arguments?.getString("lat")
            val lang = arguments?.getString("lang")
            admin = arguments?.getString("admin", "")!!

            if (lang!!.isNotEmpty()) {
                latLng = LatLng(lat!!.toDouble(), lang!!.toDouble())

                Log.i("latlang", latLng.toString())
            }
        }

        sharpreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        lang = sharpreferences.getInt("lang", 1)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mainmap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }


    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.isMyLocationEnabled = true
        map.uiSettings.apply {
            isMyLocationButtonEnabled = true
            isMapToolbarEnabled = false
        }


        database = FirebaseDatabase.getInstance().getReference()
            .child("Users_Register_Info");


        val related = pref!!.getString("related","")
        val email = pref!!.getString("email","")

        val query = database!!.orderByChild("assist_email").equalTo(email);

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( getdata in snapshot.children) {
                    val _email = getdata.child("blind_email").getValue(
                        String::class.java
                    )

                    if (_email == related ) {
                        val lat = getdata.child("lat").getValue(
                            String::class.java
                        )
                        val lang = getdata.child("lang").getValue(
                            String::class.java
                        )

                        if (lang != "") {
                            latLng = LatLng(lat!!.toDouble(), lang!!.toDouble())
                            drawMarker(latLng, "Student Location Here")
                            // drawCircle(latLng,3000f)
                            zoomocation(latLng)
                        }
                    }
                }
                }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestsBackgroundLocationPermission(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        // onGeofenceReady()
        Toast.makeText(
            requireContext(),
            "Permission Granted! Long Press on the Map to add a Geofence.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun drawMarker(location: LatLng, name: String) {
        map.addMarker(
            MarkerOptions().position(location).title(name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )
    }

    private fun zoomocation(latLng: LatLng) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 12f), 2000, null
        )
    }


    private fun drawCircle(location: LatLng, radius: Float) {
        circle = map.addCircle(
            CircleOptions().center(location).radius(radius.toDouble())
                .strokeColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
                .fillColor(
                    ContextCompat.getColor(
                        requireContext(),
                        androidx.appcompat.R.color.material_blue_grey_800
                    )
                )
        )
    }


    fun requestsBackgroundLocationPermission(fragment: Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                fragment,
                "Background location permission is essential to this application. Without it we will not be able to provide you with our service.",
                2,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}
*/