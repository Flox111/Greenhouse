package com.maltsev.greenhouse.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.network.TokenManager;
import com.maltsev.greenhouse.network.client.RetrofitBuilder;
import com.maltsev.greenhouse.network.model.AccessToken;
import com.maltsev.greenhouse.network.model.LoginRequest;
import com.maltsev.greenhouse.network.model.User;
import com.maltsev.greenhouse.network.service.ApiAuthService;
import com.maltsev.greenhouse.ui.activity.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninTabFragment extends Fragment {

    EditText email;
    EditText password;
    Button login_btn;

    ApiAuthService service;
    TokenManager tokenManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signin_tab_fragment, container, false);

        transparentStatusBarAndNavigation();
        service = RetrofitBuilder.createService(ApiAuthService.class);
        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE));

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        login_btn = root.findViewById(R.id.login_btn);

        email.setTranslationX(800);
        password.setTranslationX(800);
        login_btn.setTranslationX(800);

        email.setAlpha(0);
        password.setAlpha(0);
        login_btn.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();


        login_btn.setOnClickListener(v -> login());

        if (tokenManager.getToken().getAccessToken() != null) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        return root;
    }

    private void login() {

        LoginRequest loginRequest;
        Call<User> call;
        if (email.getText().toString() != "" && password.getText().toString() != "") {
            loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString());
            call = service.login(loginRequest);
        } else {
            Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
            return;
        }

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    tokenManager.saveToken(new AccessToken(response.body().getAccessToken(), response.body().getRefreshToken()));
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void transparentStatusBarAndNavigation() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(int i, boolean b) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (b) {
            winParams.flags |= i;
        } else {
            winParams.flags &= ~i;
        }
    }
}
