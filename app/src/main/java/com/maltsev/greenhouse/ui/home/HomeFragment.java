package com.maltsev.greenhouse.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.ui.login.LoginActivity;
import com.maltsev.greenhouse.ui.settings.SettingsActivity;
import com.maltsev.greenhouse.ui.settings.SettingsFragment;

public class HomeFragment extends Fragment {

    CardView sendEmail;
    ImageView openSettings;
    CardView user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);

        openSettings = root.findViewById(R.id.btn_settings);
        openSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        });

        user = root.findViewById(R.id.user);
        user.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        sendEmail = root.findViewById(R.id.card_view_email);
        sendEmail.setOnClickListener(view -> {
            String[] email = {getString(R.string.email)};

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, email);
            if (intent.resolveActivity(getContext().getPackageManager()) != null){
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(), "No App is installed", Toast.LENGTH_LONG);
            }
        });

        return root;
    }

}
