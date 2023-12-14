package com.example.termproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.termproject.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapView
import android.content.Intent
import android.widget.Button
import net.daum.mf.map.api.MapPoint

// lateinit var mapViewContainer1 : ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mapView = MapView(this)
        binding.clKakaoMapView.addView(mapView)

        val mapPoint = MapPoint.mapPointWithGeoCoord(35.134785, 129.103160) // 부경대 좌표
        mapView.setMapCenterPoint(mapPoint, true)
        mapView.setZoomLevel(3, true)
       /* 화면전환 버튼
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        mapViewContainer1 = findViewById(R.id.clKakaoMapView)
        mapViewContainer1.addView(mapView);

        // Map 중심점 설정
        //mapView.setMapCenterPoint()
        // 줌 레벨 변경
        mapView.setZoomLevel(1,true);
        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);

        //마커 추가//
        var Marker_Point1 : MapPoint = MapPoint.mapPointWithGeoCoord(32.15,123.5);
        val marker1 = MapPOIItem()

        marker1.itemName = "Marker Name";
        marker1.mapPoint = Marker_Point1;
        marker1.markerType = MapPOIItem.MarkerType.BluePin;

        marker1.selectedMarkerType=MapPOIItem.MarkerType.RedPin;
        mapView.addPOIItem(marker1);
*/
    }

}
