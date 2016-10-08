package com.veronica.medaily;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.IOException;

/**
 * Created by Veronica on 10/8/2016.
 */
public class ToBeRemoved {

//    private void getLocation() {
//
//        int hasPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
//        if(hasPermission== PackageManager.PERMISSION_GRANTED){
//            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            mLatitude = location.getLatitude();
//            mLongitude = location.getLongitude ();
//
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    mLongitude = location.getLongitude();
//                    mLatitude = location.getLatitude();
//                    try {
//                        LocationHelper.getCompleteAddress(getContext(),mLatitude,mLongitude);
//                        mTextViewTitle.setText(mLocation);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    //this.mTextViewTitle.setText(mLocation);
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//
//        }else{
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
//
//        }
//    }
//


    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode){
//            case 0:
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                }else{
//                    notificationHandler.toastNeutralNotificationBottom("Permission denied");
//                }
//        }
//    }
}
