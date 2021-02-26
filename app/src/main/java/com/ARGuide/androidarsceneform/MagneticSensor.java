package com.ARGuide.androidarsceneform;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;

import static android.content.Context.SENSOR_SERVICE;

public class MagneticSensor  implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
//    private float[] mLastAccelerometer = new float[3];
//    private float[] mLastMagnetometer = new float[3];
//    private boolean mLastAccelerometerSet = false;
//    private boolean mLastMagnetometerSet = false;
//    private float[] mR = new float[9];
//    private float[] mOrientation = new float[3];
    float[] gData = new float[3]; // accelerometer
    float[] mData = new float[3]; // magnetometer
    float[] rMat = new float[9];
    float[] iMat = new float[9];
    float[] orientation = new float[3];

    private int mAzimuth = 0; // degree

    private float mCurrentDegree = 0f;
    private Location LocationObj;
    private String textBusola;
    private int directie;


    public MagneticSensor(Context context, int directie) {
        mSensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
        LocationObj = new Location(LocationManager.NETWORK_PROVIDER);
        this.directie = directie;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (LocationObj == null) return;
//        if (event.sensor == mAccelerometer) {
//            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
//            mLastAccelerometerSet = true;
//        } else if (event.sensor == mMagnetometer) {
//            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
//            mLastMagnetometerSet = true;
//        }
//        if (mLastAccelerometerSet && mLastMagnetometerSet) {
//            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
//            SensorManager.getOrientation(mR, mOrientation);
//            float azimuthInRadians = mOrientation[0];
//            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
////            RotateAnimation ra = new RotateAnimation(
////                    mCurrentDegree,
////                    -azimuthInDegress,
////                    Animation.RELATIVE_TO_SELF, 0.5f,
////                    Animation.RELATIVE_TO_SELF,
////                    0.5f);
//            mCurrentDegree = -azimuthInDegress;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                gData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mData = event.values.clone();
                break;
            default:
                return;
        }

        if (SensorManager.getRotationMatrix(rMat, iMat, gData, mData)) {
            float baseAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + directie) % 360;


//        float azimuth = event.values[0];
//        float baseAzimuth = mAzimuth;

//        GeomagneticField geoField = new GeomagneticField( Double
//                .valueOf( LocationObj.getLatitude() ).floatValue(), Double
//                .valueOf( LocationObj.getLongitude() ).floatValue(),
//                Double.valueOf( LocationObj.getAltitude() ).floatValue(),
//                System.currentTimeMillis() );

//        base -= geoField.getDeclination(); // converts magnetic north into true north
//        this.mCurrentDegree = azimuth;

            // Store the bearingTo in the bearTo variable
//        float bearTo = LocationObj.bearingTo( destinationObj );

//        // If the azimuth is smaller than 0, add 360 to get the rotation clockwise.
//        if (baseAzimuth < 0) {
//            baseAzimuth = baseAzimuth + 360;
//        }
//
//        //This is where we choose to point it
//        float direction = bearTo - azimuth;
//
//        // If the direction is smaller than 0, add 360 to get the rotation clockwise.
//        if (direction < 0) {
//            direction = direction + 360;
//        }

            //Set the field
            String bearingText = "N";


            if ((360 >= baseAzimuth && baseAzimuth >= 337.5) || (0 <= baseAzimuth && baseAzimuth <= 22.5))
                bearingText = "N";
            else if (baseAzimuth > 22.5 && baseAzimuth < 67.5) bearingText = "NE";
            else if (baseAzimuth >= 67.5 && baseAzimuth <= 112.5) bearingText = "E";
            else if (baseAzimuth > 112.5 && baseAzimuth < 157.5) bearingText = "SE";
            else if (baseAzimuth >= 157.5 && baseAzimuth <= 202.5) bearingText = "S";
            else if (baseAzimuth > 202.5 && baseAzimuth < 247.5) bearingText = "SW";
            else if (baseAzimuth >= 247.5 && baseAzimuth <= 292.5) bearingText = "W";
            else if (baseAzimuth > 292.5 && baseAzimuth < 337.5) bearingText = "NW";
            else bearingText = Float.toString(baseAzimuth);

        textBusola = bearingText;
        mCurrentDegree = baseAzimuth;
    }
}

    public String getTextBusola() {
        return textBusola;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    public float getmCurrentDegree() {
        return mCurrentDegree;
    }
}