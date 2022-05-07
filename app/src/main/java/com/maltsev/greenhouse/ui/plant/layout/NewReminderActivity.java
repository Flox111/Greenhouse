package com.maltsev.greenhouse.ui.plant.layout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.common.DateFormatter;
import com.maltsev.greenhouse.ui.note.layout.CreateNoteActivity;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewReminderActivity extends AppCompatActivity {

    EditText title;
    EditText edDate;
    EditText edTime;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        final Toolbar toolbar = findViewById(R.id.toolbar_create_reminder);
        setSupportActionBar(toolbar);

        //title = findViewById(R.id.title_create_note);
        //title.setText(getIntent().getExtras().get("name").toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        NumberPicker numberPicker1 = findViewById(R.id.number_picker_every);
        numberPicker1.setMaxValue(0);
        numberPicker1.setMinValue(0);
        numberPicker1.setDisplayedValues(new String[]{"Every"});

        NumberPicker numberPicker2 = findViewById(R.id.number_picker);
        numberPicker2.setMaxValue(10);
        numberPicker2.setMinValue(1);
        numberPicker2.setValue(5);

        List<String> data = new ArrayList<>();
        data.add("days");
        data.add("weeks");
        data.add("months");

        NumberPicker numberPicker3 = findViewById(R.id.number_picker_date);
        String[] str = new String[data.size()];
        data.toArray(str);
        numberPicker3.setMaxValue(2);
        numberPicker3.setMinValue(0);
        numberPicker3.setValue(1);
        numberPicker3.setDisplayedValues(str);


        edDate = findViewById(R.id.add_reminder_date);
        calendar = Calendar.getInstance();

        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);
        edDate.setText(DateFormatter.getShortDate(calendar, month, day));
        edDate.setOnClickListener(view -> handleDateButton());

        edTime = findViewById(R.id.add_reminder_time);
        edTime.setText(DateFormatter.getTimeNow(calendar));
        edTime.setOnClickListener(view -> handleTimeButton());

    }

    private void handleDateButton() {
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);

        edDate.setText(DateFormatter.getShortDate(calendar, month, day));

        edDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    NewReminderActivity.this, (datePicker, year1, month1, day1) -> {
                calendar.set(year1, month1, day1);
                String date = DateFormatter.getShortDate(calendar, month1, day1);
                edDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void handleTimeButton() {
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour1, int minute1) {
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
                        hour1, minute1);
                edTime.setText(DateFormatter.getTimeNow(calendar));
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
