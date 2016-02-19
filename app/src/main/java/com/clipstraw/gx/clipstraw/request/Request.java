package com.clipstraw.gx.clipstraw.request;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONObject;

/**
 * Created by tahir on 2/11/2016.
 */
public abstract class Request {

    protected String apiName;

    protected String apiEndPoint;

    protected Bundle parameters;

    protected RequestCallback callback;

    public Request(String apiName, String apiEndPoint){
        this.apiName = apiName;
        this.apiEndPoint = apiEndPoint;
    }

    public void setParameters(Bundle parameters) {
        this.parameters = parameters;
    }

    public void setCallback(RequestCallback callback) {
        this.callback = callback;
    }

    public String getApiName() {
        return apiName;
    }

    public Bundle getParameters() {
        return parameters;
    }

    public String getApiEndPoint() {
        return apiEndPoint;
    }

    public void execute(){
        new RequestExecutor().execute();
    }

    private class RequestExecutor extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.currentThread().sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            onRawResponse(null);
        }
    }

    protected abstract void onRawResponse(String response);

    public interface RequestCallback{
        public void onCompleted(JSONObject response);
    }

}
