package justynafirkowska.pocketknife;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by admin on 04/02/17.
 */

public class Light implements SensorEventListener {
    private TextView tv;
    private SensorManager sManager;

    public Light(TextView tv, SensorManager sManager) {
        this.tv = tv;
        this.sManager = sManager;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {}

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        //else it will output the Roll, Pitch and Yawn values
        tv.setText(Float.toString(event.values[0]) + " SI lux units");
    }

    public void registerListener() {
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterListener() {
        sManager.unregisterListener(this);
    }
}