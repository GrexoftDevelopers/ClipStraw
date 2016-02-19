package com.clipstraw.gx.clipstraw.helper.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.ClipstrawApplication;
import com.clipstraw.gx.clipstraw.model.session.ClipstrawSession;
import com.clipstraw.gx.clipstraw.request.AuthenticationRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tahir on 2/12/2016.
 */
public class SessionManager {

    public static final String SESSION_ID_KEY = "session_id";
    public static final String USER_ID_KEY = "user_id";
    public static final String USER_EMAIL_KEY = "user_email";
    public static final String USER_NAME_KEY = "user_name";

    private ClipstrawSession session;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private SessionExpireListener sessionExpireListener;

    private SessionListener sessionListener;

    private static SessionManager mInstance;

    private SessionManager(){
        Context context = ClipstrawApplication.getInstance().getApplicationContext();
        sharedPreferences = context.getSharedPreferences(ClipstrawApplication.PREF_NAME,ClipstrawApplication.PREF_MODE);
        editor = sharedPreferences.edit();
    }

    public static SessionManager getInstance() {
        if (mInstance == null){
            mInstance = new SessionManager();
        }
        return mInstance;
    }

    public void setSessionExpireListener(SessionExpireListener sessionExpireListener) {
        this.sessionExpireListener = sessionExpireListener;
    }

    public void setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }

    public ClipstrawSession getActiveSession(){

        if (this.session != null) return session;

        String sessionId = sharedPreferences.getString(SESSION_ID_KEY,null);

        if (sessionId != null) {
            String userId = sharedPreferences.getString(USER_ID_KEY,null);
            String userEmail = sharedPreferences.getString(USER_EMAIL_KEY,null);
            String userName = sharedPreferences.getString(USER_NAME_KEY,null);
            ClipstrawSession session = new ClipstrawSession(sessionId,userEmail,userId,userName);
            this.session = session;

            return session;
        }
        return null;

    }

    public void clearSession(){
        editor.remove(SESSION_ID_KEY);
        editor.remove(USER_EMAIL_KEY);
        editor.remove(USER_EMAIL_KEY);
        editor.remove(USER_NAME_KEY);
        editor.commit();
        session.clear();
        session = null;
        if(sessionListener != null){
            sessionListener.onSessionDestroyed();
        }
    }

    public void expireSession(){
        session.expire();
        if(sessionExpireListener != null){
            sessionExpireListener.onSessionExpired();
        }
    }

    public void createSession(String sessionId, String userEmail, String userId, String userName){
        this.session = new ClipstrawSession(sessionId,userEmail,userId,userName);
        editor.putString(SESSION_ID_KEY, sessionId);
        editor.putString(USER_ID_KEY, userId);
        editor.putString(USER_EMAIL_KEY, userEmail);
        editor.putString(USER_NAME_KEY, userName);
        editor.commit();
    }

    public void createSession(JSONObject sessionInfo){
        try {
            String sessionId = sessionInfo.getString(SessionManager.SESSION_ID_KEY);
            String userEmail = sessionInfo.getString(SessionManager.USER_EMAIL_KEY);
            String userName = sessionInfo.getString(SessionManager.USER_NAME_KEY);
            String userId = sessionInfo.getString(SessionManager.USER_ID_KEY);
            createSession(sessionId,userEmail,userId,userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void login(String userId, String password){
        Request loginRequest = new AuthenticationRequest(AuthenticationRequest.LOGIN, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try{
                    if (!response.has("error")){
                        createSession(response.getJSONObject("session_info"));
                        if(sessionListener != null){
                            sessionListener.onSessionCreated(session);
                        }
                    }else {
                        if(sessionListener != null){
                            sessionListener.onSessionError(response.getJSONObject("error"));
                        }

                    }
                }
                catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id",userId);
        parameters.putString("password",password);
        loginRequest.setParameters(parameters);
        loginRequest.execute();
    }

    public void register(String userId, String email, String password){
        Request registerRequest = new AuthenticationRequest(AuthenticationRequest.REGISTER, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if(!response.has("error")){
                        createSession(response.getJSONObject("session_info"));
                        if (sessionListener != null){
                            sessionListener.onSessionCreated(session);
                        }
                    }
                    else{
                        if(sessionListener != null){
                            sessionListener.onSessionError(response.getJSONObject("error"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle params = new Bundle();
        params.putString("email",email);
        params.putString("user_id",userId);
        params.putString("password",password);
        registerRequest.setParameters(params);
        registerRequest.execute();
    }

    public interface SessionExpireListener{

        public void onSessionExpired();

    }

    public interface SessionListener{

        public void onSessionCreated(ClipstrawSession session);

        public void onSessionDestroyed();

        public void onSessionError(JSONObject error);

    }
}
