package com.maltsev.greenhouse.ui.greenhouse.layout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.maltsev.greenhouse.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class CreateGreenhouseActivity extends AppCompatActivity {

    TextInputEditText etDate;
    FloatingActionButton button;
    ShapeableImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_greenhouse);

        Toolbar toolbar = findViewById(R.id.toolbar_greenhouse);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etDate = findViewById(R.id.et_date_greenhouse);

        button = findViewById(R.id.button_add_photo_greenhouse);
        imageView = findViewById(R.id.photo_preview_greenhouse);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(CreateGreenhouseActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);

        etDate.setText(new DateFormatSymbols().getShortMonths()[month] + " " +
                + day +", " + year);

        etDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CreateGreenhouseActivity.this, (datePicker, year1, month1, day1) -> {
                        String date = new DateFormatSymbols().getShortMonths()[month1] + " " +
                                + day1 +", " + year1;
                        etDate.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        imageView.setImageURI(uri);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
