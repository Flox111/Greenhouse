package com.maltsev.greenhouse.ui.plant.layout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.plant.database.PlantDatabaseQueryClass;
import com.maltsev.greenhouse.ui.plant.model.Plant;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CreatePlantActivity extends AppCompatActivity {

    FloatingActionButton addImageButton;
    ShapeableImageView imageView;
    Uri uri;
    final String DEFAULT_URI = "https://images.unsplash.com/photo-1623050095430-480240c0c463?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
    ImageView done;

    TextInputEditText nameEditText;
    TextInputEditText dateEditText;
    TextInputEditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plant);

        Toolbar toolbar = findViewById(R.id.toolbar_plant_create);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dateEditText = findViewById(R.id.create_plant_date);

        addImageButton = findViewById(R.id.button_add_photo_plant);
        imageView = findViewById(R.id.photo_preview_plant);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(CreatePlantActivity.this)
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

        dateEditText.setText(new DateFormatSymbols().getShortMonths()[month] + " " +
                + day +", " + year);

        dateEditText.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CreatePlantActivity.this, (datePicker, year1, month1, day1) -> {
                        String newDate = new DateFormatSymbols().getShortMonths()[month1] + " " +
                                + day1 +", " + year1;
                        dateEditText.setText(newDate);
                    }, year, month, day);
            datePickerDialog.show();
        });


        uri = null;
        nameEditText = findViewById(R.id.create_plant_name);
        dateEditText = findViewById(R.id.create_plant_date);
        descriptionEditText = findViewById(R.id.create_plant_description);

        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                String nameString = nameEditText.getText().toString();
                String dateString = dateEditText.getText().toString();
                String descriptionString = descriptionEditText.getText().toString();
                String imageUrlString = uri == null ? DEFAULT_URI : uri.toString();

                returnIntent.putExtra("name", nameString);
                returnIntent.putExtra("date", dateString);
                returnIntent.putExtra("description", descriptionString);
                returnIntent.putExtra("imageUrl", imageUrlString);
                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        imageView.setImageURI(uri);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
