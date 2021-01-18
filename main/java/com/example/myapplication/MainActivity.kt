package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.tr_button)
        button.setOnClickListener {
            val a:TextView = findViewById(R.id.tr_view)
            //val b:EditText = findViewById(R.id.tr_text)

            a.text="눌림당함"
            //b.setText(b.getText().toString() + "\n hi")
            Log.d("버튼","눌림")
            getLocation()
        }
    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Log.d("버튼","조건문 내부")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        Log.d("버튼","조건문 외부"+LocationManager.PASSIVE_PROVIDER)

        //가져오는 부분
        var location : Location? = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            Log.d("Test", "GPS Location changed, Latitude: $latitude" +
                    ", Longitude: $longitude")
        }
        else{
            Log.d("실망","저장된 위치 없음" + LocationManager.GPS_PROVIDER)
        }

        location  = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            Log.d("Test", "GPS Location changed, Latitude: $latitude" +
                    ", Longitude: $longitude")
        }
        else{
            Log.d("실망","저장된 위치 없음" + LocationManager.NETWORK_PROVIDER)
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        Log.d("클래스","1번")
        tvGpsLocation = findViewById(R.id.tr_view)
        tvGpsLocation.text = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        val b:EditText = findViewById(R.id.tr_text)
        b.setText(b.getText().toString() + "\n" + "Latitude: " + location.latitude + " , Longitude: " + location.longitude)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d("클래스","2번")
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("클래스","2번-조거")
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}