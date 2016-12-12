package com.example.gd.ex10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

/**
 * Created by gd on 16/11/30.
 */
public class SensorUtils {

    //转换坐标
    public LatLng getLatLng(double latitude, double longitude) {
        CoordinateConverter mConverter  = new CoordinateConverter();
        mConverter.from(CoordinateConverter.CoordType.GPS);
        mConverter.coord(new LatLng(latitude, longitude));
        return mConverter.convert();
    }

    //获取箭头角度
    public float getNewRotationDegree(float[] accValues, float[] magValues) {
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R, null, accValues, magValues);
        SensorManager.getOrientation(R, values);
        return (float)Math.toDegrees(values[0]);
    }

}





























