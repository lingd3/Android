package com.example.gd.ex10;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by gd on 16/11/30.
 */
public class MainActivity extends Activity {

    private MapView mapView = null;
    private SensorManager mSensorManager;
    private LocationManager mLocationManager;
    private Sensor mMagneticSensor;
    private Sensor mACcelerometerSensor;
    private ToggleButton toggleButton;
    private static SensorUtils sensorUtils = new SensorUtils();

    float[] accValues = new float[3];
    float[] magValues = new float[3];
    float newRotationDegree = 0;
    double latitude = 0;
    double longitude = 0;
    LatLng desLatLng;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //获取传感器管理器
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mACcelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (checkPermission()) {
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);

        //使用Handler，时刻更新箭头指向
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.pointer), 100, 100, true);
                BitmapDescriptor bitmapD = BitmapDescriptorFactory.fromBitmap(bitmap);
                mapView.getMap().setMyLocationEnabled(true);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapD);
                mapView.getMap().setMyLocationConfigeration(config);

                MyLocationData.Builder data = new MyLocationData.Builder();
                desLatLng = sensorUtils.getLatLng(latitude, longitude);
                data.latitude(desLatLng.latitude);
                data.longitude(desLatLng.longitude);
                data.direction(newRotationDegree);
                mapView.getMap().setMyLocationData(data.build());

                if (toggleButton.isChecked()) {
                    //当按钮是实心时，更新位置，保持箭头在中点
                    MapStatus mMapStatus = new MapStatus.Builder().target(desLatLng).build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mapView.getMap().setMapStatus(mMapStatusUpdate);
                }

                //摇一摇功能
                int value = 19;
                if (Math.abs(accValues[0]) > value || Math.abs(accValues[1]) > value || Math.abs(accValues[2]) > value) {
                    Toast.makeText(MainActivity.this, "别再摇了，我快晕了。。。", Toast.LENGTH_SHORT).show();
                }

                handler.postDelayed(this, 1000);
            }
        }, 1000);

        toggleButton = (ToggleButton)findViewById(R.id.tb_center);
        if (toggleButton != null) {
            //使坐标居中
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MapStatus mMapStatus = new MapStatus.Builder().target(desLatLng).build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mapView.getMap().setMapStatus(mMapStatusUpdate);
                }
            });

            //拖动地图后按钮有实心变空心
            mapView.getMap().setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
                @Override
                public void onTouch(MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            toggleButton.setChecked(false);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册传感器
        mSensorManager.registerListener(mSensorEventListener, mMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mSensorEventListener, mACcelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

        if (checkPermission()) {
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, mLocationListener);
            } else {
                Toast.makeText(MainActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }

        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销传感器
        mSensorManager.unregisterListener(mSensorEventListener);
        //权限检查
        if (!checkPermission()) {
            return;
        }
        mLocationManager.removeUpdates(mLocationListener);
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accValues = event.values;
                    accValues[0] = event.values[0];
                    accValues[1] = event.values[1];
                    accValues[2] = event.values[2];
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magValues = event.values;
                    break;
                default:
                    break;
            }
            newRotationDegree = sensorUtils.getNewRotationDegree(accValues, magValues);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude(); //纬度
            longitude = location.getLongitude(); //经度
            desLatLng = sensorUtils.getLatLng(latitude, longitude);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };

    //检查权限
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


}
