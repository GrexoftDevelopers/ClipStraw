package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import widgets.User;

/**
 * Created by Faizzy on 11-01-2016.
 */
public class RegistrationFragment extends Fragment {

    private View fragmentView;

    private EditText etName, etEmail, etPassword, etConfirmPassword;

    private Button btnLogin, btnRegister;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        fragmentView = inflater.inflate(R.layout.registration_page, container, false);

        etName = (EditText) fragmentView.findViewById(R.id.et_name);

        etEmail = (EditText) fragmentView.findViewById(R.id.et_email);

        etPassword = (EditText) fragmentView.findViewById(R.id.et_password);

        etConfirmPassword = (EditText) fragmentView.findViewById(R.id.et_confirm_password);

        btnLogin = (Button)fragmentView.findViewById(R.id.btn_alt_login);

        btnRegister = (Button)fragmentView.findViewById(R.id.btn_register);

        btnLogin.setOnClickListener((View.OnClickListener)getActivity());

        btnRegister.setOnClickListener((View.OnClickListener)getActivity());






        return fragmentView;
    }



    public Bundle getUserCredentials(){
        String userId = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (!userId.isEmpty() && !email.isEmpty() && !password.isEmpty() && confirmPassword.equals(password)){
            Bundle credentials = new Bundle();
            credentials.putString("user_id",userId);
            credentials.putString("email",email);
            credentials.putString("password",password);
            return  credentials;
        }
        else{
            if(userId == null){
                Toast.makeText(getActivity(),"Please enter user id",Toast.LENGTH_SHORT).show();
            }
            else if(email == null){
                Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_SHORT).show();
            }
            else if(password == null){
                Toast.makeText(getActivity(),"Please enter password",Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirmPassword)){
                Toast.makeText(getActivity(),"the passwords do not match",Toast.LENGTH_SHORT).show();
            }
            return  null;
        }


    }
}
