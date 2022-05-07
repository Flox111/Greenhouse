package com.maltsev.greenhouse.ui.note.layout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.common.DateFormatter;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {

    ImageView done;
    TextInputEditText edText;
    EditText edDate;
    EditText title;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        final Toolbar toolbar = findViewById(R.id.toolbar_create_note);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title_create_note);
        title.setText(getIntent().getExtras().get("name").toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);


        edDate = findViewById(R.id.add_note_date);

        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);

        edDate.setText(DateFormatter.getShortDate(calendar, month, day));

        edDate.setOnClickListener(view -> {
            handleDateButton();
        });

        done = findViewById(R.id.done);
        done.setOnClickListener(view -> {
            if (edDate.getText().toString() != "" && edText.getText().toString() != "") {
                Intent returnIntent = new Intent();

                String dateString = Long.toString(calendar.getTimeInMillis());
                String textString = edText.getText().toString();
                returnIntent.putExtra("date", dateString);
                returnIntent.putExtra("text", textString);
                setResult(Activity.RESULT_OK, returnIntent);

                closeKeyboard();
                finish();
            }
        });

        edText = findViewById(R.id.edit_text_note);
        edText.setFocusableInTouchMode(true);
        edText.requestFocus();

        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    done.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green4));
                } else {
                    done.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_no_active));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    private void handleDateButton(){
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);

        edDate.setText(DateFormatter.getShortDate(calendar, month, day));

        edDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CreateNoteActivity.this, (datePicker, year1, month1, day1) -> {
                calendar.set(year1, month1, day1);
                String date = DateFormatter.getShortDate(calendar, month1, day1);
                edDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        closeKeyboard();
        onBackPressed();
        return true;
    }


    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
