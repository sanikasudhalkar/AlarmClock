package demo.sanika.com.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sanika on 3/12/2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    SensorManager sensorManager;
    TextView alertText;
    int count = 10;
    MediaPlayer mediaPlayer;
    SetAlarmActivity inst;

    @Override
    public void onReceive(Context context, Intent intent) {

        //this will update the UI with message
        inst = SetAlarmActivity.instance();

        // Used to fetch the phone's default alarm tone
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        //used to repeatedly play the alarm tone, until the user walks 10 steps.
        mediaPlayer = MediaPlayer.create(context, alarmUri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        alertText = (TextView) inst.findViewById(R.id.alert);
        alertText.setText("Walk 10 steps to stop alarm!!!");

        sensorManager = (SensorManager) inst.getSystemService(Context.SENSOR_SERVICE);
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (count != 0)
                        alertText.setText("Walk " + count-- + " more steps...");
                   else if (count == 0) {

                        mediaPlayer.stop();
                        alertText.setText("Hi! You did a great job!!!");
                        Switch setAlarm = (Switch)inst.findViewById(R.id.setAlarm);
                        setAlarm.setChecked(false);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(inst, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }
}
