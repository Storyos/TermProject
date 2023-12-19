package com.example.termproject
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.daum.mf.map.api.*

class MapActivity : AppCompatActivity() {
    private lateinit var userNicknameTextView: TextView
    private lateinit var mapView : MapView
    private var locationManager : LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity_form)

        mapView = findViewById(R.id.mapView)
        userNicknameTextView = findViewById(R.id.mynickname)
        //플로팅 액션 버튼 구현
        val fab: FloatingActionButton = findViewById(R.id.fab)
        loadAndDisplayMarkers()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userId = getUserIdFromPreferencesOrDatabase()
        val userNickname = getUserIdFromPreferencesOrDatabase()
        userNicknameTextView.text = userNickname

        val mapPoint = MapPoint.mapPointWithGeoCoord(35.134785, 129.103160) // 부경대 좌표
        mapView.setMapCenterPoint(mapPoint, true)
        mapView.setZoomLevel(1, true)
        Log.d("MapActivity", "User ID: $userId")

        fab.setOnClickListener {
            if (checkLocationPermission()) {
                getCurrentLocation().let { location ->
                    if (location != null) {
                        Log.d("Location", "Location is not null")
                        showMarkerTitleInputDialog(location)
                    }else{
                        Log.e("Location", "Location is NuLL")
                    }
                }
            }
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        }
    }
    // 닉네임 우측상단에 표기하기위한 함수
    private fun getUserIdFromPreferencesOrDatabase() : String {
        val sharedPref = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        return sharedPref.getString("loggedInUserId","")?: ""
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Location? {
        if (checkLocationPermission()) {
            Log.d("GetCurrentLocation", "Have to Return LocATION")
            val isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
            val isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
           if (isNetworkEnabled) {
                Log.d("GetCurrentLocation", "Return Network Location")
                return locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }  else if (isGpsEnabled) {
               Log.d("GetCurrentLocation", "Return GPS Location")
               return locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
           }
        }
        Log.d("GetCurrentLocation", " NULL RETURNED ")
        return null
    }

    private fun addMarkerOnMap(latitude: Double, longitude: Double, title: String, userId: String) {
        val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        val marker = MapPOIItem()
        marker.itemName = title
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        mapView.addPOIItem(marker)

        // 마커 정보를 데이터베이스에 저장
        val myDataManager = MyDataManager(this)
        myDataManager.open()
        myDataManager.saveMarker(userId, latitude, longitude, title)
        myDataManager.close()
    }


    @SuppressLint("Range")
    private fun loadAndDisplayMarkers() {
        val userId = getUserIdFromPreferencesOrDatabase() // 현재 사용자 ID

        val myDataManager = MyDataManager(this)
        myDataManager.open()
        val cursor = myDataManager.loadMarkers(userId)
        while (cursor.moveToNext()) {
            val latitude = cursor.getDouble(cursor.getColumnIndex("latitude"))
            val longitude = cursor.getDouble(cursor.getColumnIndex("longitude"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            // 마커를 지도에 추가
            addMarkerOnMap(latitude, longitude, title, userId)
        }
        cursor.close()
        myDataManager.close()
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            return false
        }
        return true
    }
    private fun showMarkerTitleInputDialog(location: Location)
    {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("마커 제목 설정")
        val input= EditText(this)
        builder.setView(input)

        builder.setPositiveButton("확인") { dialog, which ->
            val title = input.text.toString()
            val userId = getUserIdFromPreferencesOrDatabase()
            addMarkerOnMap(location.latitude,location.longitude, title, userId)
        }
        builder.setNegativeButton("취소") {dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }
    //MENU
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                // "설정" 메뉴 아이템 클릭 시 수행할 작업
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logout -> {
                // "로그아웃" 메뉴 아이템 클릭 시 수행할 작업
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun logoutUser() {
        val sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("loggedInUserId")
            apply()
        }

        val loginIntent = Intent(this, MainActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}

