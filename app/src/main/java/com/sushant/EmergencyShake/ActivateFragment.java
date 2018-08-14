package com.sushant.EmergencyShake;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivateFragment extends Fragment implements  View.OnClickListener, SensorEventListener,LocationListener {

    @BindView(R.id.textViewSensor)
    TextView txtSensor;

    @BindView(R.id.buttonActivate)
    Button btnActivate;

    @BindView(R.id.textViewLocation)
    TextView txtLocation;

    SensorManager sensorManager;

    Sensor sensor;

    Sensor tempsen;

    LocationManager locationManager;

    View view;



    public ActivateFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activate_fragment,container,false);


        btnActivate = view.findViewById(R.id.buttonActivate);
        btnActivate.setOnClickListener(this);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tempsen = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        locationManager= (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float cal = ((x * x) + (y * y) + (z * z)) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        if (cal > 25) {

            txtSensor.setText("Device Shaken at Coordinates =  " + x + " : " + y + " : " + z);
            sensorManager.unregisterListener(this);


            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, this);
            }

        } else {
            txtSensor.setText("Coordinates: " + x + " : " + y + " : " + z);
            txtLocation.setText("Location will be displayed when device is shaken");
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        txtLocation.setText("Location is: " + latitude + " , " + longitude);

        try {
            Geocoder geocoder = new Geocoder(getActivity());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 2);

            StringBuffer completeAddress = new StringBuffer();

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    completeAddress.append(address.getAddressLine(i) + "\n");
                }

                txtLocation.setText("Location is: " + latitude + " , " + longitude + "\n" + completeAddress.toString());
            }



            if(Util.phone == ""){
                Toast.makeText(getActivity(), "No Contact Selected \nSending message to Default Contact", Toast.LENGTH_LONG).show();
                SmsManager smsManager = SmsManager.getDefault();
                String message = "THIS IS AN EMERGENCY! \nLOCATION IS:http://maps.google.com/maps?saddr="+latitude+ ","+longitude;
                String phone = "7986816504";
                smsManager.sendTextMessage(phone, null, message, null, null);
                locationManager.removeUpdates(this);

            }
            else {
                Toast.makeText(getActivity(), "Message has been sent", Toast.LENGTH_LONG).show();
                SmsManager smsManager = SmsManager.getDefault();
                String message = "THIS IS AN EMERGENCY! \nLOCATION IS:http://maps.google.com/maps?saddr="+latitude+ ","+longitude;
                smsManager.sendTextMessage(Util.phone, null, message, null, null);
                locationManager.removeUpdates(this);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (id == R.id.buttonActivate) {
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        locationManager.removeUpdates(this);
    }
}


