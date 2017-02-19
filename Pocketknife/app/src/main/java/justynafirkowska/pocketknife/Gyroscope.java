package justynafirkowska.pocketknife;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by admin on 04/02/17.
 */

public class Gyroscope implements SensorEventListener
{
    private TextView tv;
    private SensorManager sManager;

    public Gyroscope(TextView tv, SensorManager sManager) {
        this.tv = tv;
        this.sManager = sManager;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {}

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        tv.setText("Orientation X (Roll): "+ Float.toString(event.values[2]) +"\n"+
                "Orientation Y (Pitch): "+ Float.toString(event.values[1]) +"\n"+
                "Orientation Z (Yaw): "+ Float.toString(event.values[0]));
    }

    public void registerListener() {
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregisterListener() {
        sManager.unregisterListener(this);
    }
}
