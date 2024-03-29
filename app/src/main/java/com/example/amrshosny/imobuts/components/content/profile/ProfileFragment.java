package com.example.amrshosny.imobuts.components.content.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.amrshosny.imobuts.R;
import com.example.amrshosny.imobuts.api.ApiController;
import com.example.amrshosny.imobuts.api.json.JsonResponse;
import com.example.amrshosny.imobuts.api.json.User;
import com.example.amrshosny.imobuts.components.charge.Charge;
import com.example.amrshosny.imobuts.components.signin.SignIn;
import com.example.amrshosny.imobuts.components.updateprofile.UpdateProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    View view;
    TextView username;
    TextView balance;
    TextView email;
    Button updateProfile;
    Button charge;
    Button logout;
    SharedPreferences sharedPreferences;
    String token;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        username = (TextView) view.findViewById(R.id.username);
        balance = (TextView) view.findViewById(R.id.balance);
        email = (TextView) view.findViewById(R.id.email);
        updateProfile = (Button) view.findViewById(R.id.edit_profile);
        charge = (Button) view.findViewById(R.id.charge);
        logout = (Button) view.findViewById(R.id.logout);
        sharedPreferences = this.getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProfile.class);
                intent.putExtra("username", username.getText().toString());
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().remove("token").commit();
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Charge.class);
                startActivity(intent);
            }
        });

        getProfileApi();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfileApi();
    }

    void getProfileApi(){
        ApiController.getApi()
                .getProfile(token)
                .enqueue(new Callback<JsonResponse<User>>() {
                    @Override
                    public void onResponse(Call<JsonResponse<User>> call, Response<JsonResponse<User>> response) {
                        if(response.body().getSuccess()){
                            username.setText(response.body().getResponse().getUsername());
                            balance.setText(response.body().getResponse().getBalance().toString());
                            email.setText(response.body().getResponse().getEmail());

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("balance", response.body().getResponse().getBalance().toString());
                            editor.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponse<User>> call, Throwable t) {

                    }
                });
    }
}
