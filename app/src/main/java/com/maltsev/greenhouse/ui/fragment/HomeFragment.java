package com.maltsev.greenhouse.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.settings.SettingsActivity;
import com.maltsev.greenhouse.ui.settings.SettingsFragment;

public class HomeFragment extends Fragment {

    CardView sendEmail;
    ImageView openSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);

        openSettings = root.findViewById(R.id.btn_settings);
        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        sendEmail = root.findViewById(R.id.card_view_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] email = {"artem_maltsev_2000@mail.ru"};

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                if (intent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "No App is installed", Toast.LENGTH_LONG);
                }
            }
        });

        return root;
    }

}
