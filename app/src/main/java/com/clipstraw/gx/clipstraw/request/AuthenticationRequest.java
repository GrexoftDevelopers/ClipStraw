package com.clipstraw.gx.clipstraw.request;

import com.clipstraw.gx.clipstraw.helper.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tahir on 2/11/2016.
 */
public class AuthenticationRequest extends Request {

    private static final String API_NAME = "authenticator";

    public final static String LOGIN = "login";

    public static final String REGISTER = "register";

    public AuthenticationRequest(String apiEndPoint, RequestCallback callback) {
        super(API_NAME, apiEndPoint);
        this.callback = callback;
    }


    @Override
    protected void onRawResponse(String response) {
        JSONObject jsonResponse = new JSONObject();
        switch (apiEndPoint){
            case LOGIN:
                try {
                    if (parameters.getString("user_id").equals("tahir") && parameters.getString("password").equals("tahir@123")){
                        JSONObject sessionInfo = new JSONObject();
                        sessionInfo.put(SessionManager.SESSION_ID_KEY, new String("new_session_id"));
                        sessionInfo.put(SessionManager.USER_EMAIL_KEY, new String("foobar@example.com"));
                        sessionInfo.put(SessionManager.USER_NAME_KEY, new String("John Doe"));
                        sessionInfo.put(SessionManager.USER_ID_KEY, new String("foobar123"));
                        jsonResponse.put("session_info",sessionInfo);
                    }
                    else{
                        JSONObject error = new JSONObject();
                        error.put("error",true);
                        error.put("error_msg","incorrect email or password");
                        jsonResponse.put("error",error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case REGISTER:
                try {
                    JSONObject sessionInfo = new JSONObject();
                    sessionInfo.put(SessionManager.SESSION_ID_KEY, new String("new_session_id"));
                    sessionInfo.put(SessionManager.USER_EMAIL_KEY, parameters.getString("email"));
                    sessionInfo.put(SessionManager.USER_NAME_KEY, "foobar");
                    sessionInfo.put(SessionManager.USER_ID_KEY, parameters.getString("user_id"));
                    jsonResponse.put("session_info",sessionInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        callback.onCompleted(jsonResponse);
    }
}
