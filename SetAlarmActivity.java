package demo.sanika.com.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity {

    private TimePicker alarm;
    private Switch setAlarmButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static SetAlarmActivity inst;

    public static SetAlarmActivity instance() {
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        alarm = (TimePicker)findViewById(R.id.alarm);
        setAlarmButton = (Switch) findViewById(R.id.setAlarm);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent myIntent = new Intent(SetAlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SetAlarmActivity.this, 0, myIntent, 0);

        setAlarmButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("SetAlarmActivity", "Alarm is set");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarm.getCurrentMinute());
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        });
    }

}
