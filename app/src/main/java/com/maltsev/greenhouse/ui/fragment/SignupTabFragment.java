package com.maltsev.greenhouse.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.maltsev.greenhouse.R;
import com.maltsev.greenhouse.network.TokenManager;
import com.maltsev.greenhouse.network.client.RetrofitBuilder;
import com.maltsev.greenhouse.network.model.MessageResponse;
import com.maltsev.greenhouse.network.model.SignupRequest;
import com.maltsev.greenhouse.network.service.ApiAuthService;
import com.maltsev.greenhouse.ui.activity.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupTabFragment extends Fragment {

    EditText username;
    EditText email;
    EditText password;
    Button signup_btn;

    ApiAuthService service;
    TokenManager tokenManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

        username = root.findViewById(R.id.username);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        signup_btn = root.findViewById(R.id.signup_btn);

        service = RetrofitBuilder.createService(ApiAuthService.class);
        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE));


        signup_btn.setOnClickListener(v -> signup());

        if(tokenManager.getToken().getAccessToken() != null){
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }

        return root;
    }

    private void signup(){
        SignupRequest signupRequest;
        Call<MessageResponse> call;
        if (username.getText().toString() != "" && email.getText().toString() != "" && password.getText().toString() != ""){
            signupRequest = new SignupRequest(username.getText().toString(),
                    email.getText().toString(), password.getText().toString());
            call = service.signup(signupRequest);
        }
        else{
            Toast.makeText(getContext(),"empty", Toast.LENGTH_SHORT).show();
            return;
        }

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
}
