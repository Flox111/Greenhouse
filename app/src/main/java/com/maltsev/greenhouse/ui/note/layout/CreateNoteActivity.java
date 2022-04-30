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

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CreateNoteActivity extends AppCompatActivity {

    ImageView done;
    TextInputEditText edText;
    EditText edDate;
    EditText title;

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
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);

        edDate.setText(getDayOfWeek(calendar) + ", " + new DateFormatSymbols().getShortMonths()[month] + " " +
                +day);

        edDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CreateNoteActivity.this, (datePicker, year1, month1, day1) -> {
                String date = new DateFormatSymbols().getShortMonths()[month1] + " " +
                        +day1 + ", " + year1;
                edDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });

        done = findViewById(R.id.done);
        done.setOnClickListener(view -> {
            if (edDate.getText().toString() != "" && edText.getText().toString() != "") {
                Intent returnIntent = new Intent();

                String dateString = edDate.getText().toString();
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

    @Override
    public boolean onSupportNavigateUp() {
        closeKeyboard();
        onBackPressed();
        return true;
    }

    private String getDayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String weekDay = "";
        if (Calendar.MONDAY == dayOfWeek) weekDay = "Monday";
        else if (Calendar.TUESDAY == dayOfWeek) weekDay = "Tuesday";
        else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = "Wednesday";
        else if (Calendar.THURSDAY == dayOfWeek) weekDay = "Thursday";
        else if (Calendar.FRIDAY == dayOfWeek) weekDay = "Friday";
        else if (Calendar.SATURDAY == dayOfWeek) weekDay = "Saturday";
        else if (Calendar.SUNDAY == dayOfWeek) weekDay = "Sunday";

        return weekDay;
    }

    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
