package ca.nait.adrantiev1.picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity
{
    DateFormat formatter = DateFormat.getDateTimeInstance();
    TextView tvDateTime;

    Chronometer chrono;
    Button buttonStart, buttonStop, buttonReset;
    TextView tvChrono;
    Calendar calendar = Calendar.getInstance();

    long elapsedTime = 0;
    String currentTime = "";
    long startTime = SystemClock.elapsedRealtime();
    boolean resume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate
        tvDateTime = (TextView)findViewById(R.id.date_and_time);
        chrono = (Chronometer)findViewById(R.id.chrono_clock);
        tvChrono = (TextView)findViewById(R.id.chrono_text);
        buttonStart = (Button)findViewById(R.id.button_start);
        buttonStop = (Button)findViewById(R.id.button_stop);
        buttonReset = (Button)findViewById(R.id.button_reset);

    }
    private void updateTimeLabel()
    {
        tvDateTime.setText(formatter.format(calendar.getTime()));
    }
    DatePickerDialog.OnDateSetListener dateSetListner =
            new DatePickerDialog.OnDateSetListener()
            {

                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day)
                {
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,day);
                    updateTimeLabel();
                }
            };

    TimePickerDialog.OnTimeSetListener timeSetListener =
            new  TimePickerDialog.OnTimeSetListener()
            {

                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute)
                {
                    calendar.set(Calendar.HOUR_OF_DAY,hour);
                    calendar.set(Calendar.MINUTE,minute);
                    updateTimeLabel();
                }
            };

    public void chooseDate(View view)
    {
        new DatePickerDialog(this, dateSetListner,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void chooseTime(View view)
    {
        new TimePickerDialog(this,timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true).show();
    }
    public void onButtonClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_start:
            {
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
                if(!resume)
                {
                    chrono.setBase(SystemClock.elapsedRealtime());
                }
                chrono.start();
                break;
            }
            case R.id.button_stop:
            {
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                buttonReset.setEnabled(true);
                chrono.stop();
                chrono.setText(currentTime); //needs to be implemented
                resume = true;

                break;
            }
            case R.id.button_reset:
            {
                chrono.stop();
                chrono.setText("00:00");
                resume = false;
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                buttonReset.setEnabled(false);
                break;
            }
        }
    }

}
