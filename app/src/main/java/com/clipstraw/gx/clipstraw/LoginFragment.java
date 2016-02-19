package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.helper.session.SessionManager;

import widgets.User;

/**
 * Created by Faizzy on 11-01-2016.
 */
public class LoginFragment extends Fragment{


    private View fragmentView;
    private EditText etUserName,etPassword;

    private Button btnLogin,btnFacebookLogin,btnRegisterNow;
    private CheckBox checkKeepLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.login_page,container,false);

        etUserName = (EditText)fragmentView.findViewById(R.id.et_user_name);

        etPassword = (EditText)fragmentView.findViewById(R.id.et_password);

        btnLogin = (Button)fragmentView.findViewById(R.id.btn_login);

        btnFacebookLogin = (Button)fragmentView.findViewById(R.id.btn_fb_login);

        btnRegisterNow = (Button)fragmentView.findViewById(R.id.btn_alt_register);

        checkKeepLogin = (CheckBox)fragmentView.findViewById(R.id.chk_login);

        btnRegisterNow.setOnClickListener((View.OnClickListener) getActivity());

        btnLogin.setOnClickListener((View.OnClickListener) getActivity());

        return fragmentView;
    }

    public Bundle getUserCredentials(){
        String userId = etUserName.getText().toString();
        String password = etPassword .getText().toString();
        if (userId != null && password != null){
            Bundle cred = new Bundle();
            cred.putString("user_id", userId);
            cred.putString("password", password);
            return cred;
        }
        else{
            if (userId == null){
                Toast.makeText(getActivity(),"please enter user id",Toast.LENGTH_SHORT).show();
            }
            else if (password == null){
                Toast.makeText(getActivity(),"please enter password",Toast.LENGTH_SHORT).show();
            }
            return  null;
        }

    }
}
