package com.example.vtb_test

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.vtb_test.data.database.BankDatabase
import com.example.vtb_test.data.database.dao.DepartmentDao
import com.example.vtb_test.data.database.entity.DepartmentEntity
import com.example.vtb_test.databinding.ActivityMainBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val MAPKIT_API_KEY = "a2e3a802-b15b-484c-8b70-61d044c37caf"
    private lateinit var mapView: MapView
    private lateinit var departmentDao: DepartmentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView=binding.mapview
        mapView.map.move(CameraPosition(Point(55.4507,37.3656),11.0f,0.0f,0.0f),
        Animation(Animation.Type.SMOOTH,10f),null)
        var mapKit:MapKit=MapKitFactory.getInstance()

        RequestLocationPermission()

        var traffic = mapKit.createTrafficLayer(mapView.mapWindow)
        traffic.isTrafficVisible=true

        var userLocation = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocation.isVisible = true


        val db: BankDatabase? = App().getInstance()!!.getDatabase()
        departmentDao = db!!.departmentDao()

        val gsonConvert = GsonConvert(this)

        InsertDatabase(gsonConvert.jsonArray)

    }

    private fun RequestLocationPermission() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),0)
        return
    }

    fun InsertDatabase(jsonArray: JSONArray) {

        for (i in 0 until jsonArray.length()) {
            val data = jsonArray.getJSONObject(i)
            val salePointName = data.getString("salePointName")
            val address = data.getString("address")

            val openHoursArray = data.getJSONArray("openHours")
            val openHourMut: MutableList<String> = mutableListOf()
            for (i in 0 until openHoursArray.length()) {
                val data = openHoursArray.getJSONObject(i)
                openHourMut.add(data.getString("days"))
                openHourMut.add(data.getString("hours"))
            }

            val openHour: List<String> = openHourMut

            val openHoursIndividualArray = data.getJSONArray("openHoursIndividual")
            val openHourIndividualMut: MutableList<String> = mutableListOf()

            for (i in 0 until openHoursIndividualArray.length()) {
                val data = openHoursIndividualArray.getJSONObject(i)
                openHourIndividualMut.add(data.getString("days"))
                openHourIndividualMut.add(data.getString("hours"))
            }
            val openHourIndividual: List<String> = openHourIndividualMut

            val hasRamp = data.getString("hasRamp")
            val latitude = data.getString("latitude").toFloat()
            val longitude = data.getString("longitude").toFloat()
            val metroStation = data.getString("metroStation")

            lifecycleScope.launch(Dispatchers.IO) {
                departmentDao.insertDepartment(
                    DepartmentEntity(
                    0,
                    address,
                    salePointName,
                    openHour,
                    openHourIndividual,
                    hasRamp,
                    latitude,
                    longitude,
                    metroStation
                )
                )

            }

            mapView.map.mapObjects.addPlacemark(
                Point(latitude.toDouble(), longitude.toDouble()),
                ImageProvider.fromAsset(this,"mark_department.png")
            )

            openHourMut.clear()
            openHourIndividualMut.clear()

        }

    }
    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}